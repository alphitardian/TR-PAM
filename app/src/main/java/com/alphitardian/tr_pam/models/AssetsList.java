package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssetsList {
    @SerializedName("coin")
    private List<AssetsListCoin> coin;

    public AssetsList(List<AssetsListCoin> coin) {
        this.coin = coin;
    }

    public List<AssetsListCoin> getCoin() {
        return coin;
    }

    public void setCoin(List<AssetsListCoin> coin) {
        this.coin = coin;
    }
}
