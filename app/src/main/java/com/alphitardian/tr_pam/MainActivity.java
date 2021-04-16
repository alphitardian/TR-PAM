package com.alphitardian.tr_pam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alphitardian.tr_pam.api.ApiList;
import com.alphitardian.tr_pam.api.RetrofitClient;
import com.alphitardian.tr_pam.model.CryptoData;
import com.alphitardian.tr_pam.model.CryptoList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllCrypto();
    }

    private void getAllCrypto() {
        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<CryptoList> call = apiList.getAllList();

        call.enqueue(new Callback<CryptoList>() {
            @Override
            public void onResponse(Call<CryptoList> call, Response<CryptoList> response) {
                if (response.isSuccessful()) {
                    CryptoList data = response.body();
                    Log.d("TAG", "onResponse: " + data.getData().get(0).getName());
                } else {
                    Toast.makeText(MainActivity.this, "Responses failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CryptoList> call, Throwable t) {

            }
        });
    }
}