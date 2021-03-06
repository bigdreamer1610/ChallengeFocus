package fpt.provipluxurylimited.challengefocus.pomodoro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.helpers.ApiClient;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.models.Pomodoro;


public class HistoryPomoActivity extends AppCompatActivity {

  int ripe_num = 0, rotten_num = 0;
  ImageView btnBack;
  private ArrayList<Pomodoro> list;
  RecyclerView recyclerView;
  PomoRecyclerAdapter adapter;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_history_pomo);
    list = new ArrayList<>();
    initComponents();
    getPomoList();
  }

  private void initComponents() {
    context = this;
    btnBack = findViewById(R.id.btnBack);
    btnBack.setClickable(true);
    clickBack();
    recyclerView = findViewById(R.id.pomo_recycleview);
    setUpRecyclerView();
    getList();
  }

  void clickBack() {
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
  }

  private void drawChart() {
    PieChart pieChart = findViewById(R.id.pieChart);
    pieChart.setUsePercentValues(true);

    Description description = new Description();
    description.setText("T??? l??? C?? chua");
    description.setTextSize(25);
    description.setPosition(710f, 570f);
    pieChart.setDescription(description);

    pieChart.setHoleRadius(50f);
    pieChart.setTransparentCircleRadius(50f);

    float ripe_percent = (float) ripe_num / (ripe_num + rotten_num) * 100;
    float rotten_percent = (float) 100-ripe_percent;

    ArrayList<PieEntry> values = new ArrayList<PieEntry>();
    values.add(new PieEntry(ripe_percent, "Ch??n"));
    values.add(new PieEntry(rotten_percent, "Th???i"));

    PieDataSet dataSet = new PieDataSet(values, "");

    dataSet.setColors(Color.rgb(236, 42, 40), Color.rgb(132, 79, 45));

    PieData data = new PieData(dataSet);

    data.setValueFormatter(new PercentFormatter());
    data.setValueTextSize(28f);
    data.setValueTextColor(Color.WHITE);

    pieChart.setData(data);
    pieChart.setUsePercentValues(true);

    pieChart.animateXY(1000, 1000);

  }

  public void getPomoList() {
    FirebaseUtil.shared.getReference().child(ApiClient.pomodoro)
            .addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<Pomodoro> pomodoros = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                  int id = ds.child("id").getValue(Integer.class);
                  int cycle = ds.child("cycle").getValue(Integer.class);
                  String endDate = ds.child("endDate").getValue(String.class);
                  String mode = ds.child("mode").getValue(String.class);
                  String status = ds.child("status").getValue(String.class);
                  pomodoros.add(new Pomodoro(id, cycle, endDate, mode, status));
                  if(status.equals("RIPE")) {
                    ripe_num ++;
                  } else {
                    rotten_num ++;
                  }
                }
                drawChart();
              }

              @Override
              public void onCancelled(@NonNull @NotNull DatabaseError error) {
              }
            });
  }

  void setUpRecyclerView() {
    adapter = new PomoRecyclerAdapter(list, context);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(adapter);
  }

  public void getList() {
    FirebaseUtil.shared.getReference().child(ApiClient.pomodoro)
            .addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<Pomodoro> listPomo = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                  Pomodoro pomodoro = ds.getValue(Pomodoro.class);
                  listPomo.add(pomodoro);
                }
                list = listPomo;
                adapter.setList(listPomo);
                adapter.notifyDataSetChanged();
              }
              @Override
              public void onCancelled(@NonNull @NotNull DatabaseError error) {
              }
            });
  }
}