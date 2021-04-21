package com.alphitardian.tr_pam.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CryptoPrice implements Parcelable {

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

    protected CryptoPrice(Parcel in) {
        current = in.readDouble();
        in1h = in.readDouble();
        in24h = in.readDouble();
        in7d = in.readDouble();
        in30d = in.readDouble();
        in60d = in.readDouble();
        in90d = in.readDouble();
    }

    public static final Creator<CryptoPrice> CREATOR = new Creator<CryptoPrice>() {
        @Override
        public CryptoPrice createFromParcel(Parcel in) {
            return new CryptoPrice(in);
        }

        @Override
        public CryptoPrice[] newArray(int size) {
            return new CryptoPrice[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(current);
        dest.writeDouble(in1h);
        dest.writeDouble(in24h);
        dest.writeDouble(in7d);
        dest.writeDouble(in30d);
        dest.writeDouble(in60d);
        dest.writeDouble(in90d);
    }
}
