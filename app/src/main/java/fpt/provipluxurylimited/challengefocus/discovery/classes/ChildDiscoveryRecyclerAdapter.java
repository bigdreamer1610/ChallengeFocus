package fpt.provipluxurylimited.challengefocus.discovery.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.models.CategoryChallenge;

public class ChildDiscoveryRecyclerAdapter extends RecyclerView.Adapter<ChildDiscoveryRecyclerAdapter.ItemHolder> {

    int section;
    ArrayList<CategoryChallenge> list;
    private ChildClickListener mClickListener;

    public ChildDiscoveryRecyclerAdapter(int section, ArrayList<CategoryChallenge> list) {
        this.section = section;
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

        String title = list.get(position).getTitle();
        holder.textViewName.setText(title);
        Picasso.get().load(list.get(position).getImageUrl()).into(holder.imgViewChallenge);

    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgViewChallenge;
        TextView textViewName;

        public ItemHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imgViewChallenge = itemView.findViewById(R.id.imageViewDiscovery);
            textViewName = itemView.findViewById(R.id.textViewChallengeName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onClickChild(view, getAdapterPosition(), section);
            }

//            mClickListener.onClickChild(view, getAdapterPosition());
            
        }
    }

    public void setChildClickListener(ChildClickListener childClickListener) {
        this.mClickListener = childClickListener;
    }

    public interface ChildClickListener {
        void onClickChild(View view, int position, int section);
    }
}
