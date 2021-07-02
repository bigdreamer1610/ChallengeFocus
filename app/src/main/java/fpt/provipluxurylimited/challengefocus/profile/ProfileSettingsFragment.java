package fpt.provipluxurylimited.challengefocus.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.models.SettingType;
import fpt.provipluxurylimited.challengefocus.models.SettingsItem;
import fpt.provipluxurylimited.challengefocus.profile.classes.SettingsRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSettingsFragment extends Fragment {

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

    private ArrayList<SettingsItem> list;

    public ProfileSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSettingsFragment newInstance(String param1, String param2) {
        ProfileSettingsFragment fragment = new ProfileSettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    }

    private void initComponents(View view) {
        btnLogout = view.findViewById(R.id.btnLogout);
        recyclerView = view.findViewById(R.id.recyclerViewSettings);
        imageView = view.findViewById(R.id.imageViewProfile);
        adapter = new SettingsRecyclerAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new SettingsItem(SettingType.FEEDBACK));
        list.add(new SettingsItem((SettingType.CONTACT)));
    }
}