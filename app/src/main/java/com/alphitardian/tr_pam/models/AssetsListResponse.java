package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssetsListResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private AssetsList data;

    public AssetsListResponse(String status, AssetsList data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AssetsList getData() {
        return data;
    }

    public void setData(AssetsList data) {
        this.data = data;
    }
}
