package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TransactionOnHistory {
    @SerializedName("type")
    private String type;

    @SerializedName("coin")
    private int coin;

    @SerializedName("price")
    private double price;

    @SerializedName("amount")
    private double amount;

    @SerializedName("date")
    private DateResult date;

    public TransactionOnHistory(String type, int coin, double price, double amount, DateResult date) {
        this.type = type;
        this.coin = coin;
        this.price = price;
        this.amount = amount;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public DateResult getDate() {
        return date;
    }

    public void setDate(DateResult date) {
        this.date = date;
    }
}