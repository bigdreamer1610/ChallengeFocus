package fpt.provipluxurylimited.challengefocus.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import fpt.provipluxurylimited.challengefocus.R;

public class AboutPomoActivity extends AppCompatActivity {

  ImageView btnBack;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about_pomo);
    initComponents();
  }

  private void initComponents() {
    btnBack = findViewById(R.id.btnBack);
    btnBack.setClickable(true);
    clickBack();
  }

  void clickBack() {
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
  }
}