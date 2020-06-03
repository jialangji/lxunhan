package com.ay.lxunhan.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class VideoBean implements MultiItemEntity, Serializable {


    /**
     * id : 2
     * uid : 1
     * title : 图书推荐
     * cover : http://www.hlx.com/storage/video_cover/hn4CZUNP65XyQBukLYCSZ15LlLFmBVgGXpNsFKcP.jpeg
     * video : http://www.hlx.com/storage/video/6zZpAwRJXkNkNiY1NVgKTY8iHShjJ940qMmRZGPe.mp4
     * desc : 我也不知道说点啥！
     * type : 5
     * avatar : http://www.hlx.com/storage/media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg
     * nickname : 广电官方账号
     * like_count : 0
     * comment_count : 0
     * is_fol : 1
     * is_like : 0
     */

    private int id;
    private String uid;
    private String title;
    private String cover;
    private String video;
    private String desc;
    private int type;
    private String avatar;
    private String nickname;
    private int like_count;
    private int comment_count;
    private int is_fol;
    private int is_like;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getIs_fol() {
        return is_fol;
    }

    public void setIs_fol(int is_fol) {
        this.is_fol = is_fol;
    }

    public boolean getIs_like() {
        return is_like==1;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
