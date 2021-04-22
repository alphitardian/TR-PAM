package com.alphitardian.tr_pam.apis;

import com.alphitardian.tr_pam.models.Balance;
import com.alphitardian.tr_pam.models.BalanceResponse;
import com.alphitardian.tr_pam.models.CurrentBalance;
import com.alphitardian.tr_pam.models.CryptoList;
import com.alphitardian.tr_pam.models.TransactionHistoryList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiList {
    @GET("/coin/api/v1")
    Call<CryptoList> getAllList();

    @GET("/coin/api/v1/single")
    Call<CryptoList> getSingleCrypto();

    @GET("/balance/current")
    Call<CurrentBalance> getCurrentBalance(@Header("userId") String userId);

    @POST("/balance")
    Call<BalanceResponse> topUpBalance(@Header("userId") String userId, @Body Balance balance);

    @GET("/transaction/history")
    Call<TransactionHistoryList> getTransactionHistory(@Header("userId") String userId);
}
