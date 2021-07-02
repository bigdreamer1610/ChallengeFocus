package fpt.provipluxurylimited.challengefocus.profile.classes;

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
import fpt.provipluxurylimited.challengefocus.models.SettingType;
import fpt.provipluxurylimited.challengefocus.models.SettingsItem;

public class SettingsRecyclerAdapter extends RecyclerView.Adapter<SettingsRecyclerAdapter.SettingsHolder> {

    private ArrayList<SettingsItem> list;

    public SettingsRecyclerAdapter(ArrayList<SettingsItem> list) {
        this.list = list;
    }

    public class SettingsHolder extends RecyclerView.ViewHolder {
        private ImageView imgIcon;
        private TextView textViewSettingsItem;

        public SettingsHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            textViewSettingsItem = itemView.findViewById(R.id.textViewSetting);
        }
    }

    @NonNull
    @NotNull
    @Override
    public SettingsHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_item_cell, parent, false);
        return new SettingsHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SettingsHolder holder, int position) {
        SettingType type = list.get(position).getType();

        switch (type) {
            case CONTACT:
                holder.textViewSettingsItem.setText("Gửi phản hồi về app");
                holder.imgIcon.setImageResource(R.drawable.ic_feedback);
            case FEEDBACK:
                holder.textViewSettingsItem.setText("Liên hệ với chúng tôi");
                holder.imgIcon.setImageResource(R.drawable.ic_contact);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
