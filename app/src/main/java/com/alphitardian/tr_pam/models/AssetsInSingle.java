package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class AssetsInSingle {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private AssetsInSingleData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AssetsInSingleData getData() {
        return data;
    }

    public void setData(AssetsInSingleData data) {
        this.data = data;
    }

    public AssetsInSingle(String status, AssetsInSingleData data) {
        this.status = status;
        this.data = data;
    }
}
