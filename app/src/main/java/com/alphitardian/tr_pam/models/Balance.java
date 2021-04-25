package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class Balance {
    @SerializedName("amount")
    private double amount;

    @SerializedName("gateway")
    private String gateway;

    @SerializedName("type")
    private String type;

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Balance(double amount, String gateway, String type) {
        this.amount = amount;
        this.gateway = gateway;
        this.type = type;
    }
}
