package fpt.provipluxurylimited.challengefocus.discovery.discovery;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.helpers.ApiClient;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseUseCaseDelegate;
import fpt.provipluxurylimited.challengefocus.models.Category;
import fpt.provipluxurylimited.challengefocus.models.CategoryChallenge;

public class DiscoveryListUseCase {
    public interface DiscoveryListUseCaseDelegate extends BaseUseCaseDelegate {
        void onGetCategoryListSuccess(ArrayList<Category> list);
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
                        ArrayList<Category> categories = new ArrayList<>();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            ArrayList<CategoryChallenge> categoryChallenges = new ArrayList<>();
                            int id = ds.child("id").getValue(Integer.class);
                            String title = ds.child("title").getValue(String.class);
                            DataSnapshot challengeSnapshot = ds.child("challenges");
                            for(DataSnapshot childDs : challengeSnapshot.getChildren()) {
                                CategoryChallenge item = childDs.getValue(CategoryChallenge.class);
                                categoryChallenges.add(item);
                            }
                            categories.add(new Category(id, title, categoryChallenges));
                        }
                        System.out.println("count: " + categories.size());
                        delegate.onGetCategoryListSuccess(categories);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        delegate.onFailure(error.getMessage());
                    }
                });
    }

}
