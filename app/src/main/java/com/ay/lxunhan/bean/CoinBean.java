package com.ay.lxunhan.bean;

public class CoinBean {

    /**
     * type : 1
     * gold : 40
     * status : 1
     * desc : 发表评论任务完成
     * created_at :
     */

    private int type;
    private int gold;
    private int status;
    private String desc;
    private String created_at;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public boolean getStatus() {
        return status==1;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
