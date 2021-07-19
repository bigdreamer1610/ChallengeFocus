package fpt.provipluxurylimited.challengefocus.pomodoro;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import fpt.provipluxurylimited.challengefocus.MainActivity;
import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.helpers.ApiClient;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.models.Pomodoro;
import fpt.provipluxurylimited.challengefocus.models.ToDoItem;

public class FocusPomoActivity extends AppCompatActivity {

  Context context;
  private ArrayList<Pomodoro> list;
  Button btnGiveup;
  ImageButton btnSound;
  private static int notificationId = 101;
  private final String NOTIFICATION_CHANNEL = "My channel";
//  private static final long FOCUS_TIME_IN_MILLIS = 1500000;
//  private static final long BREAK_TIME_IN_MILLIS = 300000;
  private static final long FOCUS_TIME_IN_MILLIS = 15000;
  private static final long BREAK_TIME_IN_MILLIS = 8500;

  private int cycle = 1;
  private boolean isFocus;
  private TextView txtCountDown;
  private TextView textMode;
  private TextView textCycle;
  private CountDownTimer mCountDownFocusTimer;
  private CountDownTimer mCountDownBreakTimer;
  private long mFocusTimeLeftInMillis;
  private long mBreakTimeLeftInMillis;
  private String contentText;

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
    context = this;
    getList();
    contentText = "Cà chua của bạn thối luôn, do bạn mất tập trung chuyển qua ứng dụng khác. Trồng lại cà chua sau nhé!";
    btnGiveup = findViewById(R.id.btnGiveup);
    btnSound = findViewById(R.id.imgBtnSound);
    btnGiveup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String mode;
        if(isFocus){
          mode = "FOCUS";
        } else {
          mode = "BREAK";
        }
        addPomo(list.size()+1, cycle, Calendar.getInstance().getTime().toString(), mode, "ROTTEN");
        contentText = "Thêm một quả cà chua thối, vì bạn đã bỏ cuộc! Trồng lại cà chua sau nhé!";
        Intent historyPomoIntent = new Intent(FocusPomoActivity.this, HistoryPomoActivity.class);
        startActivity(historyPomoIntent);
      }
    });
    btnSound.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        btnSound.setImageResource(R.drawable.ic_sound_off);
        MediaPlayer media = MediaPlayer.create(context, R.raw.lake);
        media.setVolume(0 , 0);
      }
    });
  }

  private void startFocusTimer() {
    if(cycle>4){
      cycle=4;
      finishPomodoro();
    } else {
      mFocusTimeLeftInMillis = FOCUS_TIME_IN_MILLIS;
      mCountDownFocusTimer = new CountDownTimer(mFocusTimeLeftInMillis, 1000) {
        @Override
        public void onTick(long millisFocusUntilFinished) {
          mFocusTimeLeftInMillis = millisFocusUntilFinished;
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
    contentText = "Bạn vừa trồng được một quả cà chua chín! Tiếp tục phát huy nhé!";
    mCountDownBreakTimer.cancel();
    addPomo(list.size()+1, cycle, Calendar.getInstance().getTime().toString(), "BREAK", "RIPE");
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

  private void addPomo(int id, int cycle, String endDate, String mode, String status){
    Pomodoro pomodoro = new Pomodoro(id, cycle, endDate, mode, status);
    FirebaseUtil.shared.getReference().child(ApiClient.pomodoro).push()
            .setValue(pomodoro);
  }

  public void getList() {
    FirebaseUtil.shared.getReference().child(ApiClient.pomodoro)
            .addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<Pomodoro> listPomo = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                  Pomodoro pomodoro = ds.getValue(Pomodoro.class);
                  listPomo.add(pomodoro);
                }
                list = listPomo;
              }
              @Override
              public void onCancelled(@NonNull @NotNull DatabaseError error) {
              }
            });
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  protected void onStop() {
    super.onStop();
//    String mode;
//    if(isFocus){
//      mode = "FOCUS";
//    } else {
//      mode = "BREAK";
//    }
//    addPomo(list.size()+1, cycle, Calendar.getInstance().getTime().toString(), mode, "ROTTEN");
      sendNotification();
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  protected void onPause() {
    super.onPause();
//    String mode;
//    if(isFocus){
//      mode = "FOCUS";
//    } else {
//      mode = "BREAK";
//    }
//    addPomo(list.size()+1, cycle, Calendar.getInstance().getTime().toString(), mode, "ROTTEN");
      sendNotification();
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public void sendNotification() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_menu_challenge)
            .setContentTitle("Á à, Pomodoro bắt được rồi nhé :<")
            .setContentText(contentText)
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