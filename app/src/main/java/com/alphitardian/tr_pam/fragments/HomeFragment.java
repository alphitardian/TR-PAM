package com.alphitardian.tr_pam.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alphitardian.tr_pam.R;
import com.alphitardian.tr_pam.TopUpActivity;
import com.alphitardian.tr_pam.apis.ApiList;
import com.alphitardian.tr_pam.apis.RetrofitClient;
import com.alphitardian.tr_pam.models.CurrentBalance;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.NumberFormat;
import java.util.Currency;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ShimmerFrameLayout shimmerBalance, shimmerBuy, shimmerSell;
    private TextView homeBalanceValue, homeBuyValue, homeSellValue;
    private Button goToTopUp;

    SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        pref = this.getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeBalanceValue = getActivity().findViewById(R.id.homeBalanceValue);
        homeBuyValue = getActivity().findViewById(R.id.homeBuyValue);
        homeSellValue = getActivity().findViewById(R.id.homeSellValue);
        shimmerBalance = view.findViewById(R.id.shimmer_balance);
        shimmerBuy = view.findViewById(R.id.shimmer_buy);
        shimmerSell = view.findViewById(R.id.shimmer_sell);
        goToTopUp = view.findViewById(R.id.goToTopUp);

        homeBalanceValue.setVisibility(View.GONE);
        homeBuyValue.setVisibility(View.GONE);
        homeSellValue.setVisibility(View.GONE);
        getCurrentBalance();

        goToTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TopUpActivity.class));
            }
        });
    }

    public void getCurrentBalance() {
        ApiList apiList = RetrofitClient.getRetrofitClient().create(ApiList.class);
        Call<CurrentBalance> call = apiList.getCurrentBalance(pref.getString("userId", ""));


        call.enqueue(new Callback<CurrentBalance>() {
            @Override
            public void onResponse(Call<CurrentBalance> call, Response<CurrentBalance> response) {
                if (response.isSuccessful()) {
                    CurrentBalance currentBalance = response.body();
                    String balance = currentBalance.getCurrent();
                    Double Buy = currentBalance.getBalanceTransaction().getTransactionBuy();
                    Double Sell = currentBalance.getBalanceTransaction().getTransactionSell();

                    NumberFormat format = NumberFormat.getCurrencyInstance();
                    format.setMaximumFractionDigits(0);

                    format.setCurrency(Currency.getInstance("USD"));

                    shimmerBalance.stopShimmer();
                    shimmerBuy.stopShimmer();
                    shimmerSell.stopShimmer();

                    shimmerBalance.setVisibility(View.GONE);
                    shimmerBuy.setVisibility(View.GONE);
                    shimmerSell.setVisibility(View.GONE);

                    homeBalanceValue.setText(format.format(Double.parseDouble(balance)));
                    homeBuyValue.setText(format.format(Buy));
                    homeSellValue.setText(format.format(Sell));

                    homeBalanceValue.setVisibility(View.VISIBLE);
                    homeBuyValue.setVisibility(View.VISIBLE);
                    homeSellValue.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CurrentBalance> call, Throwable t) {
                Log.w("error wallet", t.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerBalance.startShimmer();
        shimmerBuy.startShimmer();
        shimmerSell.startShimmer();
    }

    @Override
    public void onPause() {
        shimmerBalance.stopShimmer();
        shimmerBuy.stopShimmer();
        shimmerSell.stopShimmer();
        super.onPause();
    }
}