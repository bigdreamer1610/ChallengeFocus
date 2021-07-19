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
    ImageView imgCbx1 = findViewById(R.id.imgCbx1);
    ImageView imgCbx2 = findViewById(R.id.imgCbx2);
    ImageView imgCbx3 = findViewById(R.id.imgCbx3);
    ImageView imgCbx4 = findViewById(R.id.imgCbx4);
    ImageView imgCbx5 = findViewById(R.id.imgCbx5);
    ImageView imgCbx6 = findViewById(R.id.imgCbx6);
    ImageView imgCbx7 = findViewById(R.id.imgCbx7);
    ImageView imgCbx8 = findViewById(R.id.imgCbx8);
    ImageView imgCbx9 = findViewById(R.id.imgCbx9);
    int clickedPosition = view.getId();
    System.out.println(clickedPosition);
    switch (clickedPosition) {
      case 2131296892:
        imgCbx1.setImageResource(R.drawable.ic_checkbox_checked);
        imgCbx2.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx3.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx4.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx5.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx6.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx7.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx8.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx9.setImageResource(R.drawable.ic_checkbox_unchecked);
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
        imgCbx1.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx2.setImageResource(R.drawable.ic_checkbox_checked);
        imgCbx3.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx4.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx5.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx6.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx7.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx8.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx9.setImageResource(R.drawable.ic_checkbox_unchecked);
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
        imgCbx1.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx2.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx3.setImageResource(R.drawable.ic_checkbox_checked);
        imgCbx4.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx5.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx6.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx7.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx8.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx9.setImageResource(R.drawable.ic_checkbox_unchecked);
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
        imgCbx1.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx2.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx3.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx4.setImageResource(R.drawable.ic_checkbox_checked);
        imgCbx5.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx6.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx7.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx8.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx9.setImageResource(R.drawable.ic_checkbox_unchecked);
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
        imgCbx1.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx2.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx3.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx4.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx5.setImageResource(R.drawable.ic_checkbox_checked);
        imgCbx6.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx7.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx8.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx9.setImageResource(R.drawable.ic_checkbox_unchecked);
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
        imgCbx1.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx2.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx3.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx4.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx5.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx6.setImageResource(R.drawable.ic_checkbox_checked);
        imgCbx7.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx8.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx9.setImageResource(R.drawable.ic_checkbox_unchecked);
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
      case 2131296898:
        imgCbx1.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx2.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx3.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx4.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx5.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx6.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx7.setImageResource(R.drawable.ic_checkbox_checked);
        imgCbx8.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx9.setImageResource(R.drawable.ic_checkbox_unchecked);
        if (mp == null) {
        } else {
          if(mp.isPlaying()){
            mp.stop();
            mp.release();
          }
        }
        break;
      case 2131296899:
        imgCbx1.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx2.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx3.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx4.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx5.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx6.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx7.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx8.setImageResource(R.drawable.ic_checkbox_checked);
        imgCbx9.setImageResource(R.drawable.ic_checkbox_unchecked);
        if (mp == null) {
        } else {
          if(mp.isPlaying()){
            mp.stop();
            mp.release();
          }
        }
        break;
      case 2131296900:
        imgCbx1.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx2.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx3.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx4.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx5.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx6.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx7.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx8.setImageResource(R.drawable.ic_checkbox_unchecked);
        imgCbx9.setImageResource(R.drawable.ic_checkbox_checked);
        if (mp == null) {
        } else {
          if(mp.isPlaying()){
            mp.stop();
            mp.release();
          }
        }
        break;
      default:
        mp.stop();
        break;
    }
  }
}