package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class BalanceTransaction {
    @SerializedName("transactionBuy")
    private double transactionBuy;

    @SerializedName("transactionSell")
    private double transactionSell;

    public double getTransactionBuy() {
        return transactionBuy;
    }

    public void setTransactionBuy(double transactionBuy) {
        this.transactionBuy = transactionBuy;
    }

    public double getTransactionSell() {
        return transactionSell;
    }

    public void setTransactionSell(double transactionSell) {
        this.transactionSell = transactionSell;
    }

    public BalanceTransaction(double transactionBuy, double transactionSell) {
        this.transactionBuy = transactionBuy;
        this.transactionSell = transactionSell;
    }
}
