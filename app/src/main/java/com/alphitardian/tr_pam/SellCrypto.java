package com.alphitardian.tr_pam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alphitardian.tr_pam.adapters.MarketListAdapter;
import com.alphitardian.tr_pam.apis.ApiList;
import com.alphitardian.tr_pam.apis.RetrofitClient;
import com.alphitardian.tr_pam.models.AssetsInSingle;
import com.alphitardian.tr_pam.models.AssetsInSingleData;
import com.alphitardian.tr_pam.models.Transaction;
import com.alphitardian.tr_pam.models.TransactionResponse;

import java.text.NumberFormat;
import java.util.Currency;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellCrypto extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_PRICE = "extra_price";

    private TextView txtViewCrypto, priceTxtView, buyBalance, totalPrice;
    private EditText editTextQuantity;
    private ImageView btnDecrease, btnIncrease;
    Button btnBuyCrypto;

    NumberFormat format = NumberFormat.getCurrencyInstance();

    SharedPreferences pref;
    int amount = 0;
    int currentAmount;
    double total, myBalance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_crypto);

        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("USD"));
        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        txtViewCrypto = findViewById(R.id.txtViewCrypto);
        priceTxtView = findViewById(R.id.txtViewPrice);
        buyBalance = findViewById(R.id.buyBalance);
        totalPrice = findViewById(R.id.totalPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnBuyCrypto = findViewById(R.id.btnBuyCrypto);

        txtViewCrypto.setText(getIntent().getStringExtra(EXTRA_NAME));
        priceTxtView.setText(format.format(getIntent().getDoubleExtra(EXTRA_PRICE, 0.0)));
        editTextQuantity.setText("0");
        totalPrice.setText("$0");

        getAsset();
        checkQuantity();

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTotalAndValue()) {
                    amount += 1;
                    editTextQuantity.setText(amount + "");
                    checkQuantity();
                    checkTotal();
                } else {
                    Toast.makeText(SellCrypto.this, getString(R.string.dont_have_coin_toast),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount -= 1;
                editTextQuantity.setText(amount + "");
                checkQuantity();
                checkTotal();
            }
        });

        btnBuyCrypto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellCrypto();
            }
        });

    }

    public void checkQuantity() {
        String value = editTextQuantity.getText().toString();
        int amountCheck = Integer.parseInt(value);

        if (amountCheck < 1) {
            btnDecrease.setEnabled(false);
        } else {
            btnDecrease.setEnabled(true);
        }
    }

    public void checkTotal() {
        String tempCoinValue = editTextQuantity.getText().toString();
        double price = getIntent().getDoubleExtra(EXTRA_PRICE, 0.0);
        int coinValue = Integer.parseInt(tempCoinValue);

        total = coinValue * price;

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);

        format.setCurrency(Currency.getInstance("USD"));

        totalPrice.setText(format.format(total));
    }

    public boolean checkTotalAndValue() {

        if (currentAmount < amount + 1) {
            return false;
        } else {
            return true;
        }
    }

    private void sellCrypto() {

        String tempCoinValue = editTextQuantity.getText().toString();
        double price = getIntent().getDoubleExtra(EXTRA_PRICE, 0.0);
        int coinValue = Integer.parseInt(tempCoinValue);

        total = coinValue * price;

        String _type = "sell";
        String _coin = txtViewCrypto.getText().toString();
        int _id = Integer.parseInt(getIntent().getExtras().getString(MarketListAdapter.EXTRA_ID));
        int _amount = Integer.parseInt(editTextQuantity.getText().toString());
        double _price = total;

        Transaction transaction = new Transaction(_id, _coin, _type, _price, _amount);

        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<TransactionResponse> call = apiList.createTransaction(pref.getString("userId", ""), transaction);

        call.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if (response.isSuccessful()) {
                    SweetAlertDialog pDialog = new SweetAlertDialog(SellCrypto.this, SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.setTitleText(getString(R.string.success_alert_title));
                    pDialog.setContentText(getString(R.string.success_sell_alert_content));
                    pDialog.setConfirmButton(getString(R.string.transaction_alert_confirm_button), sweetAlertDialog -> {
                        finish();
                    });
                    pDialog.show();
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(SellCrypto.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText(getString(R.string.error_alert_title));
                    pDialog.setContentText(getString(R.string.error_alert_content));
                    pDialog.setConfirmButton(getString(R.string.transaction_alert_confirm_button), sweetAlertDialog -> {
                        finish();
                    });
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                SweetAlertDialog pDialog = new SweetAlertDialog(SellCrypto.this, SweetAlertDialog.ERROR_TYPE);
                pDialog.setTitleText(getString(R.string.error_alert_title));
                pDialog.setContentText(getString(R.string.error_alert_content));
                pDialog.setConfirmButton(getString(R.string.transaction_alert_confirm_button), sweetAlertDialog -> {
                    finish();
                });
                pDialog.show();
            }
        });
    }

    public void getAsset() {
        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<AssetsInSingle> call = apiList.getSingleCryptoReport(pref.getString("userId", ""), Integer.parseInt(getIntent().getStringExtra(MarketListAdapter.EXTRA_ID)));

        call.enqueue(new Callback<AssetsInSingle>() {
            @Override
            public void onResponse(Call<AssetsInSingle> call, Response<AssetsInSingle> response) {
                if (response.isSuccessful()) {
                    AssetsInSingle assetsInSingle = response.body();
                    AssetsInSingleData data = assetsInSingle.getData();

                    currentAmount = data.getAmount();
                }
            }

            @Override
            public void onFailure(Call<AssetsInSingle> call, Throwable t) {

            }
        });
    }
}