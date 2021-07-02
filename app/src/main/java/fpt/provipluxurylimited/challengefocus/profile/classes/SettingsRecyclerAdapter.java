package fpt.provipluxurylimited.challengefocus.profile.classes;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.models.SettingsItem;

public class SettingsRecyclerAdapter extends RecyclerView.Adapter<SettingsRecyclerAdapter.SettingsHolder> {

    private ArrayList<SettingsItem> list;

    public SettingsRecyclerAdapter(ArrayList<SettingsItem> list) {
        this.list = list;
    }

    public class SettingsHolder extends RecyclerView.ViewHolder {
        public SettingsHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @NotNull
    @Override
    public SettingsHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @NonNull
    @NotNull
    @Override

    @Override
    public void onBindViewHolder(@NonNull @NotNull SettingsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
