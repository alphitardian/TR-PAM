package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class TransactionOnHistory {

    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("type")
    private String type;

    @SerializedName("coin")
    private String coin;

    @SerializedName("price")
    private double price;

    @SerializedName("amount")
    private double amount;

    @SerializedName("date")
    private DateResult date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
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

    public TransactionOnHistory(int id, String type, String coin, double price, double amount, DateResult date) {
        this.id = id;
        this.type = type;
        this.coin = coin;
        this.price = price;
        this.amount = amount;
        this.date = date;
    }
}
