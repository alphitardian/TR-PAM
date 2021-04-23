package com.alphitardian.tr_pam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alphitardian.tr_pam.adapters.CryptoWalletGridAdapter;
import com.alphitardian.tr_pam.apis.ApiList;
import com.alphitardian.tr_pam.apis.RetrofitClient;
import com.alphitardian.tr_pam.models.CurrentBalance;
import com.alphitardian.tr_pam.models.CryptoData;
import com.alphitardian.tr_pam.models.CryptoList;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity {

    TextView userBalanceTextView;
    RecyclerView recyclerView;
    ProgressBar progressBar, progressBarBalance;
    ImageView topupButton;
    private ArrayList<CryptoData> cryptoData = new ArrayList<>();

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        userBalanceTextView = findViewById(R.id.user_balance_textview);
        progressBar = findViewById(R.id.progress_bar);
        progressBarBalance = findViewById(R.id.progress_bar_balance);
        topupButton = findViewById(R.id.topup_button);
        recyclerView = findViewById(R.id.crypto_grid);
        recyclerView.setHasFixedSize(true);

        userBalanceTextView.setVisibility(View.GONE);
        progressBarBalance.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        getCurrentBalance();
        getAllCrypto();

        topupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), TopUpActivity.class);
                startActivity(intent);
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

                    NumberFormat format = NumberFormat.getCurrencyInstance();
                    format.setMaximumFractionDigits(0);

                    format.setCurrency(Currency.getInstance("USD"));

                    progressBarBalance.setVisibility(View.GONE);
                    userBalanceTextView.setVisibility(View.VISIBLE);

                    userBalanceTextView.setText(format.format(Double.parseDouble(balance)));

                }
            }

            @Override
            public void onFailure(Call<CurrentBalance> call, Throwable t) {
                Log.w("error wallet", t.toString());
            }
        });
    }

    private void getAllCrypto() {
        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<CryptoList> call = apiList.getAllList();

        call.enqueue(new Callback<CryptoList>() {
            @Override
            public void onResponse(Call<CryptoList> call, Response<CryptoList> response) {
                if (response.isSuccessful()) {
                    CryptoList data = response.body();

                    Log.d("TAG", "onResponse: " + data.getData().size());

                    for (int i = 0; i < data.getData().size(); i++) {
                        CryptoData itemData = new CryptoData(data.getData().get(i).getName(), data.getData().get(i).getSymbol(), data.getData().get(i).getLastUpdate(), data.getData().get(i).getPrice());
                        cryptoData.add(itemData);
                    }

                    showRecyclerList();

                    progressBar.setVisibility(View.INVISIBLE);

                } else {
                    Toast.makeText(getApplicationContext(), "Responses failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CryptoList> call, Throwable t) {
                Log.w("error wallet", t.toString());
            }
        });
    }

    private void showRecyclerList(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        CryptoWalletGridAdapter listAdapter = new CryptoWalletGridAdapter(cryptoData);
        recyclerView.setAdapter(listAdapter);
    }
}