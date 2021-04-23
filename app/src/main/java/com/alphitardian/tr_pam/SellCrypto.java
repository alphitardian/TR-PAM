package com.alphitardian.tr_pam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphitardian.tr_pam.adapters.MarketListAdapter;
import com.alphitardian.tr_pam.apis.ApiList;
import com.alphitardian.tr_pam.apis.RetrofitClient;
import com.alphitardian.tr_pam.models.AssetsInSingle;
import com.alphitardian.tr_pam.models.AssetsInSingleData;
import com.alphitardian.tr_pam.models.CurrentBalance;
import com.alphitardian.tr_pam.models.Transaction;
import com.alphitardian.tr_pam.models.TransactionResponse;

import java.text.NumberFormat;
import java.util.Currency;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellCrypto extends AppCompatActivity {

    private TextView txtViewCrypto, priceTxtView, buyBalance, totalPrice;
    private EditText editTextQuantity;
    private ImageView btnDecrease, btnIncrease;
    Button btnBuyCrypto;

    SharedPreferences pref;
    int amount = 0;
    int currentAmount;
    double total, myBalance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_crypto);

        Intent intent = new Intent(SellCrypto.this, CryptoDetailActivity.class);

        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        txtViewCrypto = findViewById(R.id.txtViewCrypto);
        priceTxtView = findViewById(R.id.txtViewPrice);
        buyBalance = findViewById(R.id.buyBalance);
        totalPrice = findViewById(R.id.totalPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnBuyCrypto = findViewById(R.id.btnBuyCrypto);

        txtViewCrypto.setText(getIntent().getStringExtra(MarketListAdapter.EXTRA_NAME));
        priceTxtView.setText(getIntent().getStringExtra(MarketListAdapter.EXTRA_PRICE));
        editTextQuantity.setText("0");
        totalPrice.setText("$0");

        getAsset();
        checkQuantity();

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkTotalAndValue()){
                    amount += 1;
                    editTextQuantity.setText(amount + "");
                    checkQuantity();
                    checkTotal();
                }else{
                    Toast.makeText(SellCrypto.this, "You don't have enough coin",
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

    public void checkQuantity(){
        String value = editTextQuantity.getText().toString();
        int amountCheck = Integer.parseInt(value);

        if(amountCheck < 1){
            btnDecrease.setEnabled(false);
        }else{
            btnDecrease.setEnabled(true);
        }
    }

    public void checkTotal(){
        String tempCoinValue = editTextQuantity.getText().toString();
        String tempPrice = priceTxtView.getText().toString();
        int coinValue = Integer.parseInt(tempCoinValue);
        double price = Double.parseDouble(tempPrice);

        total = coinValue * price;

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);

        format.setCurrency(Currency.getInstance("USD"));

        totalPrice.setText(format.format(total));
    }

    public boolean checkTotalAndValue(){

        String tempPrice = priceTxtView.getText().toString();

        if(currentAmount < amount + 1){
            return false;
        }else {
            return true;
        }
    }

    private void sellCrypto(){
        String _type = "sell";
        String _coin = txtViewCrypto.getText().toString();
        int _id = Integer.parseInt(getIntent().getExtras().getString(MarketListAdapter.EXTRA_ID));
        int _amount = Integer.parseInt(editTextQuantity.getText().toString());
        double _price = Double.parseDouble(priceTxtView.getText().toString());

        Transaction transaction = new Transaction(_id, _coin, _type, _price, _amount);

        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<TransactionResponse> call = apiList.createTransaction(pref.getString("userId", ""), transaction);

        call.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SellCrypto.this, "Transaction Successfully",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SellCrypto.this, "Transaction Failed",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                Toast.makeText(SellCrypto.this, "Error during Transaction",
                        Toast.LENGTH_SHORT).show();
                Log.w("Error Transaction", t.toString());
            }
        });
    }

    public void getAsset(){
        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<AssetsInSingle> call = apiList.getSingleCryptoReport(pref.getString("userId", ""), Integer.parseInt(getIntent().getStringExtra(MarketListAdapter.EXTRA_ID)));

        call.enqueue(new Callback<AssetsInSingle>() {
            @Override
            public void onResponse(Call<AssetsInSingle> call, Response<AssetsInSingle> response) {
                if(response.isSuccessful()){
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