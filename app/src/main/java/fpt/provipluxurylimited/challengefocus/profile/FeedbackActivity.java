package fpt.provipluxurylimited.challengefocus.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.profile.classes.StarRecyclerAdapter;

public class FeedbackActivity extends AppCompatActivity implements StarRecyclerAdapter.ItemClickListener {

    private List<Boolean> list;
    RecyclerView recyclerView;
    StarRecyclerAdapter adapter;
    ImageView btnBack;
    NavController navController;

    private int starPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initData();
        initComponents();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(Boolean.FALSE);
        list.add(Boolean.FALSE);
        list.add(Boolean.FALSE);
        list.add(Boolean.FALSE);
        list.add(Boolean.FALSE);
    }

    private void initComponents() {
        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recyclerViewStar);
        adapter = new StarRecyclerAdapter(list, this);
        adapter.setClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        btnBack.setClickable(true);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                navController.popBackStack();
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        for(int i = 0; i < list.size(); i++) {
            if (i <= position) {
                list.set(i, true);
            } else {
                list.set(i, false);
            }
        }
        adapter.notifyDataSetChanged();
    }
}