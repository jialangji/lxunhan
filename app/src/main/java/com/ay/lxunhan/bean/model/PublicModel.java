package com.ay.lxunhan.bean.model;

public class PublicModel {
    private String phone;
    private String code;
    private int type;
    private String password;
    private String password_confirmation;
    private String platform;

    public PublicModel(String phone, String code, String platform, int type) {
        this.phone = phone;
        this.type = type;
        this.platform = platform;
        this.password=code;
    }
    public PublicModel(String phone, String code, int type, String platform) {
        this.phone = phone;
        this.type = type;
        this.platform = platform;
        this.code=code;
    }

    public PublicModel(String phone, String code, int type, String password, String password_confirmation) {
        this.phone = phone;
        this.code = code;
        this.type = type;
        this.password = password;
        this.password_confirmation = password_confirmation;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }
}
