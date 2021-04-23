package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("id")
    private int id;

    @SerializedName("coin")
    private String coin;

    @SerializedName("type")
    private String type;

    @SerializedName("price")
    private double price;

    @SerializedName("amount")
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Transaction(int id, String coin, String type, double price, int amount) {
        this.id = id;
        this.coin = coin;
        this.type = type;
        this.price = price;
        this.amount = amount;
    }
}
