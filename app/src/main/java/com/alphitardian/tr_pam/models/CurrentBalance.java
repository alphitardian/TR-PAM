package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class CurrentBalance {

    @SerializedName("transaction")
    private BalanceTransaction balanceTransaction;

    public BalanceTransaction getBalanceTransaction() {
        return balanceTransaction;
    }

    public void setBalanceTransaction(BalanceTransaction balanceTransaction) {
        this.balanceTransaction = balanceTransaction;
    }

    @SerializedName("current")
    private String current;

    @SerializedName("top_up")
    private String topUp;

    @SerializedName("withdraw")
    private String withdraw;

    public CurrentBalance(BalanceTransaction balanceTransaction, String current, String topUp, String withdraw) {
        this.balanceTransaction = balanceTransaction;
        this.current = current;
        this.topUp = topUp;
        this.withdraw = withdraw;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getTopUp() {
        return topUp;
    }

    public void setTopUp(String topUp) {
        this.topUp = topUp;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }
}
