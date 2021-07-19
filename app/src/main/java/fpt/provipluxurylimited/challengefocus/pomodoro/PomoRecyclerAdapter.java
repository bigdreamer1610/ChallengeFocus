package fpt.provipluxurylimited.challengefocus.pomodoro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.models.Pomodoro;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class PomoRecyclerAdapter extends RecyclerView.Adapter<PomoRecyclerAdapter.PomoHolder>{

  private ArrayList<Pomodoro> list;
  private Context context;

  public PomoRecyclerAdapter(ArrayList<Pomodoro> list, Context context) {
    this.list = list;
    this.context = context;
  }

  public void setList(ArrayList<Pomodoro> list) {
    this.list = list;
  }

  @NonNull
  @NotNull
  @Override
  public PomoRecyclerAdapter.PomoHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.pomo_cell, parent, false);
    return new PomoRecyclerAdapter.PomoHolder(cell);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull PomoRecyclerAdapter.PomoHolder holder, int position) {
    String date = list.get(position).getEndDate();
    holder.txtEndDate.setText(date);
    String status = list.get(position).getStatus();
    int cycle = list.get(position).getCycle();


    switch (status) {
      case "RIPE":
        holder.txtCycle.setText("Cà chua chín thơm");
        holder.imgStatus.setImageResource(R.drawable.ic_ripe_pomo);
        break;
      case "ROTTEN":
        holder.txtCycle.setText("Thối sau " + cycle + " chu kỳ");
        holder.imgStatus.setImageResource(R.drawable.ic_rotten_pomo);
        break;
      default:
        holder.txtCycle.setText("Không rõ tình trạng cà chua");
        holder.imgStatus.setImageResource(R.drawable.ic_ripe_pomo);
        break;
    }
  }

  @Override
  public int getItemCount() {
    return list.size();
  }


  public class PomoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView txtEndDate;
    private TextView txtCycle;
    private ImageView imgStatus;

    public PomoHolder(@NonNull @NotNull View itemView) {
      super(itemView);
      txtEndDate = itemView.findViewById(R.id.txtEndDate);
      txtCycle = itemView.findViewById(R.id.txtCycle);
      imgStatus = itemView.findViewById(R.id.imgStatus);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
  }
}
