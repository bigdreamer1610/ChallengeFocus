
package fpt.provipluxurylimited.challengefocus.challenge;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.challenge.classes.ItemRecyclerAdapter;
import fpt.provipluxurylimited.challengefocus.challenge.detail.DetailChallengeActivity;
import fpt.provipluxurylimited.challengefocus.models.ItemStatus;
import fpt.provipluxurylimited.challengefocus.models.ToDoItem;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemFragment extends Fragment implements ItemRecyclerAdapter.ToDoItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<ToDoItem> list;
    RecyclerView recyclerView;
    ItemRecyclerAdapter adapter;


    public ItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemFragment newInstance(String param1, String param2) {
        ItemFragment fragment = new ItemFragment();
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
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initComponent(view);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new ToDoItem("Hi bitch", "10-12-1222",false, "ho",1));
    }

    public void setList(ArrayList<ToDoItem> list) {
        this.list = list;
        this.list = list;
        adapter.setList(list);
        System.out.println("size: " + this.list.size());
//        adapter.set
        adapter.notifyDataSetChanged();
    }

    private void initComponent(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewItem);
        adapter = new ItemRecyclerAdapter(list, this.getContext());
        adapter.setItemClickListener(this);
;        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    list.remove(viewHolder.getAdapterPosition());
//                    getActivity().
                    adapter.notifyDataSetChanged();
                    ((DetailChallengeActivity) getActivity()).updateData(list);
                case ItemTouchHelper.RIGHT:
                    break;
            }
        }
    };

    @Override
    public void onclick(View view, int position) {
        if (list.get(position).getIsDone()) {
            list.get(position).setExpanded(!list.get(position).getExpanded());
            adapter.setList(list);
            adapter.notifyDataSetChanged();
        }
    }
}