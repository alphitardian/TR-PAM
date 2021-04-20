package com.alphitardian.tr_pam.model;

import com.google.gson.annotations.SerializedName;

public class CryptoStatus {

    @SerializedName("timestamp")
    private String timeStamp;

    @SerializedName("error_code")
    private int errorCode;

    @SerializedName("error_message")
    private String errorMessage;

    @SerializedName("elapsed")
    private int elapsed;

    @SerializedName("credit_count")
    private int creditCount;

    @SerializedName("notice")
    private String notice;

    @SerializedName("total_count")
    private int totalCount;

    public CryptoStatus(String timeStamp, int errorCode, String errorMessage, int elapsed, int creditCount, String notice, int totalCount) {
        this.timeStamp = timeStamp;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.elapsed = elapsed;
        this.creditCount = creditCount;
        this.notice = notice;
        this.totalCount = totalCount;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getElapsed() {
        return elapsed;
    }

    public void setElapsed(int elapsed) {
        this.elapsed = elapsed;
    }

    public int getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(int creditCount) {
        this.creditCount = creditCount;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
