package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class BalanceResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private Balance data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Balance getData() {
        return data;
    }

    public void setData(Balance data) {
        this.data = data;
    }

    public BalanceResponse(String status, Balance data) {
        this.status = status;
        this.data = data;
    }
}
