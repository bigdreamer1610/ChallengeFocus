package fpt.provipluxurylimited.challengefocus.challenge.fail;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Timer;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.challenge.detail.DetailChallengeActivity;
import fpt.provipluxurylimited.challengefocus.challenge.classes.ChallengeRecyclerAdapter;
import fpt.provipluxurylimited.challengefocus.helpers.Constants;
import fpt.provipluxurylimited.challengefocus.helpers.SaveSharedPreference;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.ChallengeStatus;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FailedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FailedFragment extends Fragment implements ChallengeRecyclerAdapter.ChallengeItemClickListener, FailedPresenter.FailedPresenterDelegate {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Challenge> list;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ChallengeRecyclerAdapter adapter;
    Timer timer;
    NavController navController;
    Context context;
    Dialog dialog;
    TextView textViewTitle;
    AppCompatButton btnAgree;
    AppCompatButton btnReject;

    Gson gson = new Gson();
    private FailedPresenter presenter;
    private Challenge challenge;

    public FailedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FailedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FailedFragment newInstance(String param1, String param2) {
        FailedFragment fragment = new FailedFragment();
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
        return inflater.inflate(R.layout.fragment_failed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
        list = new ArrayList<>();
        initComponents(view);
        initData();
    }

    void setUp() {
        presenter = new FailedPresenter(new FailedUseCase(), this);
        presenter.setDelegate(this);
    }

    protected void initComponents(View view) {
        context = this.getContext();
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerViewFailed);
        setUpRecyclerView();
        setUpDialog();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    void setUpRecyclerView() {
        adapter = new ChallengeRecyclerAdapter(list, getContext());
        adapter.setItemClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    void setUpDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirm_dialog);
        btnAgree = dialog.findViewById(R.id.btnAgree);
        btnReject = dialog.findViewById(R.id.btnReject);
        textViewTitle = dialog.findViewById(R.id.dialogTitle);

        textViewTitle.setText(Constants.DialogConstants.restartMessage);
        btnAgree.setText(Constants.DialogConstants.optionAgreeRestart);
        btnReject.setText(Constants.DialogConstants.optionRejectRestart);

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDialog();
                Challenge newChallenge = new Challenge(challenge.getChallengeId(), challenge.getImageUrl(), 0, challenge.getTitle(), Constants.doing);
                String challengeString = gson.toJson(newChallenge);
                Intent intent = new Intent(context, DetailChallengeActivity.class);
                intent.putExtra("challenge", challengeString);
                startActivity(intent);
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDialog();
            }
        });
    }

    void showDialog() {
        dialog.show();
    }

    void closeDialog() {
        dialog.dismiss();
    }

    protected void initData() {

        presenter.getFailedList(SaveSharedPreference.getUserId(this.getContext()));
    }

    @Override
    public void onClick(View view, int position) {
//        Intent intent = new Intent(this.getActivity(), DetailChallengeActivity.class);
//        startActivity(intent);
        challenge = list.get(position);
        showDialog();
    }

    @Override
    public void responseFailedList(ArrayList<Challenge> list) {
        this.list = list;
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String error) {

    }
}