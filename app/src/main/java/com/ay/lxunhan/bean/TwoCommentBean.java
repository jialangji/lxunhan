package com.ay.lxunhan.bean;

import java.util.List;

public class TwoCommentBean {


    /**
     * first_show : {"id":8,"content":"就那样啊","timeText":"2020-05-19T07:11:24.000000Z","nickname":"广电官方账号","avatar":"http://www.hlx.com/storage/media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg","autograph":"没啥能说的。","is_media":1,"like_count":1,"is_like":"1","count":2}
     * comment_list : [{"id":12,"cid":2,"content":"二级评论。哈哈","created_at":"2020-05-20 17:15:42","timeText":"2020-05-20","nickname":"无噢诶","avatar":"http://www.hlx.com/storage/default_avatar/wlc9r2o1z1h3r0HYRzsffR9ChKT9hkewzSPRCJmr.png","autograph":"13234234","is_media":0,"bhfr_nickname":"广电官方账号","like_count":0,"is_like":"0"},{"id":13,"cid":3,"content":"二级评论。哈哈","created_at":"2020-05-20 17:15:42","timeText":"2020-05-20","nickname":"广电官方账号","avatar":"http://www.hlx.com/storage/media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg","autograph":"没啥能说的。","is_media":1,"bhfr_nickname":"无噢诶","like_count":0,"is_like":"0"}]
     */

    private FirstShowBean first_show;
    private List<CommentListBean> comment_list;

    public FirstShowBean getFirst_show() {
        return first_show;
    }

    public void setFirst_show(FirstShowBean first_show) {
        this.first_show = first_show;
    }

    public List<CommentListBean> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<CommentListBean> comment_list) {
        this.comment_list = comment_list;
    }

    public static class FirstShowBean {
        /**
         * id : 8
         * content : 就那样啊
         * timeText : 2020-05-19T07:11:24.000000Z
         * nickname : 广电官方账号
         * avatar : http://www.hlx.com/storage/media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg
         * autograph : 没啥能说的。
         * is_media : 1
         * like_count : 1
         * is_like : 1
         * count : 2  评论数量
         */

        private int id;
        private String content;
        private String timeText;
        private String nickname;
        private String avatar;
        private String autograph;
        private int is_media;
        private int like_count;
        private int is_like;
        private int count;

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

        public boolean getIs_media() {
            return is_media==1;
        }

        public void setIs_media(int is_media) {
            this.is_media = is_media;
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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class CommentListBean {
        /**
         * id : 12
         * cid : 2
         * content : 二级评论。哈哈
         * created_at : 2020-05-20 17:15:42
         * timeText : 2020-05-20
         * nickname : 无噢诶
         * avatar : http://www.hlx.com/storage/default_avatar/wlc9r2o1z1h3r0HYRzsffR9ChKT9hkewzSPRCJmr.png
         * autograph : 13234234
         * is_media : 0
         * bhfr_nickname : 广电官方账号
         * like_count : 0
         * is_like : 0
         */

        private int id;
        private int cid;
        private String content;
        private String created_at;
        private String timeText;
        private String nickname;
        private String avatar;
        private String autograph;
        private int is_media;
        private String bhfr_nickname;
        private int like_count;
        private int is_like;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
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

        public boolean getIs_media() {
            return is_media==1;
        }

        public void setIs_media(int is_media) {
            this.is_media = is_media;
        }

        public String getBhfr_nickname() {
            return bhfr_nickname;
        }

        public void setBhfr_nickname(String bhfr_nickname) {
            this.bhfr_nickname = bhfr_nickname;
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
}
