package com.ay.lxunhan.bean;

import java.util.List;

public class HomeQuizDetailBean {

    /**
     * id : 1
     * title : 这个App你觉得好用吗？
     * desc : 你对这款App的评价。
     * timeText : 2020-05-13
     * option_list : [{"id":1,"content":"好用","is_selected":0,"count":0,"bfb":0},{"id":2,"content":"一般","is_selected":0,"count":0,"bfb":0},{"id":3,"content":"很差","is_selected":0,"count":0,"bfb":0}]
     * nickname : 广电官方账号
     * avatar : http://www.hlx.com/storage/media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg
     * into : 没啥能说的。
     * is_fow : 1
     * like_count : 0
     * is_like : 0
     * comment_count : 1
     */

    private int id;
    private String title;
    private String desc;
    private String timeText;
    private String nickname;
    private String avatar;
    private String into;
    private String uid;
    private int is_fow;
    private int like_count;
    private int is_like;
    private int comment_count;
    private int is_pate;
    private List<OptionListBean> option_list;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean getIs_pate() {
        return is_pate==1;
    }

    public void setIs_pate(int is_pate) {
        this.is_pate = is_pate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public List<OptionListBean> getOption_list() {
        return option_list;
    }

    public void setOption_list(List<OptionListBean> option_list) {
        this.option_list = option_list;
    }

    public static class OptionListBean {
        /**
         * id : 1
         * content : 好用
         * is_selected : 0
         * count : 0
         * bfb : 0
         */

        private int id;
        private String content;
        private int is_selected;
        private int count;
        private int bfb;
        private boolean userIsSelect;

        public boolean isUserIsSelect() {
            return userIsSelect;
        }

        public void setUserIsSelect(boolean userIsSelect) {
            this.userIsSelect = userIsSelect;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean getIs_selected() {
            return is_selected==1;
        }

        public void setIs_selected(int is_selected) {
            this.is_selected = is_selected;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getBfb() {
            return bfb;
        }

        public void setBfb(int bfb) {
            this.bfb = bfb;
        }
    }
}
