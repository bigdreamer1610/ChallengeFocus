package fpt.provipluxurylimited.challengefocus.discovery.discovery;

import java.util.ArrayList;
import java.util.HashMap;

import fpt.provipluxurylimited.challengefocus.helpers.base.BasePresenterDelegate;
import fpt.provipluxurylimited.challengefocus.models.Category;
import fpt.provipluxurylimited.challengefocus.models.CategoryChallenge;
import fpt.provipluxurylimited.challengefocus.models.DiscoveryResult;

public class DiscoveryListPresenter implements DiscoveryListUseCase.DiscoveryListUseCaseDelegate {

    public interface DiscoveryListPresenterDelegate extends BasePresenterDelegate{
        void responseData(DiscoveryResult result);
    }

    protected DiscoveryListUseCase useCase;
    private DiscoveryListPresenterDelegate delegate;

    public DiscoveryListPresenter(DiscoveryListUseCase useCase, DiscoveryListPresenterDelegate delegate) {
        this.useCase = useCase;
        this.delegate = delegate;
        this.useCase.setDelegate(this);
    }

    public void setDelegate(DiscoveryListPresenterDelegate delegate) {
        this.delegate = delegate;
    }

    public void getDiscoveryList() {
        useCase.getDiscoveryList();
    }

    @Override
    public void onGetCategoryListSuccess(DiscoveryResult result) {
        delegate.responseData(result);
    }

    @Override
    public void onFailure(String message) {
        delegate.showError(message);
    }
}
