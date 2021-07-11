package fpt.provipluxurylimited.challengefocus.profile.feedback;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import fpt.provipluxurylimited.challengefocus.helpers.ApiClient;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseUseCaseDelegate;
import fpt.provipluxurylimited.challengefocus.models.Feedback;

public class FeedbackUseCase {
    public interface FeedbackUseCaseDelegate extends BaseUseCaseDelegate {
        void onSendFeedbackSuccess();
    }

    private FeedbackUseCaseDelegate delegate;

    public void setDelegate(FeedbackUseCaseDelegate delegate) {
        this.delegate = delegate;
    }

    public void sendFeedback(Feedback feedback) {
        FirebaseUtil.shared.getReference().child(ApiClient.feedback).push()
                .setValue(feedback)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        delegate.onSendFeedbackSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        delegate.onFailure(e.getMessage());
                    }
                });
    }

}
