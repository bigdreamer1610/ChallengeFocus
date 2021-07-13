package fpt.provipluxurylimited.challengefocus.challenge.doing;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.helpers.ApiClient;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseUseCaseDelegate;
import fpt.provipluxurylimited.challengefocus.models.Challenge;

public class DoingUseCase {
    public interface DoingUseCaseDelegate extends BaseUseCaseDelegate {
        void onSuccessDoing(ArrayList<Challenge> list);
    }

    private DoingUseCaseDelegate delegate;

    public void setDelegate(DoingUseCaseDelegate delegate) {
        this.delegate = delegate;
    }

    public void getDoingList() {
        FirebaseUtil.shared.getReference().child(ApiClient.myDoing)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ArrayList<Challenge> list = new ArrayList<>();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            Challenge challenge = ds.getValue(Challenge.class);
                            list.add(challenge);
                        }
                        System.out.println(list.size() + " ahihihiihi");
                        delegate.onSuccessDoing(list);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        delegate.onFailure(error.getMessage());
                    }
                });
    }
}
