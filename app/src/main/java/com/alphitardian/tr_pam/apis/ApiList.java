package com.alphitardian.tr_pam.apis;

import com.alphitardian.tr_pam.models.AssetsInSingle;
import com.alphitardian.tr_pam.models.AssetsList;
import com.alphitardian.tr_pam.models.AssetsListResponse;
import com.alphitardian.tr_pam.models.Balance;
import com.alphitardian.tr_pam.models.BalanceResponse;
import com.alphitardian.tr_pam.models.CurrentBalance;
import com.alphitardian.tr_pam.models.CryptoList;
import com.alphitardian.tr_pam.models.Transaction;
import com.alphitardian.tr_pam.models.TransactionHistoryList;
import com.alphitardian.tr_pam.models.TransactionResponse;

import java.lang.annotation.Inherited;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiList {
    @GET("/coin/api/v1")
    Call<CryptoList> getAllList();

    @GET("/coin/api/v1/single")
    Call<CryptoList> getSingleCrypto(@Header("coinId") int coinId);

    @GET("/balance/current")
    Call<CurrentBalance> getCurrentBalance(@Header("userId") String userId);

    @POST("/balance")
    Call<BalanceResponse> topUpBalance(@Header("userId") String userId, @Body Balance balance);

    @GET("/transaction/history")
    Call<TransactionHistoryList> getTransactionHistory(@Header("userId") String userId);

    @POST("/transaction/")
    Call<TransactionResponse> createTransaction(@Header("userId") String userId, @Body Transaction transaction);

    @GET("/transaction/assets_in_single")
    Call<AssetsInSingle> getSingleCryptoReport(@Header("userId") String userId, @Header("coinId") int coinId);

    @GET("transaction/my_coin")
    Call<AssetsListResponse> getMyAssetList(@Header("userId") String userId);
}
