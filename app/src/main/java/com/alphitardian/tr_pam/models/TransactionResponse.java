package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class TransactionResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private Transaction data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Transaction getData() {
        return data;
    }

    public void setData(Transaction data) {
        this.data = data;
    }

    public TransactionResponse(String status, Transaction data) {
        this.status = status;
        this.data = data;
    }
}
