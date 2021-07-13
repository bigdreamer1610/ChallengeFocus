package fpt.provipluxurylimited.challengefocus.challenge.fail;

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

public class FailedUseCase {
    public interface FailedUseCaseDelegate extends BaseUseCaseDelegate {
        void onSuccessFailed(ArrayList<Challenge> list);
    }

    private FailedUseCaseDelegate delegate;

    public void setDelegate(FailedUseCaseDelegate delegate) {
        this.delegate = delegate;
    }

    public void getFailedList() {
        FirebaseUtil.shared.getReference().child(ApiClient.myFailed)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ArrayList<Challenge> list = new ArrayList<>();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            Challenge challenge = ds.getValue(Challenge.class);
                            list.add(challenge);
                        }
                        delegate.onSuccessFailed(list);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        delegate.onFailure(error.getMessage());
                    }
                });
    }
}