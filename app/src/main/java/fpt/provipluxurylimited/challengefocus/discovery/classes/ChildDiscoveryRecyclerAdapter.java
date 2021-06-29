package fpt.provipluxurylimited.challengefocus.discovery.classes;

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
import fpt.provipluxurylimited.challengefocus.models.Challenge;

public class ChildDiscoveryRecyclerAdapter extends RecyclerView.Adapter<ChildDiscoveryRecyclerAdapter.ItemHolder> {

    ArrayList<Challenge> list;

    public ChildDiscoveryRecyclerAdapter(ArrayList<Challenge> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.discovery_item_cell, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemHolder holder, int position) {
        String name = list.get(position).getName();
        String image = list.get(position).getImage();
        holder.textViewName.setText(name);
        holder.imgViewChallenge.setImageResource(R.drawable.ic_cook);
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        ImageView imgViewChallenge;
        TextView textViewName;



        public ItemHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imgViewChallenge = itemView.findViewById(R.id.imageViewDiscovery);
            textViewName = itemView.findViewById(R.id.textViewChallengeName);
        }
    }
}
