package fpt.provipluxurylimited.challengefocus.challenge.doing;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.helpers.base.BasePresenterDelegate;
import fpt.provipluxurylimited.challengefocus.models.Challenge;

public class DoingPresenter implements DoingUseCase.DoingUseCaseDelegate {
    public interface DoingPresenterDelegate extends BasePresenterDelegate {
        void responseDoingList(ArrayList<Challenge> list);
    }

    protected DoingUseCase useCase;
    private DoingPresenterDelegate delegate;

    public DoingPresenter(DoingUseCase useCase, DoingPresenterDelegate delegate) {
        this.useCase = useCase;
        this.delegate = delegate;
        useCase.setDelegate(this);
    }

    public void setDelegate(DoingPresenterDelegate delegate) {
        this.delegate = delegate;
    }

    void getDoingList() {
        useCase.getDoingList();
    }

    @Override
    public void onSuccessDoing(ArrayList<Challenge> list) {
        delegate.responseDoingList(list);
    }

    @Override
    public void onFailure(String message) {
        delegate.showError(message);
    }
}
