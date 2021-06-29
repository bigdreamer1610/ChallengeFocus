package fpt.provipluxurylimited.challengefocus.discovery.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.models.Challenge;

public class DiscoveryRecylerAdapter extends RecyclerView.Adapter<DiscoveryRecylerAdapter.SectionHolder> {

    ArrayList<DiscoverySection> list;

    public DiscoveryRecylerAdapter(ArrayList<DiscoverySection> list) {
        this.list = list;
    }

    class SectionHolder extends RecyclerView.ViewHolder {

        TextView header;
        RecyclerView childRecyclerView;
        public SectionHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.textViewHeader);
            childRecyclerView = itemView.findViewById(R.id.recyclerViewDiscoverySection);
        }
    }

    @NonNull
    @NotNull
    @Override
    public SectionHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.discovery_section_cell, parent, false);
        return new SectionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SectionHolder holder, int position) {
        DiscoverySection section = list.get(position);
        String header = section.getSectionName();

        ArrayList<Challenge> items = section.getItems();

        holder.header.setText(header.toString());

        ChildDiscoveryRecyclerAdapter childDiscoveryRecyclerAdapter = new ChildDiscoveryRecyclerAdapter(items);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        holder.childRecyclerView.setLayoutManager(layoutManager);
        holder.childRecyclerView.setAdapter(childDiscoveryRecyclerAdapter);
        holder.childRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
