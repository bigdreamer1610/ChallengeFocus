package fpt.provipluxurylimited.challengefocus.pomodoro;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import fpt.provipluxurylimited.challengefocus.MainActivity;
import fpt.provipluxurylimited.challengefocus.R;

public class FocusPomoActivity extends AppCompatActivity {

  Button btnGiveup;
  private static int notificationId = 101;
  private final String NOTIFICATION_CHANNEL = "My channel";
//  private static final long FOCUS_TIME_IN_MILLIS = 1500000;
//  private static final long BREAK_TIME_IN_MILLIS = 300000;
  private static final long FOCUS_TIME_IN_MILLIS = 15000;
  private static final long BREAK_TIME_IN_MILLIS = 8500;

  private int cycle = 1;
  private boolean isFocus;
  private boolean pomodoring;
  private TextView txtCountDown;
  private TextView textMode;
  private TextView textCycle;
  private CountDownTimer mCountDownFocusTimer;
  private CountDownTimer mCountDownBreakTimer;
  private long mFocusTimeLeftInMillis;
  private long mBreakTimeLeftInMillis;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_focus_pomo);
    initComponents();

    txtCountDown = findViewById(R.id.txtCountdown);
    textMode = findViewById(R.id.textMode);
    textCycle = findViewById(R.id.textCycle);
    startFocusTimer();
  }

  private void initComponents() {
    btnGiveup = findViewById(R.id.btnGiveup);
    btnGiveup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pomodoring = false;
        Intent historyPomoIntent = new Intent(FocusPomoActivity.this, HistoryPomoActivity.class);
        startActivity(historyPomoIntent);
      }
    });
  }

  private void startFocusTimer() {
    if(cycle>4){
      finishPomodoro();
    } else {
      mFocusTimeLeftInMillis = FOCUS_TIME_IN_MILLIS;
      mCountDownFocusTimer = new CountDownTimer(mFocusTimeLeftInMillis, 1000) {
        @Override
        public void onTick(long millisFocusUntilFinished) {
          mFocusTimeLeftInMillis = millisFocusUntilFinished;
          pomodoring = true;
          isFocus = true;
          updateTextMode();
          updateFocusCountDownText();
        }
        @Override
        public void onFinish() {
          startBreakTimer();
        }
      }.start();
    }
  }

  private void startBreakTimer() {
    mBreakTimeLeftInMillis = BREAK_TIME_IN_MILLIS;
    mCountDownBreakTimer = new CountDownTimer(mBreakTimeLeftInMillis, 1000) {
      @Override
      public void onTick(long millisBreakUntilFinished) {
        mBreakTimeLeftInMillis = millisBreakUntilFinished;
        isFocus = false;
        updateTextMode();
        updateBreakCountDownText();
      }
      @Override
      public void onFinish() {
        cycle ++;
        updateTextCycle();
        startFocusTimer();
      }
    }.start();
  }

  private void finishPomodoro() {
    mCountDownBreakTimer.cancel();
    pomodoring = false;
    Intent historyPomoIntent = new Intent(this, HistoryPomoActivity.class);
    startActivity(historyPomoIntent);
  }

  private void updateTextMode(){
    textMode.setText(isFocus ? "Chế độ tập trung" : "Chế độ nghỉ ngơi");
  }

  private void updateTextCycle(){
    switch (cycle) {
      case 2:
        textCycle.setText("Chu kỳ thứ hai");
        break;
      case 3:
        textCycle.setText("Chu kỳ thứ ba");
        break;
      case 4:
        textCycle.setText("Chu kỳ cuối cùng");
        break;
    }

  }

  private void updateFocusCountDownText() {
    int minutesF = (int) (mFocusTimeLeftInMillis / 1000) / 60;
    int secondsF = (int) (mFocusTimeLeftInMillis / 1000) % 60;
    String timeFocusLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutesF, secondsF);
    txtCountDown.setText(timeFocusLeftFormatted);
  }

  private void updateBreakCountDownText() {
    int minutesB = (int) (mBreakTimeLeftInMillis / 1000) / 60;
    int secondsB = (int) (mBreakTimeLeftInMillis / 1000) % 60;
    String timeBreakLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutesB, secondsB);
    txtCountDown.setText(timeBreakLeftFormatted);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  protected void onStop() {
    super.onStop();
    if(pomodoring) {
      sendNotification();
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  protected void onPause() {
    super.onPause();
    if(pomodoring) {
      sendNotification();
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public void sendNotification() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_menu_challenge)
            .setContentTitle("Á à, Pomodoro bắt được rồi nhé :<")
            .setContentText("Cà chua của bạn thối luôn, do bạn mất tập trung chuyển qua ứng dụng khác. Trồng lại cà chua sau nhé!")
            .setChannelId(NOTIFICATION_CHANNEL)
            .setStyle(new NotificationCompat.BigTextStyle());
    NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL,
            "My notification channel", NotificationManager.IMPORTANCE_HIGH);
    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.createNotificationChannel(channel);
    notificationManager.notify(notificationId, builder.build());
  }
}