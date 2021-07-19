package fpt.provipluxurylimited.challengefocus.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import fpt.provipluxurylimited.challengefocus.R;

public class SoundPomoActivity extends AppCompatActivity {

  ImageView btnBack;
  Button btnConfirm;
  MediaPlayer mp;

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

  private void clickBack() {
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
  }

  private void confirm(){
    btnConfirm = findViewById(R.id.btnConfirm);
    btnConfirm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent focusPomoIntent = new Intent(SoundPomoActivity.this, FocusPomoActivity.class);
        startActivity(focusPomoIntent);
      }
    });
  }

  public void playSoundDemo(View view){
    int clickedPosition = view.getId();
    switch (clickedPosition) {
      case 2131296892:
        if (mp == null) {
        } else {
          if(mp.isPlaying()){
            mp.stop();
            mp.release();
          }
        }
        mp = MediaPlayer.create(this, R.raw.classroom);
        mp.setLooping(true);
        mp.start();
        break;
      case 2131296893:
        if (mp == null) {
        } else {
          if(mp.isPlaying()){
            mp.stop();
            mp.release();
          }
        }
        mp = MediaPlayer.create(this, R.raw.clock);
        mp.setLooping(true);
        mp.start();
        break;
      case 2131296894:
        if (mp == null) {
        } else {
          if(mp.isPlaying()){
            mp.stop();
            mp.release();
          }
        }
        mp = MediaPlayer.create(this, R.raw.lake);
        mp.setLooping(true);
        mp.start();
        break;
      case 2131296895:
        if (mp == null) {
        } else {
          if(mp.isPlaying()){
            mp.stop();
            mp.release();
          }
        }
        mp = MediaPlayer.create(this, R.raw.ocean);
        mp.setLooping(true);
        mp.start();
        break;
      case 2131296896:
        if (mp == null) {
        } else {
          if(mp.isPlaying()){
            mp.stop();
            mp.release();
          }
        }
        mp = MediaPlayer.create(this, R.raw.rain);
        mp.setLooping(true);
        mp.start();
        break;
      case 2131296897:
        if (mp == null) {
        } else {
          if(mp.isPlaying()){
            mp.stop();
            mp.release();
          }
        }
        mp = MediaPlayer.create(this, R.raw.rain2);
        mp.setLooping(true);
        mp.start();
        break;
      default:
        mp.stop();
        break;
    }
  }
}