package com.alphitardian.tr_pam.api;

import com.alphitardian.tr_pam.model.CryptoData;
import com.alphitardian.tr_pam.model.CryptoList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiList {
    @Headers("X-CMC_PRO_API_KEY: 90a92416-bde3-4252-80f9-c8d72237863e")
    @GET("/v1/cryptocurrency/listings/latest")
    Call<CryptoList> getAllList();
}
