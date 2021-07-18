package fpt.provipluxurylimited.challengefocus.challenge.detail;

import android.net.Uri;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.helpers.base.BasePresenterDelegate;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.ToDoItem;

public class DetailChallengePresenter implements DetailChallengeUseCase.DetailChallengeUseCaseDelegate {
    public interface DetailChallengePresenterDelegate extends BasePresenterDelegate {
        void responseItemList(ArrayList<ToDoItem> list);
        void responseChallengeId(String challengeId);
        void responsePercentage(int percentage);
    }

    protected DetailChallengeUseCase useCase;
    private DetailChallengePresenterDelegate delegate;

    public DetailChallengePresenter(DetailChallengeUseCase useCase, DetailChallengePresenterDelegate delegate) {
        this.useCase = useCase;
        this.delegate = delegate;
        this.useCase.setDelegate(this);
    }

    public void setDelegate(DetailChallengePresenterDelegate delegate) {
        this.delegate = delegate;
    }

    void getItemList(String userId, String challengId) {
        useCase.getToDoItemsList(userId, challengId);
    }

    void addFirstIitem(String userId, Challenge challenge, ToDoItem item) {
        useCase.addNewChallenge(userId, challenge, item);
    }

    void addItem(String userId, String id, ToDoItem item) {
        useCase.addItem(userId, id, item);
    }

    void removeItem(String userId, String challengeId, String itemId) {
        useCase.removeItem(userId, challengeId, itemId);
    }

    void uploadImageToStorage(String userId, String challengeId, ToDoItem item, Uri uri) {
        useCase.uploadImageToStorage(userId, challengeId, item, uri);
    }

    void updatePercentage(String userId, String id, ArrayList<ToDoItem> list) {
        useCase.updateChallengePercentage(userId, id, list);
    }

    void removeEmptyChallenge(String userId, String id) {
        useCase.removeEmptyChallenge(userId, id);
    }

    @Override
    public void onSuccessGetItems(ArrayList<ToDoItem> list) {
        delegate.responseItemList(list);
    }

    @Override
    public void onSuccessAddFirstItem(String challengeId) {
        delegate.responseChallengeId(challengeId);
    }

    @Override
    public void onFailure(String message) {
        delegate.showError(message);
    }

    @Override
    public void onSuccessGetPercentage(int percentage) {
        delegate.responsePercentage(percentage);
    }
}
