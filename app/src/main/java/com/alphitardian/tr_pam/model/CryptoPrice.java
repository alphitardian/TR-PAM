package com.alphitardian.tr_pam.model;

import com.google.gson.annotations.SerializedName;

public class CryptoPrice {

    @SerializedName("current")
    private double current;

    @SerializedName("in_1h")
    private double in1h;

    @SerializedName("in_24h")
    private double in24h;

    @SerializedName("in_7d")
    private double in7d;

    @SerializedName("in_30d")
    private double in30d;

    @SerializedName("in_60d")
    private double in60d;

    @SerializedName("in_90d")
    private double in90d;

    public CryptoPrice(double current, double in1h, double in24h, double in7d, double in30d, double in60d, double in90d) {
        this.current = current;
        this.in1h = in1h;
        this.in24h = in24h;
        this.in7d = in7d;
        this.in30d = in30d;
        this.in60d = in60d;
        this.in90d = in90d;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getIn1h() {
        return in1h;
    }

    public void setIn1h(double in1h) {
        this.in1h = in1h;
    }

    public double getIn24h() {
        return in24h;
    }

    public void setIn24h(double in24h) {
        this.in24h = in24h;
    }

    public double getIn7d() {
        return in7d;
    }

    public void setIn7d(double in7d) {
        this.in7d = in7d;
    }

    public double getIn30d() {
        return in30d;
    }

    public void setIn30d(double in30d) {
        this.in30d = in30d;
    }

    public double getIn60d() {
        return in60d;
    }

    public void setIn60d(double in60d) {
        this.in60d = in60d;
    }

    public double getIn90d() {
        return in90d;
    }

    public void setIn90d(double in90d) {
        this.in90d = in90d;
    }
}
