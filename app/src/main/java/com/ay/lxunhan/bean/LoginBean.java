package com.ay.lxunhan.bean;

public class LoginBean {
    private String uqid;
    private int is_perfect;
    private int is_media;
    /**
     * avatar : default_avatar/wlc9r2o1z1h3r0HYRzsffR9ChKT9hkewzSPRCJmr.png
     * nickname : 无噢诶
     * sex : 1
     * autograph : 13234234
     * gold : 1010
     * likeCount : 0
     * folCount : 1
     * beFolCount : 1
     * proportion : 0.0000
     */

    private String avatar;
    private String nickname;
    private int sex;
    private String autograph;
    private int gold;
    private int likeCount;
    private int folCount;
    private int beFolCount;
    private String proportion;
    private int is_phone;

    public boolean getIs_phone() {
        return is_phone==1;
    }

    public void setIs_phone(int is_phone) {
        this.is_phone = is_phone;
    }


    public boolean getIs_media() {//是否为自媒体 true是 false否
        return is_media==1;
    }

    public void setIs_media(int is_media) {
        this.is_media = is_media;
    }

    public boolean getIs_perfect() {//是否完善信息 true是 false否
        return is_perfect==1;
    }

    public void setIs_perfect(int is_perfect) {
        this.is_perfect = is_perfect;
    }

    public String getUqid() {//用户id
        return uqid;
    }

    public void setUqid(String uqid) {
        this.uqid = uqid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean getSex() {
        return sex==1;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getFolCount() {
        return folCount;
    }

    public void setFolCount(int folCount) {
        this.folCount = folCount;
    }

    public int getBeFolCount() {
        return beFolCount;
    }

    public void setBeFolCount(int beFolCount) {
        this.beFolCount = beFolCount;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }
}
