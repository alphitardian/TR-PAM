package com.alphitardian.tr_pam;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BuyCryptoActivity extends AppCompatActivity {

    private TextView txtViewCrypto, priceTxtView;
    private EditText editTextQuantity;
    private ImageView btnDecrease, btnIncrease;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_crypto_screen);

        txtViewCrypto = findViewById(R.id.txtViewCrypto);
        priceTxtView = findViewById(R.id.txtViewPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);

    }
}
