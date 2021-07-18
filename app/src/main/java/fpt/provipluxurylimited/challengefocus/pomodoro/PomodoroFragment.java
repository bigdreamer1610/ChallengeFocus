package fpt.provipluxurylimited.challengefocus.pomodoro;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.profile.feedback.FeedbackActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PomodoroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PomodoroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentActivity fragmentActivity;
    Dialog dialog;
    Button btnMusic;
    Button btnSilent;

    public PomodoroFragment() {
        // Required empty public constructor
    }
    private void initComponent(View view) {
        fragmentActivity = this.getActivity();
        setUpDialog();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PomodoroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PomodoroFragment newInstance(String param1, String param2) {
        PomodoroFragment fragment = new PomodoroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pomodoro, container, false);
    }

    void setUpDialog() {
        dialog = new Dialog(fragmentActivity);
        dialog.setContentView(R.layout.music_dialog);
        btnMusic = dialog.findViewById(R.id.btnMusic);
        btnSilent = dialog.findViewById(R.id.btnSilent);
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent soundPomoIntent = new Intent(getActivity(), SoundPomoActivity.class);
                startActivity(soundPomoIntent);
            }
        });

        btnSilent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent focusPomoIntent = new Intent(getActivity(), FocusPomoActivity.class);
                startActivity(focusPomoIntent);
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initComponent(view);
        Button btnAbout = getView().findViewById(R.id.btnAbout);
        Button btnStart = getView().findViewById(R.id.btnStart);
        Button btnHistory = getView().findViewById(R.id.btnHistory);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutPomoIntent = new Intent(getActivity(), AboutPomoActivity.class);
                startActivity(aboutPomoIntent);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyPomoIntent = new Intent(getActivity(), HistoryPomoActivity.class);
                startActivity(historyPomoIntent);
            }
        });

    }
}
