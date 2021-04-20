package com.alphitardian.tr_pam.models;

public class UserDetail {
    private String username;
    private String fullName;
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    private String photo_path;

    public UserDetail(String username, String fullname, String address, String photo_path) {
        this.username = username;
        this.fullName = fullname;
        this.address = address;
        this.photo_path = photo_path;
    }
}
