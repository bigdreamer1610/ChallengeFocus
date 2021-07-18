package fpt.provipluxurylimited.challengefocus.challenge.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.models.ToDoItem;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemHolder>{
    private ArrayList<ToDoItem> list;
    private Context context;
    private ToDoItemClickListener mClickListener;

    public ItemRecyclerAdapter(ArrayList<ToDoItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(ArrayList<ToDoItem> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_cell, parent, false);
        return new ItemHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemHolder holder, int position) {
        Boolean isDone = list.get(position).getIsDone();
        String title = list.get(position).getTitle();
        String date = list.get(position).getDate();
        Boolean isExpand = list.get(position).getExpanded();
        if (isDone) {
            holder.textViewDate.setText(list.get(position).getDate());
            Picasso.get().load(list.get(position).getImageUrl()).into(holder.imgResult);
        }

        holder.imgArrow.setVisibility(isDone ? View.VISIBLE : View.INVISIBLE);
        holder.imgCheckbox.setImageResource(isDone ? R.drawable.ic_checkbox_unchecked : R.drawable.ic_checkbox_checked);
        holder.textViewToDo.setText(title);
        holder.layoutDateHolder.setVisibility(isExpand ? View.VISIBLE : View.GONE);
        holder.layoutImageHolder.setVisibility(isExpand ? View.VISIBLE : View.GONE);
        holder.imgArrow.setImageResource(isExpand ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_right);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imgCheckbox;
        private ImageView imgArrow;
        private TextView textViewToDo;
        private RelativeLayout layoutImageHolder;
        private LinearLayout layoutDateHolder;
        private ImageView imgResult;
        private TextView textViewDate;

        public ItemHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgCheckbox = itemView.findViewById(R.id.imgCbx1);
            imgArrow = itemView.findViewById(R.id.imgArrowOpen);
            textViewToDo = itemView.findViewById(R.id.txt1);
            layoutImageHolder = itemView.findViewById(R.id.layoutImageHolder);
            layoutDateHolder = itemView.findViewById(R.id.layoutDateHolder);
            imgResult = itemView.findViewById(R.id.imageViewResult);
            textViewDate = itemView.findViewById(R.id.textViewDate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onclick(view, getAdapterPosition());
            }
        }
    }


    public void setItemClickListener(ToDoItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ToDoItemClickListener {
        void onclick(View view, int position);
    }
}
