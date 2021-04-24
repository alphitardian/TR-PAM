package com.alphitardian.tr_pam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphitardian.tr_pam.adapters.TransactionListAdapter;
import com.google.android.material.card.MaterialCardView;

public class TransactionDetailActivity extends AppCompatActivity {

    private TextView cryptoNameTextView, purchaseAmountTextView, currentStatusTextView, purchaseDateTextView;
    private ImageView cryptoImage;
    private MaterialCardView materialCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        cryptoNameTextView = findViewById(R.id.crypto_name_textview);
        purchaseAmountTextView = findViewById(R.id.purchase_amount_textview);
        currentStatusTextView = findViewById(R.id.current_status_textview);
        purchaseDateTextView = findViewById(R.id.purchase_date_textview);
        cryptoImage = findViewById(R.id.crypto_image);
        materialCardView = findViewById(R.id.statusCard);

        if (getIntent().getStringExtra(TransactionListAdapter.EXTRA_STATUS).equals("buy")) {
            materialCardView.setCardBackgroundColor(getResources().getColor(R.color.teal_200));
        } else {
            materialCardView.setCardBackgroundColor(getResources().getColor(R.color.downtrend));
        }

        cryptoNameTextView.setText(getIntent().getStringExtra(TransactionListAdapter.EXTRA_NAME));
        purchaseAmountTextView.setText(getIntent().getStringExtra(TransactionListAdapter.EXTRA_PRICE));
        currentStatusTextView.setText(getIntent().getStringExtra(TransactionListAdapter.EXTRA_STATUS).toUpperCase() + " Transaction Success".toUpperCase());
        purchaseDateTextView.setText(getIntent().getStringExtra(TransactionListAdapter.EXTRA_DATE));
        cryptoImage.setImageResource(getIntent().getIntExtra(TransactionListAdapter.EXTRA_ICON, 0));
    }
}