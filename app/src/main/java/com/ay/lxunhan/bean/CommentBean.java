package com.ay.lxunhan.bean;

public class CommentBean {


    /**
     * id : 8
     * content : 就那样啊
     * timeText : 2020-05-19
     * nickname : 广电官方账号
     * avatar : http://www.hlx.com/storage/media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg
     * autograph : 没啥能说的。
     * is_two : 1
     * two_arr : {"name":"无噢诶","count":1}
     * is_media : 1
     * is_like :
     * like_count :
     */

    private int id;
    private String content;
    private String timeText;
    private String nickname;
    private String avatar;
    private String autograph;
    private int is_two;
    private TwoArrBean two_arr;
    private int is_media;
    private int is_like;
    private int like_count;
    /**
     * is_media : 1
     * is_like :
     * comment_count :
     * is_adoption : 0
     */

    private int comment_count;
    private int is_adoption;

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

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public boolean getIs_two() {
        return is_two==1;
    }

    public void setIs_two(int is_two) {
        this.is_two = is_two;
    }

    public TwoArrBean getTwo_arr() {
        return two_arr;
    }

    public void setTwo_arr(TwoArrBean two_arr) {
        this.two_arr = two_arr;
    }

    public boolean getIs_media() {
        return is_media==1;
    }

    public void setIs_media(int is_media) {
        this.is_media = is_media;
    }

    public boolean getIs_like() {
        return is_like==1;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
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

    public boolean getIs_adoption() {
        return is_adoption==1;
    }

    public void setIs_adoption(int is_adoption) {
        this.is_adoption = is_adoption;
    }

    public static class TwoArrBean {
        /**
         * name : 无噢诶
         * count : 1
         */

        private String name;
        private int count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
