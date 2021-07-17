package fpt.provipluxurylimited.challengefocus.challenge.doing;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import java.util.TimerTask;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.challenge.detail.DetailChallengeActivity;
import fpt.provipluxurylimited.challengefocus.challenge.classes.ChallengeRecyclerAdapter;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.ChallengeStatus;

public class DoingFragment extends Fragment implements ChallengeRecyclerAdapter.ChallengeItemClickListener, DoingPresenter.DoingPresenterDelegate {

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
    Context context;
    FragmentActivity fragmentActivity;

    private DoingPresenter presenter;

    public DoingFragment() {
//        setUp();
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
        return inflater.inflate(R.layout.fragment_doing, container, false);
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
        presenter = new DoingPresenter(new DoingUseCase(), this);
        presenter.setDelegate(this);
    }

    protected void initComponents(View view) {
        context = this.getContext();
        fragmentActivity = this.getActivity();
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerViewDoing);
        setUpRecyclerView();
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



    protected void initData() {
        presenter.getDoingList();
    }

    @Override
    public void responseDoingList(ArrayList<Challenge> list) {
        this.list = list;
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        System.out.println("error: " + error);
    }

    class RefreshTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("refresh done");
            timer.cancel();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onClick(View view, int position) {
        System.out.println("position: " + position);
        Challenge challenge = list.get(position);
        Gson gson = new Gson();
        String challengeString = gson.toJson(challenge);
        Intent intent = new Intent(this.getActivity(), DetailChallengeActivity.class);
        intent.putExtra("challenge", challengeString);
        startActivity(intent);
    }
}