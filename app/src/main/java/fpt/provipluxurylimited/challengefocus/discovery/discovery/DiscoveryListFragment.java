package fpt.provipluxurylimited.challengefocus.discovery.discovery;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.discovery.classes.DiscoveryRecylerAdapter;
import fpt.provipluxurylimited.challengefocus.models.DiscoveryResult;
import me.ibrahimsn.lib.CirclesLoadingView;

public class DiscoveryListFragment extends Fragment implements DiscoveryListPresenter.DiscoveryListPresenterDelegate {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    NavController navController;
    SearchView searchView;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    DiscoveryRecylerAdapter discoveryRecylerAdapter;
    CirclesLoadingView loadingView;
    Context context;

    private DiscoveryResult result;
    private DiscoveryListPresenter presenter;

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
        searchView = view.findViewById(R.id.searchView);
        loadingView = view.findViewById(R.id.loadingView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerViewDiscovery);
        setUpRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    void setUpRecyclerView() {
        discoveryRecylerAdapter = new DiscoveryRecylerAdapter(result.getCategoryNames(), result.getList());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(discoveryRecylerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
}