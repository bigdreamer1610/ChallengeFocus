package fpt.provipluxurylimited.challengefocus.challenge.doing;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import fpt.provipluxurylimited.challengefocus.helpers.ApiClient;
import fpt.provipluxurylimited.challengefocus.helpers.Constants;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.helpers.Utils;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseUseCaseDelegate;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.ChallengeStatus;
import fpt.provipluxurylimited.challengefocus.models.MyChallenges;

public class DoingUseCase {
    public interface DoingUseCaseDelegate extends BaseUseCaseDelegate {
        void onSuccessDoing(ArrayList<Challenge> list);
    }

    private DoingUseCaseDelegate delegate;

    public void setDelegate(DoingUseCaseDelegate delegate) {
        this.delegate = delegate;
    }

    public void getDoingList(String userId) {
        Log.e("Userid", userId);
        FirebaseUtil.shared.getReference().child(ApiClient.getMyChallenge(userId))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ArrayList<Challenge> list = new ArrayList<>();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            Challenge challenge = ds.getValue(Challenge.class);
                            challenge.setId(ds.getKey());
                            list.add(challenge);
                        }
                        delegate.onSuccessDoing(getChallengeByStatus(list).getDoingList());
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
