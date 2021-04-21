package com.alphitardian.tr_pam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TopUpActivity extends AppCompatActivity {

    TextView userBalanceTextView;
    EditText nominalEditText;
    Button topUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        userBalanceTextView = findViewById(R.id.user_balance_textview);
        nominalEditText = findViewById(R.id.topup_edittext);
        topUpButton = findViewById(R.id.topup_button);

        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}