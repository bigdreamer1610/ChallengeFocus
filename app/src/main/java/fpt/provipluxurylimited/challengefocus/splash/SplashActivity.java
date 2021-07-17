package fpt.provipluxurylimited.challengefocus.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import fpt.provipluxurylimited.challengefocus.MainActivity;
import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.authen.AuthenticationActivity;
import fpt.provipluxurylimited.challengefocus.helpers.Constants;
import fpt.provipluxurylimited.challengefocus.helpers.SaveSharedPreference;

public class SplashActivity extends AppCompatActivity {
    AppCompatButton btnStart;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AuthenticationActivity.class);
                startActivity(intent);
            }
        });

        if (SaveSharedPreference.getUserId(this) != null) {
            Log.e("Userid", SaveSharedPreference.getUserId(this));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}