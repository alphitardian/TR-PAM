package com.alphitardian.tr_pam.model;

import com.google.gson.annotations.SerializedName;

public class CryptoData {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("symbol")
    private String symbol;

    @SerializedName("quote")
    private CryptoQuote quote;

    public CryptoData(int id, String name, String symbol, CryptoQuote quote) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.quote = quote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public CryptoQuote getQuote() {
        return quote;
    }

    public void setQuote(CryptoQuote quote) {
        this.quote = quote;
    }
}
