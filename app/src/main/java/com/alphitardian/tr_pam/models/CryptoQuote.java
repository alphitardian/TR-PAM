package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class CryptoQuote {

    @SerializedName("USD")
    private CryptoUSD usd;

    public CryptoQuote(CryptoUSD usd) {
        this.usd = usd;
    }

    public CryptoUSD getUsd() {
        return usd;
    }

    public void setUsd(CryptoUSD usd) {
        this.usd = usd;
    }
}
