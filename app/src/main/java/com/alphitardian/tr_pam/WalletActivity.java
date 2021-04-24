package com.alphitardian.tr_pam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
import com.alphitardian.tr_pam.models.AssetsList;
import com.alphitardian.tr_pam.models.AssetsListCoin;
import com.alphitardian.tr_pam.models.AssetsListResponse;
import com.alphitardian.tr_pam.models.CurrentBalance;
import com.alphitardian.tr_pam.models.CryptoData;
import com.alphitardian.tr_pam.models.CryptoList;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity {

    ShimmerFrameLayout shimmerBalance, shimmerAssets;
    TextView userBalanceTextView;
    RecyclerView recyclerView;
    ImageView topupButton;

    private ArrayList<AssetsListCoin> assetsListsCoin = new ArrayList<>();

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        userBalanceTextView = findViewById(R.id.user_balance_textview);
        topupButton = findViewById(R.id.topup_button);
        recyclerView = findViewById(R.id.crypto_grid);
        shimmerBalance = findViewById(R.id.shimmer_balance);
        shimmerAssets = findViewById(R.id.shimmer_assets);
        recyclerView.setHasFixedSize(true);

        userBalanceTextView.setVisibility(View.GONE);

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

                    userBalanceTextView.setVisibility(View.VISIBLE);
                    shimmerBalance.stopShimmer();
                    shimmerBalance.setVisibility(View.GONE);

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
        Call<AssetsListResponse> call = apiList.getMyAssetList(pref.getString("userId", ""));

        call.enqueue(new Callback<AssetsListResponse>() {
            @Override
            public void onResponse(Call<AssetsListResponse> call, Response<AssetsListResponse> response) {
                if (response.isSuccessful()) {
                    AssetsListResponse data = response.body();
                    List<AssetsListCoin> assets = data.getData().getCoin();

                    for (int i = 0; i < assets.size(); i++) {
                        AssetsListCoin itemData = new AssetsListCoin(
                                assets.get(i).getId(),
                                assets.get(i).getCoin(),
                                assets.get(i).getTotal()
                        );
                        assetsListsCoin.add(itemData);
                    }

                    showRecyclerList();
                    shimmerAssets.stopShimmer();
                    shimmerAssets.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getApplicationContext(), "Responses failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AssetsListResponse> call, Throwable t) {
                Log.w("error wallet", t.toString());
            }
        });
    }

    private void showRecyclerList(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        CryptoWalletGridAdapter listAdapter = new CryptoWalletGridAdapter(assetsListsCoin);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerBalance.startShimmer();
        shimmerAssets.startShimmer();
    }

    @Override
    public void onPause() {
        shimmerBalance.stopShimmer();
        shimmerAssets.stopShimmer();
        super.onPause();
    }
}