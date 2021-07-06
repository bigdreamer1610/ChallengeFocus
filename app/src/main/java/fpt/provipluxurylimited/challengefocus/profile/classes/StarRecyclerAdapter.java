package fpt.provipluxurylimited.challengefocus.profile.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fpt.provipluxurylimited.challengefocus.R;

public class StarRecyclerAdapter extends RecyclerView.Adapter<StarRecyclerAdapter.StarHolder> {
    private List<Boolean> list;
    private Context context;
    private ItemClickListener mClickListener;

    public StarRecyclerAdapter(List<Boolean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public StarHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.star_cell, parent, false);
        return new StarHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StarHolder holder, int position) {
        Boolean isSelected = list.get(position).booleanValue();
        if (isSelected) {
            holder.imgStar.setImageResource(R.drawable.ic_star_checked);
        } else {
            holder.imgStar.setImageResource(R.drawable.ic_star_unchecked);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StarHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imgStar;

        public StarHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgStar = itemView.findViewById(R.id.imgStar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onClick(view, getAdapterPosition());
            }
        }
    }

    // convenionce method for getting data at clicked position
    Boolean getItem(int id) {
        return list.get(id);
    }

    // allows click events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    //parent activity/fragment will implement this method to respond to click events
    public interface ItemClickListener {
        void onClick(View view, int position);
    }


}
