package fpt.provipluxurylimited.challengefocus.challenge.classes;

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
import fpt.provipluxurylimited.challengefocus.models.ChallengeStatus;

public class ChallengeRecyclerAdapter extends RecyclerView.Adapter<ChallengeRecyclerAdapter.MyViewHolder> {

    private ArrayList<Challenge> challengeList;

    public ChallengeRecyclerAdapter(ArrayList<Challenge> challengeList) {
        this.challengeList = challengeList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgViewChallenge;
        private TextView textViewChallengeName;
        private TextView textViewPercentage;
        private ImageView imgViewStatus;

        public MyViewHolder(final View view) {
            super(view);
            imgViewChallenge = view.findViewById(R.id.imageViewChallenge);
            textViewChallengeName = view.findViewById(R.id.textViewCategoryName);
            textViewPercentage = view.findViewById(R.id.textViewPercentage);
            imgViewStatus = view.findViewById(R.id.imageViewStatus);
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
        String image = challengeList.get(position).getImage();
        ChallengeStatus status = challengeList.get(position).getStatus();
        String name = challengeList.get(position).getName();
        int percentage = challengeList.get(position).getPercentage();

        // set
        holder.imgViewChallenge.setImageResource(R.drawable.ic_book);
        switch (status){
            case doing:
                holder.imgViewStatus.setImageResource(R.drawable.ic_doing);
                break;
            case done:
                holder.imgViewStatus.setImageResource(R.drawable.ic_check);
                break;
            case failed:
                holder.imgViewStatus.setImageResource(R.drawable.ic_fail);
                break;
        }
        holder.textViewChallengeName.setText(name);
        holder.textViewPercentage.setText(percentage + "%");

    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }
}