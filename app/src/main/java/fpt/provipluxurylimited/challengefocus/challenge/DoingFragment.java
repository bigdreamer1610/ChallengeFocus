package fpt.provipluxurylimited.challengefocus.challenge;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.challenge.classes.ChallengeRecyclerAdapter;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.ChallengeStatus;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoingFragment extends Fragment implements ChallengeRecyclerAdapter.ChallengeItemClickListener{

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

    public DoingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoingFragment newInstance(String param1, String param2) {
        DoingFragment fragment = new DoingFragment();
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
        return inflater.inflate(R.layout.fragment_doing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        initComponents(view);
        initData();
    }

    protected void initComponents(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerViewDoing);
        adapter = new ChallengeRecyclerAdapter(list, getContext());
        adapter.setItemClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                timer = new Timer();
                timer.schedule(new RefreshTask(), 2000);
//                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    protected void initData() {
        list.add(0, new Challenge("ic_book","Vẽ 1 bức tranh", ChallengeStatus.doing, 50));
        list.add(1, new Challenge("ic_book","Vẽ 1 bức tranh", ChallengeStatus.doing, 30));
        list.add(2, new Challenge("ic_book","Vẽ 1 bức tranh", ChallengeStatus.doing, 30));
        list.add(3, new Challenge("ic_book","Vẽ 1 bức tranh", ChallengeStatus.doing, 30));
        list.add(4, new Challenge("ic_book","Vẽ 1 bức tranh", ChallengeStatus.doing, 30));
        list.add(5, new Challenge("ic_book","Vẽ 1 bức tranh", ChallengeStatus.doing, 30));
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
        Intent intent = new Intent(this.getActivity(), DetailChallengeActivity.class);
        startActivity(intent);
    }
}