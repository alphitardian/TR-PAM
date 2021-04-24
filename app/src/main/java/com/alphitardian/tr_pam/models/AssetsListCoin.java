package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class AssetsListCoin {
    @SerializedName("id")
    private int id;

    @SerializedName("coin")
    private String coin;

    @SerializedName("total")
    private int total;

    public AssetsListCoin(int id, String coin, int total) {
        this.id = id;
        this.coin = coin;
        this.total = total;
    }

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
