package fpt.provipluxurylimited.challengefocus.challenge.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.models.ItemStatus;
import fpt.provipluxurylimited.challengefocus.models.ToDoItem;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemHolder>{
    private ArrayList<ToDoItem> list;
    private Context context;
    private ToDoItemClickListener mClickListener;

    public ItemRecyclerAdapter(ArrayList<ToDoItem> list, Context context) {
        this.list = list;
        this.context = context;
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
        ItemStatus status = list.get(position).getStatus();
        String name = list.get(position).getName();
        String date = list.get(position).getDate();

        if (status == ItemStatus.DONE) {
            holder.imgCheckbox.setImageResource(R.drawable.ic_checkbox_checked);
            holder.imgArrow.setVisibility(View.VISIBLE);
        } else {
            holder.imgCheckbox.setImageResource(R.drawable.ic_checkbox_unchecked);
            holder.imgArrow.setVisibility(View.INVISIBLE);
        }

        holder.textViewToDo.setText(name);
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

        public ItemHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgCheckbox = itemView.findViewById(R.id.imgCheckbox);
            imgArrow = itemView.findViewById(R.id.imgArrowOpen);
            textViewToDo = itemView.findViewById(R.id.textViewToDoTitle);

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
