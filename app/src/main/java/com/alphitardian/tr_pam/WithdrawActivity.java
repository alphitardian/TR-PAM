package com.alphitardian.tr_pam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alphitardian.tr_pam.apis.ApiList;
import com.alphitardian.tr_pam.apis.RetrofitClient;
import com.alphitardian.tr_pam.models.Balance;
import com.alphitardian.tr_pam.models.BalanceResponse;
import com.alphitardian.tr_pam.models.CurrentBalance;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.NumberFormat;
import java.util.Currency;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawActivity extends AppCompatActivity {

    ShimmerFrameLayout shimmerFrameLayout;
    TextView userBalanceTextView, withdrawTextView;
    EditText nominalEditText;
    Button withdrawButton;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        withdrawTextView = findViewById(R.id.topup_title);
        userBalanceTextView = findViewById(R.id.user_balance_textview);
        nominalEditText = findViewById(R.id.topup_edittext);
        withdrawButton = findViewById(R.id.topup_button);
        shimmerFrameLayout = findViewById(R.id.shimmer_balance);

        userBalanceTextView.setVisibility(View.GONE);
        withdrawButton.setText(getString(R.string.withdraw_textview));
        withdrawTextView.setText(getString(R.string.withdraw_textview));

        getCurrentBalance();

        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawProcess();
            }
        });
    }

    private void withdrawProcess() {

        int amount = Integer.parseInt(nominalEditText.getText().toString());

        Balance balance = new Balance(amount, "Direct", "withdraw");

        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<BalanceResponse> call = apiList.topUpBalance(pref.getString("userId", ""), balance);

        call.enqueue(new Callback<BalanceResponse>() {
            @Override
            public void onResponse(Call<BalanceResponse> call, Response<BalanceResponse> response) {
                BalanceResponse balanceResponse = response.body();

                if (balanceResponse.getStatus().equals("success")) {
                    SweetAlertDialog pDialog = new SweetAlertDialog(WithdrawActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.setTitleText(getString(R.string.success_alert_title));
                    pDialog.setContentText(getString(R.string.success_transaction_alert_content));
                    pDialog.setConfirmButton(getString(R.string.transaction_alert_confirm_button), sweetAlertDialog -> {
                        pDialog.dismissWithAnimation();
                        startActivity(new Intent(WithdrawActivity.this, WalletActivity.class));
                        finish();
                    });
                    pDialog.show();
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(WithdrawActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText(getString(R.string.error_alert_title));
                    pDialog.setContentText(getString(R.string.error_alert_content));
                    pDialog.setConfirmButton(getString(R.string.transaction_alert_confirm_button), sweetAlertDialog -> {
                        pDialog.dismissWithAnimation();
                    });
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BalanceResponse> call, Throwable t) {

            }
        });

    }

    private void getCurrentBalance() {
        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<CurrentBalance> call = apiList.getCurrentBalance(pref.getString("userId", ""));

        call.enqueue(new Callback<CurrentBalance>() {
            @Override
            public void onResponse(Call<CurrentBalance> call, Response<CurrentBalance> response) {
                if (response.isSuccessful()) {
                    CurrentBalance currentBalance = response.body();
                    String balance = currentBalance.getCurrent();

                    NumberFormat format = NumberFormat.getCurrencyInstance();
                    format.setMaximumFractionDigits(0);

                    format.setCurrency(Currency.getInstance("USD"));

                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);

                    userBalanceTextView.setVisibility(View.VISIBLE);

                    userBalanceTextView.setText(format.format(Double.parseDouble(balance)));
                }
            }

            @Override
            public void onFailure(Call<CurrentBalance> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }
}