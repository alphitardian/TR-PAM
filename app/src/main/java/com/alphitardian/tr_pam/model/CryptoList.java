package com.alphitardian.tr_pam.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CryptoList {
    @SerializedName("data")
    private List<CryptoData> data;

    public CryptoList(List<CryptoData> data) {
        this.data = data;
    }

    public List<CryptoData> getData() {
        return data;
    }

    public void setData(List<CryptoData> data) {
        this.data = data;
    }
}
