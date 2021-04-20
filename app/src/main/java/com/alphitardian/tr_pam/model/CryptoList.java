package com.alphitardian.tr_pam.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CryptoList {
    @SerializedName("data")
    private ArrayList<CryptoData> data;

    public CryptoList(ArrayList<CryptoData> data) {
        this.data = data;
    }

    public ArrayList<CryptoData> getData() {
        return data;
    }

    public void setData(ArrayList<CryptoData> data) {
        this.data = data;
    }
}
