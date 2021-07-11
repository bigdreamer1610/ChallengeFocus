package fpt.provipluxurylimited.challengefocus.profile.feedback;

import fpt.provipluxurylimited.challengefocus.helpers.base.BasePresenterDelegate;
import fpt.provipluxurylimited.challengefocus.models.Feedback;

public class FeedbackPresenter implements FeedbackUseCase.FeedbackUseCaseDelegate {

    public interface FeedbackPresenterDelegate extends BasePresenterDelegate {
        void sendFeedbackSuccess();
    }
    protected FeedbackUseCase useCase;
    private FeedbackPresenterDelegate delegate;

    public FeedbackPresenter(FeedbackUseCase useCase, FeedbackPresenterDelegate delegate) {
        this.useCase = useCase;
        this.delegate = delegate;
        useCase.setDelegate(this);
    }

    public void setDelegate(FeedbackPresenterDelegate delegate) {
        this.delegate = delegate;
    }

    public void sendFeedback(Feedback feedback) {
        useCase.sendFeedback(feedback);
    }

    @Override
    public void onSendFeedbackSuccess() {
        delegate.sendFeedbackSuccess();
    }

    @Override
    public void onFailure(String message) {
        delegate.showError(message);
    }

}
