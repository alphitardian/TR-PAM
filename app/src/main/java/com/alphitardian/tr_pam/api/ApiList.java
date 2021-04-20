package com.alphitardian.tr_pam.api;

import com.alphitardian.tr_pam.model.CryptoData;
import com.alphitardian.tr_pam.model.CryptoList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiList {
    @GET("/coin/api/v1")
    Call<CryptoList> getAllList();
}
