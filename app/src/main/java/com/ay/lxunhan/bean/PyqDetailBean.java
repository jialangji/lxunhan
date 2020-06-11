package com.ay.lxunhan.bean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/9
 */
public class PyqDetailBean {

    /**
     * circle_show : {"id":1,"uid":2,"nickname":"无噢诶","sex":1,"avatar":"default_avatar/wlc9r2o1z1h3r0HYRzsffR9ChKT9hkewzSPRCJmr.png","autograph":"13234234","title":"希望半年后的自己也很幸福","image":"circle/UQ2zvyj1L5Mlg0a7a687pacB7H98zNwV1OENjp4H.jpeg,circle/bWKC48MnFcf6HxJBCuKBqswiG1T61PLjmxsjjMqD.jpeg","created_at":"2020-06-02 16:43:35","type":6,"like_count":1,"comment_count":2,"is_my":1,"is_like":1,"image_arr":["http://www.hlx.com/storage/circle/UQ2zvyj1L5Mlg0a7a687pacB7H98zNwV1OENjp4H.jpeg","http://www.hlx.com/storage/circle/bWKC48MnFcf6HxJBCuKBqswiG1T61PLjmxsjjMqD.jpeg"],"timeText":""}
     * like_list : [{"uid":2,"avatar":"http://www.hlx.com/storage/default_avatar/wlc9r2o1z1h3r0HYRzsffR9ChKT9hkewzSPRCJmr.png"}]
     */

    private CircleShowBean circle_show;
    private List<LikeListBean> like_list;

    public CircleShowBean getCircle_show() {
        return circle_show;
    }

    public void setCircle_show(CircleShowBean circle_show) {
        this.circle_show = circle_show;
    }

    public List<LikeListBean> getLike_list() {
        return like_list;
    }

    public void setLike_list(List<LikeListBean> like_list) {
        this.like_list = like_list;
    }

    public static class CircleShowBean {
        /**
         * id : 1
         * uid : 2
         * nickname : 无噢诶
         * sex : 1
         * avatar : default_avatar/wlc9r2o1z1h3r0HYRzsffR9ChKT9hkewzSPRCJmr.png
         * autograph : 13234234
         * title : 希望半年后的自己也很幸福
         * image : circle/UQ2zvyj1L5Mlg0a7a687pacB7H98zNwV1OENjp4H.jpeg,circle/bWKC48MnFcf6HxJBCuKBqswiG1T61PLjmxsjjMqD.jpeg
         * created_at : 2020-06-02 16:43:35
         * type : 6
         * like_count : 1
         * comment_count : 2
         * is_my : 1
         * is_like : 1
         * image_arr : ["http://www.hlx.com/storage/circle/UQ2zvyj1L5Mlg0a7a687pacB7H98zNwV1OENjp4H.jpeg","http://www.hlx.com/storage/circle/bWKC48MnFcf6HxJBCuKBqswiG1T61PLjmxsjjMqD.jpeg"]
         * timeText :
         */

        private int id;
        private String uid;
        private String nickname;
        private int sex;
        private String avatar;
        private String autograph;
        private String title;
        private String image;
        private String created_at;
        private int type;
        private int like_count;
        private int comment_count;
        private int is_my;
        private int is_like;
        private String timeText;
        private List<String> image_arr;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public int getIs_my() {
            return is_my;
        }

        public void setIs_my(int is_my) {
            this.is_my = is_my;
        }

        public boolean getIs_like() {
            return is_like==1;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public String getTimeText() {
            return timeText;
        }

        public void setTimeText(String timeText) {
            this.timeText = timeText;
        }

        public List<String> getImage_arr() {
            return image_arr;
        }

        public void setImage_arr(List<String> image_arr) {
            this.image_arr = image_arr;
        }
    }

    public static class LikeListBean {
        /**
         * uid : 2
         * avatar : http://www.hlx.com/storage/default_avatar/wlc9r2o1z1h3r0HYRzsffR9ChKT9hkewzSPRCJmr.png
         */

        private int uid;
        private String avatar;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
