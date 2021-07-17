package fpt.provipluxurylimited.challengefocus.challenge.detail;

import android.net.Uri;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.helpers.base.BasePresenterDelegate;
import fpt.provipluxurylimited.challengefocus.models.ToDoItem;

public class DetailChallengePresenter implements DetailChallengeUseCase.DetailChallengeUseCaseDelegate {
    public interface DetailChallengePresenterDelegate extends BasePresenterDelegate {
        void responseItemList(ArrayList<ToDoItem> list);
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

    void getItemList(String userId, String status) {
        useCase.getToDoItemsList(userId, status);
    }

    void addItem(String title) {
        useCase.addItem(title);
    }

    void removeItem(String id) {
        useCase.removeItem(id);
    }

    void uploadImageToStorage(Uri uri, ToDoItem item) {
        useCase.uploadImageToStorage(uri, item);
    }

    @Override
    public void onSuccessGetItems(ArrayList<ToDoItem> list) {
        delegate.responseItemList(list);
    }

    @Override
    public void onSuccessUploadImage(String imageName) {
        System.out.println("hi");
    }

    @Override
    public void onFailure(String message) {
        delegate.showError(message);
    }
}
