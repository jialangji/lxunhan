package com.ay.lxunhan.bean;

public class LoginBean {
    private String uqid;
    private int is_perfect;
    private int is_media;

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
}
