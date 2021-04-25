package com.alphitardian.tr_pam;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.alphitardian.tr_pam.fragments.HomeFragment;
import com.alphitardian.tr_pam.fragments.MarketFragment;
import com.alphitardian.tr_pam.fragments.ProfileFragment;
import com.alphitardian.tr_pam.fragments.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final int PERMISSION_REQ = 2;

    private BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    private String[] permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navbar);
        fab = findViewById(R.id.fab);

        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());

        permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQ);
                }
            }
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WalletActivity.class);
                startActivity(intent);
            }
        });
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
                bottomNavigationView.getMenu().getItem(2).setVisible(true);
                fab.show();
                break;
            case R.id.action_market:
                fragment = new MarketFragment();
                bottomNavigationView.getMenu().getItem(2).setVisible(false);
                fab.hide();
                break;
            case R.id.action_transaction:
                fragment = new TransactionFragment();
                bottomNavigationView.getMenu().getItem(2).setVisible(false);
                fab.hide();
                break;
            case R.id.action_profile:
                fragment = new ProfileFragment();
                bottomNavigationView.getMenu().getItem(2).setVisible(true);
                fab.show();
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager()
                .getBackStackEntryCount() > 0) {

            super.onBackPressed();

        } else {

            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Do you want to close the application?")
                    .setConfirmText("Close")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            MainActivity.super.onBackPressed();
                        }
                    })
                    .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
    }
}