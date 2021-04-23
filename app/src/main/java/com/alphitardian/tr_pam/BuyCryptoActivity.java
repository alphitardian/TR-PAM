package com.alphitardian.tr_pam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alphitardian.tr_pam.adapters.MarketListAdapter;
import com.alphitardian.tr_pam.apis.ApiList;
import com.alphitardian.tr_pam.apis.RetrofitClient;
import com.alphitardian.tr_pam.models.BalanceResponse;
import com.alphitardian.tr_pam.models.CurrentBalance;
import com.alphitardian.tr_pam.models.Transaction;
import com.alphitardian.tr_pam.models.TransactionResponse;

import java.text.NumberFormat;
import java.util.Currency;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyCryptoActivity extends AppCompatActivity {

    private TextView txtViewCrypto, priceTxtView, buyBalance, totalPrice;
    private EditText editTextQuantity;
    private ImageView btnDecrease, btnIncrease;
    Button btnBuyCrypto;

    SharedPreferences pref;
    int amount = 0;
    double total, myBalance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_crypto_screen);

        Intent intent = new Intent(BuyCryptoActivity.this, CryptoDetailActivity.class);

        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        txtViewCrypto = findViewById(R.id.txtViewCrypto);
        priceTxtView = findViewById(R.id.txtViewPrice);
        buyBalance = findViewById(R.id.buyBalance);
        totalPrice = findViewById(R.id.totalPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnBuyCrypto = findViewById(R.id.btnBuyCrypto);

        getCurrentBalance();
        txtViewCrypto.setText(getIntent().getStringExtra(MarketListAdapter.EXTRA_NAME));
        priceTxtView.setText(getIntent().getStringExtra(MarketListAdapter.EXTRA_PRICE));
        editTextQuantity.setText("0");
        totalPrice.setText("$0");

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
                    Toast.makeText(BuyCryptoActivity.this, "You don't have enough balance",
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
                buyCrypto();
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
        Double coinPrice = Double.parseDouble(tempPrice);

        if(myBalance < total + coinPrice){
            return false;
        }else {
            return true;
        }
    }

    private void buyCrypto(){
        String _type = "buy";
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
                    Toast.makeText(BuyCryptoActivity.this, "Transaction Successfully",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BuyCryptoActivity.this, "Transaction Failed",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                Toast.makeText(BuyCryptoActivity.this, "Error during Transaction",
                        Toast.LENGTH_SHORT).show();
                Log.w("Error Transaction", t.toString());
            }
        });
    }

    private void getCurrentBalance() {
        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<CurrentBalance> call = apiList.getCurrentBalance(pref.getString("userId", ""));

        call.enqueue(new Callback<CurrentBalance>() {
            @Override
            public void onResponse(Call<CurrentBalance> call, Response<CurrentBalance> response) {
                if(response.isSuccessful()){
                    CurrentBalance currentBalance = response.body();
                    String balance = currentBalance.getCurrent();

                    myBalance = Double.parseDouble(balance);

                    NumberFormat format = NumberFormat.getCurrencyInstance();
                    format.setMaximumFractionDigits(0);

                    format.setCurrency(Currency.getInstance("USD"));

                    buyBalance.setText(format.format(myBalance));

                }
            }

            @Override
            public void onFailure(Call<CurrentBalance> call, Throwable t) {
                Log.w("error wallet", t.toString());
            }
        });
    }
}
