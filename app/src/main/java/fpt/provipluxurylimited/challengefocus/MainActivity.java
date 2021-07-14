package fpt.provipluxurylimited.challengefocus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import fpt.provipluxurylimited.challengefocus.authen.AuthenticationActivity;
import fpt.provipluxurylimited.challengefocus.challenge.ChallengeFragment;
import fpt.provipluxurylimited.challengefocus.discovery.DiscoveryFragment;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.pomodoro.PomodoroFragment;
import fpt.provipluxurylimited.challengefocus.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    ChipNavigationBar menu;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents(savedInstanceState);
        initData();
    }

    // TO-DO: solve re-create fragment problem
    protected void initComponents(Bundle savedInstanceState) {
        menu = findViewById(R.id.chipMenu);

        menu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.itemExplore:
                        fragment = new DiscoveryFragment();
                        break;
                    case R.id.itemChallenge:
                        fragment = new ChallengeFragment();
                        break;
                    case R.id.itemPomo:
                        fragment = new PomodoroFragment();
                        break;
                    case R.id.itemProfile:
                        fragment = new ProfileFragment();
                        break;
                }

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, fragment)
                            .commit();
                } else {
                    Log.e(TAG, "Error in creating fragment");
                }
            }
        });
        if (savedInstanceState == null) {
            menu.setItemSelected(R.id.itemExplore, true);
        }
    }


    protected void initData() {

        //initFirebaseListener();
    }

    private void initFirebaseListener() {
        FirebaseUtil.user = FirebaseAuth.getInstance().getCurrentUser();
        if (FirebaseUtil.user == null) {
            Intent myIntent = new Intent(this, AuthenticationActivity.class);
            startActivity(myIntent);
        } else {
            configureGoogleSignIn();
        }
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
}