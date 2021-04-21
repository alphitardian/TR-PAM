package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CryptoList {
    @SerializedName("data")
    private ArrayList<CryptoData> data;

    @SerializedName("status")
    private ArrayList<CryptoStatus> status;

    public CryptoList(ArrayList<CryptoData> data, ArrayList<CryptoStatus> status) {
        this.data = data;
        this.status = status;
    }

    public ArrayList<CryptoData> getData() {
        return data;
    }

    public void setData(ArrayList<CryptoData> data) {
        this.data = data;
    }

    public ArrayList<CryptoStatus> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<CryptoStatus> status) {
        this.status = status;
    }
}
