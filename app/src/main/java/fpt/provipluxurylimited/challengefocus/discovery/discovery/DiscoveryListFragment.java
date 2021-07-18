package fpt.provipluxurylimited.challengefocus.discovery.discovery;

import android.app.Activity;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.challenge.detail.DetailChallengeActivity;
import fpt.provipluxurylimited.challengefocus.discovery.classes.DiscoveryRecylerAdapter;
import fpt.provipluxurylimited.challengefocus.helpers.Constants;
import fpt.provipluxurylimited.challengefocus.models.CategoryChallenge;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.DiscoveryResult;
import me.ibrahimsn.lib.CirclesLoadingView;

public class DiscoveryListFragment extends Fragment implements DiscoveryListPresenter.DiscoveryListPresenterDelegate, DiscoveryRecylerAdapter.ParentClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    NavController navController;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    DiscoveryRecylerAdapter discoveryRecylerAdapter;
    CirclesLoadingView loadingView;
    Context context;
    Dialog dialog;
    TextView textViewTitle;
    AppCompatButton btnAgree;
    AppCompatButton btnReject;
    private DiscoveryResult result;
    private DiscoveryListPresenter presenter;
    private CategoryChallenge selectedChallenge;
    private Challenge selectedItem;
    Gson gson = new Gson();

    public DiscoveryListFragment() {
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
        return inflater.inflate(R.layout.fragment_discovery_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
        initComponents(view);
        initData();
    }

    void setUp() {
        presenter = new DiscoveryListPresenter(new DiscoveryListUseCase(), this);
        presenter.setDelegate(this);
    }

    private void initComponents(View view) {
        result = new DiscoveryResult(new ArrayList<>(), new HashMap<>());
        context = view.getContext();
        navController = Navigation.findNavController(view);
        recyclerView = view.findViewById(R.id.recyclerViewDiscovery);
        loadingView = view.findViewById(R.id.loadingView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerViewDiscovery);
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
        discoveryRecylerAdapter = new DiscoveryRecylerAdapter(result.getCategoryNames(), result.getList());
        discoveryRecylerAdapter.setParentClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(discoveryRecylerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    void setUpDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirm_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnAgree = dialog.findViewById(R.id.btnAgree);
        btnReject = dialog.findViewById(R.id.btnReject);
        textViewTitle = dialog.findViewById(R.id.dialogTitle);
        textViewTitle.setText(Constants.DialogConstants.confirmMessage);
        btnAgree.setText(Constants.DialogConstants.optionAgree);
        btnReject.setText(Constants.DialogConstants.optionReject);
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDialog();
                startChallenge();
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

    void startChallenge() {
        String challengeString = gson.toJson(selectedItem);
        Intent intent = new Intent(context, DetailChallengeActivity.class);
        intent.putExtra("challenge", challengeString);
        startActivity(intent);
    }

    private void initData() {
        presenter.getDiscoveryList();
    }

    @Override
    public void showError(String error) {
        System.out.println("error: " + error);
    }

    @Override
    public void responseData(DiscoveryResult result) {
        this.result = result;
        discoveryRecylerAdapter.setList(result);
        discoveryRecylerAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        loadingView.clearAnimation();
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void onClickParent(View view, int row, int section) {
        showDialog();
        String sectionName = result.getCategoryNames().get(section);
        selectedChallenge = result.getList().get(sectionName).get(row);
        selectedItem = new Challenge(selectedChallenge.getId(), selectedChallenge.getImageUrl(),0, selectedChallenge.getTitle(), Constants.doing);
    }
}