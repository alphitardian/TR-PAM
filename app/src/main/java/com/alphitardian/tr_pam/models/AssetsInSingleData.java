package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class AssetsInSingleData {
    @SerializedName("amount")
    private int amount;

    @SerializedName("totalAsset")
    private Double totalAsset;

    @SerializedName("avgBuy")
    private Double avgBuy;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Double getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(Double totalAsset) {
        this.totalAsset = totalAsset;
    }

    public Double getAvgBuy() {
        return avgBuy;
    }

    public void setAvgBuy(Double avgBuy) {
        this.avgBuy = avgBuy;
    }

    public AssetsInSingleData(int amount, Double totalAsset, Double avgBuy) {
        this.amount = amount;
        this.totalAsset = totalAsset;
        this.avgBuy = avgBuy;
    }
}
