package com.alphitardian.tr_pam.apis;

import com.alphitardian.tr_pam.models.CryptoList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiList {
    @GET("/coin/api/v1")
    Call<CryptoList> getAllList();
}
