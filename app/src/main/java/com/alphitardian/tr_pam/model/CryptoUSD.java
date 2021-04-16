package com.alphitardian.tr_pam.model;

import com.google.gson.annotations.SerializedName;

public class CryptoUSD {

    @SerializedName("price")
    private double price;

    @SerializedName("percent_change_24h")
    private double percentChange24h;

    public CryptoUSD(double price, double percentChange24h) {
        this.price = price;
        this.percentChange24h = percentChange24h;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }
}
