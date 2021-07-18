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
import android.view.View;
import android.widget.Button;

import fpt.provipluxurylimited.challengefocus.MainActivity;
import fpt.provipluxurylimited.challengefocus.R;

public class FocusPomoActivity extends AppCompatActivity {

  Button btnGiveup;
  private static int notificationId = 101;
  private final String NOTIFICATION_CHANNEL = "My channel";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_focus_pomo);
    initComponents();
  }

  private void initComponents() {
    btnGiveup = findViewById(R.id.btnGiveup);
    btnGiveup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent mainActivityIntent = new Intent(FocusPomoActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
      }
    });
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  protected void onStop() {
    super.onStop();
    sendNotification();
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  protected void onPause() {
    super.onPause();
//    sendNotification();
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