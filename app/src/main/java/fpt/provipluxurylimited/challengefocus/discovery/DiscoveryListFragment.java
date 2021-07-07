package fpt.provipluxurylimited.challengefocus.discovery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.discovery.classes.DiscoveryRecylerAdapter;
import fpt.provipluxurylimited.challengefocus.discovery.classes.DiscoverySection;
import fpt.provipluxurylimited.challengefocus.models.Challenge;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoveryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoveryListFragment extends Fragment {

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

    private static final String TAG = "List_Discovery";

    private ArrayList<DiscoverySection> list;

    public DiscoveryListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoveryListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoveryListFragment newInstance(String param1, String param2) {
        DiscoveryListFragment fragment = new DiscoveryListFragment();
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
        return inflater.inflate(R.layout.fragment_discovery_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        recyclerView = view.findViewById(R.id.recyclerViewDiscovery);
        searchView = view.findViewById(R.id.searchView);

        setUpAction();
        initData();
        initComponents(view);
    }

    protected void setUpAction() {
    }

    private void initComponents(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewDiscovery);
        DiscoveryRecylerAdapter discoveryRecylerAdapter = new DiscoveryRecylerAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(discoveryRecylerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        list = new ArrayList<>();
        String name1 = "Sống khoẻ mỗi ngày";
        String name2 = "Đẹp hơn mỗi ngày";
        String name3 = "Học thêm một ít";

        ArrayList<Challenge> cha1 = new ArrayList<>();
        ArrayList<Challenge> cha2 = new ArrayList<>();
        ArrayList<Challenge> cha3 = new ArrayList<>();

        cha1.add(new Challenge("ic_book", "Skincare mỗi ngày" ));
        cha1.add(new Challenge("ic_book", "Mặc đẹp mỗi ngày" ));
        list.add(new DiscoverySection(name1, cha1));

        cha2.add(new Challenge("ic_book", "Đẹp xe mỗi " ));
        cha2.add(new Challenge("ic_book", "Yoga mỗi " ));
        cha2.add(new Challenge("ic_book", "Ngủ sớm" ));
        list.add(new DiscoverySection(name2, cha2));

        cha3.add(new Challenge("ic_book", "Một trang sách" ));
        list.add(new DiscoverySection(name3, cha3));

    }

}