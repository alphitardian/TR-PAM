package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionHistory {
    @SerializedName("id")
    private String id;

    @SerializedName("transaction")
    private TransactionOnHistory transaction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransactionOnHistory getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionOnHistory transaction) {
        this.transaction = transaction;
    }

    public TransactionHistory(String id, TransactionOnHistory transaction) {
        this.id = id;
        this.transaction = transaction;
    }
}
