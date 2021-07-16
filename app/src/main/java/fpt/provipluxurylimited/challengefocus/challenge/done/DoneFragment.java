package fpt.provipluxurylimited.challengefocus.challenge.done;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Timer;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.challenge.detail.DetailChallengeActivity;
import fpt.provipluxurylimited.challengefocus.challenge.classes.ChallengeRecyclerAdapter;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.ChallengeStatus;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoneFragment extends Fragment implements ChallengeRecyclerAdapter.ChallengeItemClickListener, DonePresenter.DonePresenterDelegate {

    private ArrayList<Challenge> list;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ChallengeRecyclerAdapter adapter;
    Timer timer;
    Context context;

    private DonePresenter presenter;

    public DoneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done, container, false);
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
        presenter = new DonePresenter(new DoneUseCase(), this);
        presenter.setDelegate(this);
    }

    protected void initComponents(View view) {
        context = this.getContext();
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerViewDone);
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
        presenter.getDoneList();

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

    @Override
    public void responseDoingList(ArrayList<Challenge> list) {
        this.list = list;
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showError(String error) {

    }
}