package fpt.provipluxurylimited.challengefocus.discovery.discovery;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fpt.provipluxurylimited.challengefocus.helpers.ApiClient;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseUseCaseDelegate;
import fpt.provipluxurylimited.challengefocus.models.CategoryChallenge;
import fpt.provipluxurylimited.challengefocus.models.DiscoveryResult;

public class DiscoveryListUseCase {
    public interface DiscoveryListUseCaseDelegate extends BaseUseCaseDelegate {
        void onGetCategoryListSuccess(DiscoveryResult result);
    }

    private DiscoveryListUseCaseDelegate delegate;

    public void setDelegate(DiscoveryListUseCaseDelegate delegate) {
        this.delegate = delegate;
    }

    public void getDiscoveryList() {
        FirebaseUtil.shared.getReference().child(ApiClient.category)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ArrayList<CategoryChallenge> list = new ArrayList<>();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            CategoryChallenge item = ds.getValue(CategoryChallenge.class);
                            list.add(item);
                        }
                        DiscoveryResult result = groupChallenges(list);
                        delegate.onGetCategoryListSuccess(result);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        delegate.onFailure(error.getMessage());
                    }
                });
    }

    private DiscoveryResult groupChallenges(ArrayList<CategoryChallenge> list) {
        HashMap<String, ArrayList<CategoryChallenge>> hashMap = new HashMap<>();
        List<String> cateNames = new ArrayList<>();
        for(CategoryChallenge challenge : list) {
            if (!hashMap.containsKey(challenge.getCategoryName())) {
                ArrayList<CategoryChallenge> challenges = new ArrayList<>();
                challenges.add(challenge);
                hashMap.put(challenge.getCategoryName(), challenges);
                cateNames.add(challenge.getCategoryName());
            } else {
                hashMap.get(challenge.getCategoryName()).add(challenge);
            }
        }
        return new DiscoveryResult(cateNames, hashMap);
    }

}
