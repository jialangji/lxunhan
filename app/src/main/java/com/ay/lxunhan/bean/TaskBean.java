package com.ay.lxunhan.bean;

public class TaskBean {


    /**
     * id : 1
     * name : 关注话题
     * icon : task/gUX9ovZedSULyAsl28dAhjaiA5SDp2HubFOSUDUW.png
     * number : 5
     * gold : 20
     * features_desc : 关注话题
     * taskCount : 2
     * taskFlg : 0
     */

    private int id;
    private String name;
    private String icon;
    private int number;
    private int gold;
    private String features_desc;
    private int taskCount;
    private int taskFlg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getFeatures_desc() {
        return features_desc;
    }

    public void setFeatures_desc(String features_desc) {
        this.features_desc = features_desc;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public boolean getTaskFlg() {
        return taskFlg==0;
    }

    public void setTaskFlg(int taskFlg) {
        this.taskFlg = taskFlg;
    }
}
