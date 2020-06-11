package com.ay.lxunhan.bean;

public class AttentionBean {


    /**
     * uid : 2
     * nickname : 无噢诶
     * avatar : http://www.hlx.com/storage/default_avatar/wlc9r2o1z1h3r0HYRzsffR9ChKT9hkewzSPRCJmr.png
     * sex : 1
     * is_media : 0
     * fcount : 2
     * is_fol : 2
     */
    

    private String uid;
    private String nickname;
    private String avatar;
    private int sex;
    private int is_media;
    private int fcount;
    private int is_fol;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
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

    public boolean getSex() {
        return sex==1;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getIs_media() {
        return is_media;
    }

    public void setIs_media(int is_media) {
        this.is_media = is_media;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getIs_fol() {
        return is_fol;
    }

    public void setIs_fol(int is_fol) {
        this.is_fol = is_fol;
    }
}
