package fpt.provipluxurylimited.challengefocus.challenge.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.discovery.classes.DiscoveryItemClickListener;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.ChallengeStatus;

public class ChallengeRecyclerAdapter extends RecyclerView.Adapter<ChallengeRecyclerAdapter.MyViewHolder> {

    private ArrayList<Challenge> challengeList;
    private Context context;
    private ChallengeItemClickListener mChallengeClickListener;


    public ChallengeRecyclerAdapter(ArrayList<Challenge> challengeList, Context context) {
        this.challengeList = challengeList;
        this.context = context;
    }

    public void setList(ArrayList<Challenge> list) {
        this.challengeList = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgViewChallenge;
        private TextView textViewChallengeName;
        private TextView textViewPercentage;
        private ImageView imgViewStatus;
        private TextView textViewDuration;
        private LinearLayout layoutPercentageHolder;

        private DiscoveryItemClickListener itemClickListener;

        public MyViewHolder(final View view) {
            super(view);
            imgViewChallenge = view.findViewById(R.id.imageViewChallenge);
            textViewChallengeName = view.findViewById(R.id.textViewCategoryName);
            textViewPercentage = view.findViewById(R.id.textViewPercentage);
            imgViewStatus = view.findViewById(R.id.imageViewStatus);
            textViewDuration = view.findViewById(R.id.textViewDuration);
            layoutPercentageHolder = view.findViewById(R.id.layoutPercentageHolder);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            itemClickListener.onClick(view, getAdapterPosition(), false);
            if (mChallengeClickListener != null) {
                mChallengeClickListener.onClick(view, getAdapterPosition());
            }
        }
    }
    @NonNull
    @NotNull
    @Override
    public ChallengeRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_item_cell, parent, false);
        return new MyViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChallengeRecyclerAdapter.MyViewHolder holder, int position) {
        String title = challengeList.get(position).getTitle();

        String status = challengeList.get(position).getStatus();
        int percentage = challengeList.get(position).getPercentage();
        String dueDate = challengeList.get(position).getDueDate();
        String doneDate = challengeList.get(position).getDoneDate();
        switch (status) {
            case "doing":
                holder.imgViewStatus.setImageResource(R.drawable.ic_doing);
                holder.layoutPercentageHolder.setVisibility(View.VISIBLE);
                holder.textViewDuration.setVisibility(View.INVISIBLE);
                break;
            case "done":
                holder.imgViewStatus.setImageResource(R.drawable.ic_check);
                holder.textViewDuration.setVisibility(View.VISIBLE);
                holder.layoutPercentageHolder.setVisibility(View.INVISIBLE);
                if (doneDate != null) {
                    holder.textViewDuration.setText(doneDate);
                }
                break;
            case "failed":
                holder.imgViewStatus.setImageResource(R.drawable.ic_fail);
                holder.layoutPercentageHolder.setVisibility(View.VISIBLE);
                holder.textViewDuration.setVisibility(View.INVISIBLE);
                break;

        }
        holder.textViewChallengeName.setText(title);
        holder.textViewPercentage.setText(percentage + "%");

        Picasso.get().load(challengeList.get(position).getImageUrl()).into(holder.imgViewChallenge);
    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }

    public void setItemClickListener(ChallengeItemClickListener itemClickListener) {
        this.mChallengeClickListener = itemClickListener;
    }

    public interface ChallengeItemClickListener {
        void onClick(View view, int position);
    }

}
