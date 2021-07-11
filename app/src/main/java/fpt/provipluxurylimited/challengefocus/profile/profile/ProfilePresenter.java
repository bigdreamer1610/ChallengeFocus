package fpt.provipluxurylimited.challengefocus.profile.profile;

import fpt.provipluxurylimited.challengefocus.helpers.base.BasePresenterDelegate;
import fpt.provipluxurylimited.challengefocus.models.UserProfile;

public class ProfilePresenter implements ProfileUseCase.ProfileUseCaseDelegate {
    protected ProfileUseCase useCase;
    private ProfilePresenterDelegate delegate;


    public interface ProfilePresenterDelegate extends BasePresenterDelegate {
        void responseData(UserProfile data);
    }

    public ProfilePresenter(ProfileUseCase useCase, ProfilePresenterDelegate delegate) {
        this.useCase = useCase;
        this.delegate = delegate;
        useCase.setDelegate(this);
    }

    public void setDelegate(ProfilePresenterDelegate delegate) {
        this.delegate = delegate;
    }


    public void getUserProfile() {
        useCase.getUserProfile();
    }

    @Override
    public void onGetUserProfileSuccess(UserProfile data) {
        delegate.responseData(data);
    }

    @Override
    public void onFailure(String message) {
        delegate.showError(message);
    }


}
