package com.ay.lxunhan.bean;

public class HomeDetailBean {

    /**
     * id : 36
     * uid : 1
     * title : 问答测试001
     * plate_name : null
     * content : <p>我是问答<img src="http://www.hlx.com/ueditor/php/upload/image/20200520/1589959660386881.jpg" title="1589959660386881.jpg" alt="1-13122P91A1S9.jpg"/></p>
     * created_at : 2020-05-19 16:54:52
     * bounty_gold : 100
     * is_solve : 0
     * timeText : 22小时前
     * nickname : 广电官方账号
     * avatar : http://www.hlx.com/storage/media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg
     * into : 没啥能说的。
     * is_fow : 1
     * like_count : 1
     * is_like : 1
     */

    private int id;
    private String uid;
    private String title;
    private String plate_name;
    private String content;
    private String created_at;
    private String plate_id;
    private int bounty_gold;
    private int is_solve;
    private String timeText;
    private String nickname;
    private String avatar;
    private String into;
    private int is_fow;
    private int like_count;
    private int is_like;
    private int sex;
    private String share_url;
    private String piiic_url;

    public String getPlate_id() {
        return plate_id;
    }

    public void setPlate_id(String plate_id) {
        this.plate_id = plate_id;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getPiiic_url() {
        return piiic_url;
    }

    public void setPiiic_url(String piiic_url) {
        this.piiic_url = piiic_url;
    }
    public boolean getSex() {
        return sex==1;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlate_name() {
        return plate_name;
    }

    public void setPlate_name(String plate_name) {
        this.plate_name = plate_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getBounty_gold() {
        return bounty_gold;
    }

    public void setBounty_gold(int bounty_gold) {
        this.bounty_gold = bounty_gold;
    }

    public boolean getIs_solve() {
        return is_solve==1;
    }

    public void setIs_solve(int is_solve) {
        this.is_solve = is_solve;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
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

    public String getInto() {
        return into;
    }

    public void setInto(String into) {
        this.into = into;
    }

    public int getIs_fow() {
        return is_fow;
    }

    public void setIs_fow(int is_fow) {
        this.is_fow = is_fow;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public boolean getIs_like() {
        return is_like==1;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }
}
