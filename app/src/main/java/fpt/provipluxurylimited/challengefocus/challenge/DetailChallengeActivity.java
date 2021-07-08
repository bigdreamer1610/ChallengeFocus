package fpt.provipluxurylimited.challengefocus.challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fpt.provipluxurylimited.challengefocus.R;

public class DetailChallengeActivity extends AppCompatActivity {

    Fragment fragmentItem;
    Fragment fragmentNoItem;
    ImageView imageViewBack;
    TextView textViewTitle;
    TextView textViewPercentage;
    FloatingActionButton floatButton;
    EditText editTextName;

    BottomSheetDialog bottomSheetDialog;

    private boolean hasItem = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_challenge);
        initComponents();
    }

    private void initComponents() {
        fragmentItem = (ItemFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragmentItem);
        fragmentNoItem = (NoItemFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragmentNoItem);
        textViewPercentage = findViewById(R.id.textViewPercentage);
        textViewTitle = findViewById(R.id.textViewTitle);
        imageViewBack = findViewById(R.id.btnBack);
        floatButton = findViewById(R.id.floatingButton);
        imageViewBack.setClickable(true);
        updateFragment();
        clickBack();
        clickFloat();
    }

    void clickBack() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void clickFloat() {
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasItem = true;
                updateFragment();
                bottomSheetDialog = new BottomSheetDialog(view.getContext());
                bottomSheetDialog.setContentView(R.layout.add_item);
                AppCompatButton btnAdd = bottomSheetDialog
                        .findViewById(R.id.btnAdd);
                EditText editTextName = bottomSheetDialog.findViewById(R.id.editTextName);
                bottomSheetDialog.show();

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });
            }
        });
    }

    void updateFragment() {
        if (hasItem) {
            fragmentNoItem.getView().setVisibility(View.GONE);
            fragmentItem.getView().setVisibility(View.VISIBLE);
        } else {
            fragmentItem.getView().setVisibility(View.GONE);
            fragmentNoItem.getView().setVisibility(View.VISIBLE);
        }
    }
}