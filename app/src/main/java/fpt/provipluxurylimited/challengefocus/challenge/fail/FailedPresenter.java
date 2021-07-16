package fpt.provipluxurylimited.challengefocus.challenge.fail;

import android.util.Log;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.helpers.base.BasePresenterDelegate;
import fpt.provipluxurylimited.challengefocus.models.Challenge;

public class FailedPresenter implements FailedUseCase.FailedUseCaseDelegate {

    public interface FailedPresenterDelegate extends BasePresenterDelegate {
        void responseFailedList(ArrayList<Challenge> list);
    }

    protected FailedUseCase useCase;
    private FailedPresenterDelegate delegate;

    public FailedPresenter(FailedUseCase useCase, FailedPresenterDelegate delegate) {
        this.useCase = useCase;
        this.delegate = delegate;
        this.useCase.setDelegate(this);
    }

    public void setDelegate(FailedPresenterDelegate delegate) {
        this.delegate = delegate;
    }

    void getFailedList() {
        useCase.getFailedList();
    }

    @Override
    public void onSuccessFailed(ArrayList<Challenge> list) {
        delegate.responseFailedList(list);
    }

    @Override
    public void onFailure(String message) {
        delegate.showError(message);
    }

}
