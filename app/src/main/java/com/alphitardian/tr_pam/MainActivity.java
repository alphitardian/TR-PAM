package com.alphitardian.tr_pam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.alphitardian.tr_pam.apis.ApiList;
import com.alphitardian.tr_pam.apis.RetrofitClient;
import com.alphitardian.tr_pam.fragments.HomeFragment;
import com.alphitardian.tr_pam.fragments.NewsFragment;
import com.alphitardian.tr_pam.fragments.MarketFragment;
import com.alphitardian.tr_pam.models.CryptoList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());

        getAllCrypto();
    }

    // Replace Fragment in the Host Fragment
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.host_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.action_home:
                fragment = new HomeFragment();
                Toast.makeText(this, "Home View", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_market:
                fragment = new MarketFragment();
                Toast.makeText(this, "Profile VIew", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_news:
                fragment = new NewsFragment();
                Toast.makeText(this, "News View", Toast.LENGTH_SHORT).show();
                break;
        }
        return loadFragment(fragment);
    }
}