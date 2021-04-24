package com.alphitardian.tr_pam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    private int loadingTime = 2000;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pref.getBoolean("LoggedIn", false)) {
                    startActivity(new Intent(SplashScreen.this, PinLogin.class));
                }else{
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                }

                finish();
            }
        }, loadingTime);
    }
}