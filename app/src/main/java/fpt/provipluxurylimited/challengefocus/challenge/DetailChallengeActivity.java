package fpt.provipluxurylimited.challengefocus.challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fpt.provipluxurylimited.challengefocus.R;

public class DetailChallengeActivity extends AppCompatActivity {

    ImageView imageViewBack;
    TextView textViewTitle;
    TextView textViewPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_challenge);
        initComponents();
    }

    private void initComponents() {
        textViewPercentage = findViewById(R.id.textViewPercentage);
        textViewTitle = findViewById(R.id.textViewTitle);
        imageViewBack = findViewById(R.id.btnBack);

        imageViewBack.setClickable(true);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println("hello");
//                Toast.makeText(this, "You clicked back", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}