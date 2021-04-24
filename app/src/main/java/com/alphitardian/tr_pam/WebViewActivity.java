package com.alphitardian.tr_pam;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.alphitardian.tr_pam.adapters.MarketListAdapter;
import com.alphitardian.tr_pam.apis.ApiList;
import com.alphitardian.tr_pam.apis.RetrofitClient;

import com.alphitardian.tr_pam.models.CryptoList;

import retrofit2.Call;


public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    public static final String EXTRA_SYMBOL = "extra_symbol";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_screen);

        int currentOrientation = this.getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        getWebView();
    }

    private void getWebView() {
        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<CryptoList> call = apiList.getAllList();

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String keySymbol = getIntent().getStringExtra(EXTRA_SYMBOL) + "USD";

        webView.loadUrl("https://www.tradingview.com/chart/?symbol="+keySymbol+"&source=unauth_header&feature=launch_chart");
    }
}
