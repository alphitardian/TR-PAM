package com.alphitardian.tr_pam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alphitardian.tr_pam.apis.ApiList;
import com.alphitardian.tr_pam.apis.RetrofitClient;
import com.alphitardian.tr_pam.models.Balance;
import com.alphitardian.tr_pam.models.BalanceResponse;
import com.alphitardian.tr_pam.models.CurrentBalance;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.Currency;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopUpActivity extends AppCompatActivity {

    ShimmerFrameLayout shimmerFrameLayout;
    TextView userBalanceTextView;
    EditText nominalEditText;
    Button topUpButton;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        userBalanceTextView = findViewById(R.id.user_balance_textview);
        nominalEditText = findViewById(R.id.topup_edittext);
        topUpButton = findViewById(R.id.topup_button);
        shimmerFrameLayout = findViewById(R.id.shimmer_balance);

        userBalanceTextView.setVisibility(View.GONE);

        getCurrentBalance();

        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topUpProcess();
            }
        });
    }

    private void topUpProcess(){

        int amount = Integer.parseInt(nominalEditText.getText().toString());

        Balance balance = new Balance(amount, "Direct", "topup");

        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<BalanceResponse> call = apiList.topUpBalance(pref.getString("userId", ""), balance);

        call.enqueue(new Callback<BalanceResponse>() {
            @Override
            public void onResponse(Call<BalanceResponse> call, Response<BalanceResponse> response) {
                BalanceResponse balanceResponse = response.body();

                if(balanceResponse.getStatus().equals("success")){
                    SweetAlertDialog pDialog = new SweetAlertDialog(TopUpActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.setTitleText("Success");
                    pDialog.setContentText("transaction is successful, your wallet is updated!");
                    pDialog.setConfirmButton("Ok", sweetAlertDialog -> {
                        startActivity(new Intent(TopUpActivity.this, WalletActivity.class));
                        finish();
                    });
                    pDialog.show();
                }else{
                    SweetAlertDialog pDialog = new SweetAlertDialog(TopUpActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Error");
                    pDialog.setContentText("transaction failed, please try again later!");
                    pDialog.setConfirmButton("Ok", sweetAlertDialog -> {
                        pDialog.dismiss();
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
                if(response.isSuccessful()){
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