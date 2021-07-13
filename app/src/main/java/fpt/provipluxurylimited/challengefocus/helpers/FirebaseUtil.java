package fpt.provipluxurylimited.challengefocus.helpers;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    private static Activity caller;


    //create a private constructor to avoid this class being instantiated
    private FirebaseUtil() {
        if (user == null) {
            mDatabaseReference = FirebaseDatabase.getInstance(Constants.firebaseURL).getReference();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
        }
    }

    public DatabaseReference getReference() {
        return mDatabaseReference;
    }

}
