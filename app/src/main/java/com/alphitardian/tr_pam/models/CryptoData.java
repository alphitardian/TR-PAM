package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class CryptoData {

    @SerializedName("coinName")
    private String name;

    @SerializedName("symbol")
    private String symbol;

    @SerializedName("lastUpdate")
    private String lastUpdate;

    @SerializedName("price")
    private CryptoPrice price;

    public CryptoData(String name, String symbol, String lastUpdate, CryptoPrice price) {
        this.name = name;
        this.symbol = symbol;
        this.lastUpdate = lastUpdate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public CryptoPrice getPrice() {
        return price;
    }

    public void setPrice(CryptoPrice price) {
        this.price = price;
    }
}
