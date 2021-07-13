package fpt.provipluxurylimited.challengefocus.profile.profile;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import fpt.provipluxurylimited.challengefocus.helpers.ApiClient;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseUseCaseDelegate;
import fpt.provipluxurylimited.challengefocus.models.UserProfile;

public class ProfileUseCase {
    public interface ProfileUseCaseDelegate extends BaseUseCaseDelegate {
        void onGetUserProfileSuccess(UserProfile data);
    }

    private ProfileUseCaseDelegate delegate;

    public void setDelegate(ProfileUseCaseDelegate delegate) {
        this.delegate = delegate;
    }

    public void getUserProfile() {
        FirebaseUtil.shared.getReference().child(ApiClient.userProfile).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserProfile userProfile = FirebaseUtil.getUserProfile();
                delegate.onGetUserProfileSuccess(userProfile);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                delegate.onFailure(error.getMessage());
            }
        });
    }
}
