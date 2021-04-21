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
import com.alphitardian.tr_pam.fragments.ProfileFragment;
import com.alphitardian.tr_pam.fragments.TransactionFragment;
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.action_home:
                fragment = new HomeFragment();
                break;
            case R.id.action_market:
                fragment = new MarketFragment();
                break;
            case R.id.action_transaction:
                fragment = new TransactionFragment();
                break;
            case R.id.action_profile:
                fragment = new ProfileFragment();
                break;
        }
        return loadFragment(fragment);
    }
}