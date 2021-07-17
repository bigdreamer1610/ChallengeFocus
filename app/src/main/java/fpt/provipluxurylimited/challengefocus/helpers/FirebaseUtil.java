package fpt.provipluxurylimited.challengefocus.helpers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import fpt.provipluxurylimited.challengefocus.MainActivity;
import fpt.provipluxurylimited.challengefocus.models.Category;
import fpt.provipluxurylimited.challengefocus.models.CategoryChallenge;
import fpt.provipluxurylimited.challengefocus.models.UserProfile;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class FirebaseUtil {
    public static FirebaseUtil shared = new FirebaseUtil();

    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static FirebaseStorage mFirebaseStorage;
    public static StorageReference mStorageReference;

    // [START declare_auth]
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseAuth.AuthStateListener mAuthStateListener;
    public static GoogleSignInClient mGoogleSignInClient;
    public static FirebaseUser user;
    // [END declare_auth]

    public static final int RC_SIGN_IN = 9001;

    //create a private constructor to avoid this class being instantiated
    private FirebaseUtil() {
        if (user == null) {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseDatabase.setPersistenceEnabled(true);
            mDatabaseReference = FirebaseDatabase.getInstance(Constants.firebaseURL).getReference();
            mFirebaseAuth = FirebaseAuth.getInstance();
        }
    }

    public DatabaseReference getReference() {
        return mDatabaseReference;
    }

    public static UserProfile getUserProfile() {
        UserProfile userProfile = null;
        if (user != null) {
            UserInfo profile = user.getProviderData().get(0);
            // Id of the provider (ex: google.com)
            String providerId = profile.getProviderId();
            // UID specific to the provider
            String uid = profile.getUid();
            // Name, email address, and profile photo Url
            String name = profile.getDisplayName();
            String email = profile.getEmail();
            Uri photoUrl = profile.getPhotoUrl();

            userProfile = readUserProfileFromFb(uid);
            if (userProfile == null) {
                userProfile = new UserProfile(name, Objects.requireNonNull(photoUrl).toString(), "Hello world! ^^");
                writeNewUser(uid, userProfile);
            }

            Log.d("User Profile", userProfile.toString());
        }
        return userProfile;
    }

    private static UserProfile readUserProfileFromFb(String uid) {
        UserProfile userProfile = null;
        mDatabaseReference.child(ApiClient.getUserProfileById(uid));
        Task<DataSnapshot> dataSnapshotTask = mDatabaseReference.child(ApiClient.getUserProfileById(uid)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });

        while (!dataSnapshotTask.isComplete()) {
        }
        userProfile = dataSnapshotTask.getResult().getValue(UserProfile.class);
        return userProfile;
    }

    private static void writeNewUser(String uid, UserProfile userProfile) {
        mDatabaseReference.child(ApiClient.getUserProfileById(uid)).setValue(userProfile);
    }

}
