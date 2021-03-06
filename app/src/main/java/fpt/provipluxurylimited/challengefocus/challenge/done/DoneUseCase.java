package fpt.provipluxurylimited.challengefocus.challenge.done;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

import fpt.provipluxurylimited.challengefocus.helpers.ApiClient;
import fpt.provipluxurylimited.challengefocus.helpers.Constants;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.helpers.Utils;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseUseCaseDelegate;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.MyChallenges;

public class DoneUseCase {
    public interface DoneUseCaseDelegate extends BaseUseCaseDelegate {
        void onSuccessDone(ArrayList<Challenge> list);
    }

    private DoneUseCaseDelegate delegate;

    public void setDelegate(DoneUseCaseDelegate delegate) {
        this.delegate = delegate;
    }

    public void getDoneList(String userId) {
        FirebaseUtil.shared.getReference().child(ApiClient.getMyChallenge(userId))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ArrayList<Challenge> list = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Challenge challenge = ds.getValue(Challenge.class);
                            challenge.setId(ds.getKey());
                            list.add(challenge);
                        }
                        delegate.onSuccessDone(getChallengeByStatus(list).getDoneList());
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        delegate.onFailure(error.getMessage());
                    }
                });
    }

    private MyChallenges getChallengeByStatus(ArrayList<Challenge> list) {
        MyChallenges myChallenges = new MyChallenges(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        for (Challenge challenge : list) {
            if (challenge.getDoneDate() == null) {
                Date dueDate = Utils.convertStringToDate(challenge.getDueDate());
                Date today = new Date();
                if (today.before(dueDate) || today.equals(dueDate)) {
                    challenge.setStatus(Constants.doing);
                    myChallenges.getDoingList().add(challenge);
                } else {
                    challenge.setStatus(Constants.failed);
                    myChallenges.getFailedList().add(challenge);
                }
            } else {
                challenge.setStatus(Constants.done);
                myChallenges.getDoneList().add(challenge);
            }
        }
        return myChallenges;
    }
}
