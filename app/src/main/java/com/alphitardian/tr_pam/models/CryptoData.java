package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class CryptoData {

    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CryptoData(String id, String name, String symbol, String lastUpdate, CryptoPrice price) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.lastUpdate = lastUpdate;
        this.price = price;
    }

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
