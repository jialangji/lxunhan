package com.ay.lxunhan.bean;

public class UserInfoBean {

    /**
     * nickname : 卡卡
     * avatar : http://www.hlx.com/storage/avatar/RPIudn6sGy8J4JhsWIMRzqkm8OpceFKa82Sr3HSR.jpeg
     * sex : 1
     * date_birth : 1988-01-05
     * province_id : 8
     * city_id : 66
     * autograph : 13234234
     * balance : 0.00
     * gold : 0
     * is_media : 1
     * age : 32年
     * address : 贵州嘉峪关
     */

    private String signal;
    private String nickname;
    private String avatar;
    private int sex;
    private String date_birth;
    private String province_id;
    private String city_id;
    private String autograph;
    private String balance;
    private int gold;
    private int is_media;
    private String age;
    private String address;

    public String getNickname() {
        return nickname;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean getSex() {//sex=1为男 否则为女
        return sex==1;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(String date_birth) {
        this.date_birth = date_birth;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getIs_media() {
        return is_media;
    }

    public void setIs_media(int is_media) {
        this.is_media = is_media;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
