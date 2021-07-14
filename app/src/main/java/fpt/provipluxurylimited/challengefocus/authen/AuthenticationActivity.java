package fpt.provipluxurylimited.challengefocus.authen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import fpt.provipluxurylimited.challengefocus.MainActivity;
import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.helpers.Constants;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;

public class AuthenticationActivity extends AppCompatActivity {
    public static final String TAG = "GoogleActivity";

    private Button btnLogin;
    //    private ImageView btnBack;
    @Override
    public void onBackPressed() {
        // do something on back.
//        Intent returnIntent = new Intent();
//        Bundle mBundle = new Bundle();
//        mBundle.putParcelable("user", FirebaseUtil.user);
//        returnIntent.putExtras(mBundle);
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();
//        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_authentication);

        configureGoogleSignIn();
        btnLogin = (Button) findViewById(R.id.btnLogin);
//        btnBack = (ImageView) findViewById(R.id.backButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });


    }

    private void signIn() {
        Intent signInIntent = FirebaseUtil.mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, FirebaseUtil.RC_SIGN_IN);
        Log.d("SignIn", ": " + FirebaseUtil.user);
    }

    public void signOut() {
        Log.d("sign out", "Singing out");
        FirebaseAuth.getInstance().signOut();
//        FirebaseUtil.mGoogleSignInClient.signOut();
        FirebaseUtil.mFirebaseAuth.removeAuthStateListener(FirebaseUtil.mAuthStateListener);

        //TODO: Test
        Intent myIntent = new Intent(this, AuthenticationActivity.class);
        startActivity(myIntent);

    }

    private void configureGoogleSignIn() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        FirebaseUtil.mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FirebaseUtil.mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == FirebaseUtil.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d(TAG, "Google sign in failed: " + e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseUtil.mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUtil.user = FirebaseUtil.mFirebaseAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Intent returnIntent = new Intent();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("user", user);
        returnIntent.putExtras(mBundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}