package com.alphitardian.tr_pam.apis;

import com.alphitardian.tr_pam.models.CryptoList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiList {
    @Headers("X-CMC_PRO_API_KEY: de6a3287-fcbd-4907-8a26-ea2d5d15517f")
    @GET("/v1/cryptocurrency/listings/latest")
    Call<CryptoList> getAllList();
}
