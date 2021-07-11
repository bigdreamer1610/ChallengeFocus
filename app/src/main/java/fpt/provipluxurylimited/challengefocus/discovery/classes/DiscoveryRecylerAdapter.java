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
import fpt.provipluxurylimited.challengefocus.models.Category;
import fpt.provipluxurylimited.challengefocus.models.CategoryChallenge;
import fpt.provipluxurylimited.challengefocus.models.Challenge;

public class DiscoveryRecylerAdapter extends RecyclerView.Adapter<DiscoveryRecylerAdapter.SectionHolder> implements ChildDiscoveryRecyclerAdapter.ChildClickListener {

    ArrayList<Category> list;
    private ParentClickListener mClickListener;

    public DiscoveryRecylerAdapter(ArrayList<Category> list) {
        this.list = list;
    }

    class SectionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView header;
        RecyclerView childRecyclerView;
        public SectionHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.textViewHeader);
            childRecyclerView = itemView.findViewById(R.id.recyclerViewDiscoverySection);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
//                mClickListener.onClickParent(view,g);
            }
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
        Category category = list.get(position);
        String header = category.getTitle();
        ArrayList<CategoryChallenge> items = category.getChallenges();

        holder.header.setText(header);

        ChildDiscoveryRecyclerAdapter childDiscoveryRecyclerAdapter = new ChildDiscoveryRecyclerAdapter(items);
        childDiscoveryRecyclerAdapter.setChildClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        holder.childRecyclerView.setLayoutManager(layoutManager);
        holder.childRecyclerView.setAdapter(childDiscoveryRecyclerAdapter);
        holder.childRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setParentClickListener(ParentClickListener parentClickListener) {
        this.mClickListener = parentClickListener;
    }

    public interface ParentClickListener {
//        void onClickParent(View view, int section);
        void onClickParent(View view, int section, int row);
    }

    @Override
    public void onClickChild(View view, int position) {
//        mClickListener.onClickParent(view, get);
    }
}
