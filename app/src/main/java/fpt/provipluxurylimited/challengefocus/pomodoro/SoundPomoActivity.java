package fpt.provipluxurylimited.challengefocus.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import fpt.provipluxurylimited.challengefocus.R;

public class SoundPomoActivity extends AppCompatActivity {

  ImageView btnBack;
  Button btnConfirm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sound_pomo);
    initComponents();
  }

  private void initComponents() {
    btnBack = findViewById(R.id.btnBack);
    btnBack.setClickable(true);
    clickBack();
    confirm();
  }

  void clickBack() {
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
  }

  void confirm(){
    btnConfirm = findViewById(R.id.btnConfirm);
    btnConfirm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent focusPomoIntent = new Intent(SoundPomoActivity.this, FocusPomoActivity.class);
        startActivity(focusPomoIntent);
      }
    });
  }
}