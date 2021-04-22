package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryList {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private ArrayList<TransactionHistory> data;

    public TransactionHistoryList(String status, List<TransactionHistory> datas) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TransactionHistory> getData() {
        return data;
    }

    public void setData(ArrayList<TransactionHistory> data) {
        this.data = data;
    }
}
