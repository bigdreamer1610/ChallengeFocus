package fpt.provipluxurylimited.challengefocus.challenge.classes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import fpt.provipluxurylimited.challengefocus.challenge.doing.DoingFragment;
import fpt.provipluxurylimited.challengefocus.challenge.done.DoneFragment;
import fpt.provipluxurylimited.challengefocus.challenge.fail.FailedFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new DoneFragment();
            case 2:
                return new FailedFragment();
        }
        return new DoingFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
