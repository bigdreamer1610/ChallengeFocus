package fpt.provipluxurylimited.challengefocus.challenge.done;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.helpers.base.BasePresenterDelegate;
import fpt.provipluxurylimited.challengefocus.models.Challenge;

public class DonePresenter implements DoneUseCase.DoneUseCaseDelegate {


    public interface DonePresenterDelegate extends BasePresenterDelegate {
        void responseDoingList(ArrayList<Challenge> list);
    }

    protected DoneUseCase useCase;
    private DonePresenterDelegate delegate;

    public DonePresenter(DoneUseCase useCase, DonePresenterDelegate delegate) {
        this.useCase = useCase;
        this.delegate = delegate;
        this.useCase.setDelegate(this);
    }

    public void setDelegate(DonePresenterDelegate delegate) {
        this.delegate = delegate;
    }

    public void getDoneList(String userId) {
        useCase.getDoneList(userId);
    }

    @Override
    public void onSuccessDone(ArrayList<Challenge> list) {
        delegate.responseDoingList(list);
    }

    @Override
    public void onFailure(String message) {
        delegate.showError(message);
    }

}
