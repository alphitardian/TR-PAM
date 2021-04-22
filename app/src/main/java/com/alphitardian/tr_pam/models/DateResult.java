package com.alphitardian.tr_pam.models;

import com.google.gson.annotations.SerializedName;

public class DateResult {

    @SerializedName("seconds")
    private long seconds;

    @SerializedName("nanoseconds")
    private long nanoseconds;

    public DateResult(long seconds, long nanoseconds) {
        this.seconds = seconds;
        this.nanoseconds = nanoseconds;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getNanoseconds() {
        return nanoseconds;
    }

    public void setNanoseconds(long nanoseconds) {
        this.nanoseconds = nanoseconds;
    }
}
