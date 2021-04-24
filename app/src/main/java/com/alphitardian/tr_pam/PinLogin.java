package com.alphitardian.tr_pam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PinLogin extends AppCompatActivity {

    Button pinLoginButton;
    EditText pinLoginValue;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_login);

        pinLoginButton = findViewById(R.id.LoginPinButton);
        pinLoginValue = findViewById(R.id.pinLoginValue);
        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        editor = pref.edit();

        pinLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myPin = pref.getString("userPin", "");
                if(myPin.equals(pinLoginValue.getText().toString())){
                    startActivity(new Intent(PinLogin.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(PinLogin.this, "Wrong Pin!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}