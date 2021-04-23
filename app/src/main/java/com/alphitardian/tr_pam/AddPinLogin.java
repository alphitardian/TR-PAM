package com.alphitardian.tr_pam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPinLogin extends AppCompatActivity {

    Button addPinButton;
    EditText newPin;

    SharedPreferences pref;
    String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pin_login);

        addPinButton = findViewById(R.id.addPinButton);
        newPin = findViewById(R.id.newPin);
        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        addPinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = newPin.getText().toString();

                if(!pin.equals("")){
                    editor.putString("userPin", newPin.getText() + "");
                    editor.apply();

                    startActivity(new Intent(AddPinLogin.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(AddPinLogin.this, "Pin is Empty! ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}