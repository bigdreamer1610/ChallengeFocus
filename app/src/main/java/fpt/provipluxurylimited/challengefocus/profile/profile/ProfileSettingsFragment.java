package fpt.provipluxurylimited.challengefocus.profile.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.authen.AuthenticationActivity;
import fpt.provipluxurylimited.challengefocus.helpers.ApiClient;
import fpt.provipluxurylimited.challengefocus.helpers.Constants;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.helpers.SaveSharedPreference;
import fpt.provipluxurylimited.challengefocus.models.SettingType;
import fpt.provipluxurylimited.challengefocus.models.SettingsItem;
import fpt.provipluxurylimited.challengefocus.models.UserProfile;
import fpt.provipluxurylimited.challengefocus.profile.classes.SettingsRecyclerAdapter;
import fpt.provipluxurylimited.challengefocus.profile.feedback.FeedbackActivity;

public class ProfileSettingsFragment extends Fragment implements SettingsRecyclerAdapter.ItemClickListener, ProfilePresenter.ProfilePresenterDelegate {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SettingsRecyclerAdapter adapter;
    RecyclerView recyclerView;
    Button btnLogout;
    CircleImageView imageView;
    NavController navController;
    TextView textViewProfileName;
    EditText textViewCaption;
    Context context;

    private ArrayList<SettingsItem> list;
    private DatabaseReference mDatabase;

    protected ProfilePresenter presenter;

    public ProfileSettingsFragment() {
        this.presenter = new ProfilePresenter(new ProfileUseCase(), this);
        presenter.setDelegate(this);
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initComponents(view);
        setUpProfile();
    }

    private void initComponents(View view) {
        context = this.getContext();
        textViewProfileName = view.findViewById(R.id.textViewProfileName);
        textViewCaption = view.findViewById(R.id.textViewCaption);
        navController = Navigation.findNavController(view);
        btnLogout = view.findViewById(R.id.btnLogout);
        recyclerView = view.findViewById(R.id.recyclerViewSettings);
        imageView = view.findViewById(R.id.imageViewProfile);
        adapter = new SettingsRecyclerAdapter(list);
        adapter.setClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        editCaption();
        clickLogout();
    }


    private void initData() {
        list = new ArrayList<>();
        list.add(new SettingsItem(SettingType.FEEDBACK));
        list.add(new SettingsItem((SettingType.CONTACT)));
        //presenter.getUserProfile(pref.getString(Constants.userId, "id1"));
    }

    private void editCaption() {
        textViewCaption.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                // Update Caption
                String newCaption = textViewCaption.getText().toString().trim();
                presenter.setCaption(SaveSharedPreference.getUserId(context), newCaption);
                return true;
            }
            return false;
        });

    }

    private void setUpProfile() {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewProfileName.setText(SaveSharedPreference.getName(context));
                textViewCaption.setText(SaveSharedPreference.getCaption(context));
                Picasso.get().load(SaveSharedPreference.getImageUrl(context)).into(imageView);
            }
        });
    }

    void clickLogout() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    public void signOut() {
        Log.d("sign out", "Singing out");
        FirebaseAuth.getInstance().signOut();
        FirebaseUtil.mGoogleSignInClient.signOut();
        FirebaseUtil.mFirebaseAuth.removeAuthStateListener(FirebaseUtil.mAuthStateListener);
        SaveSharedPreference.removeAll(this.getContext());
        Intent myIntent = new Intent(this.getContext(), AuthenticationActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View view, int position) {
        switch (position) {
            case 1:
                Intent feedbackIntent = new Intent(this.getActivity(), FeedbackActivity.class);
                startActivity(feedbackIntent);
            default:
                return;
        }
    }


    @Override
    public void responseData(UserProfile data) {

    }

    @Override
    public void showError(String error) {
        System.out.println("response error: " + error);
    }

    @Override
    public void responseCaption(String caption) {
        textViewCaption.setText(caption);
        SaveSharedPreference.setCaption(context, caption);
    }
}
