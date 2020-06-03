package com.ay.lxunhan.bean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/2
 */
public class AddFriendBean {

    private List<HotPeopleBean> hot_people;
    private List<FollowsListBean> follows_list;

    public List<HotPeopleBean> getHot_people() {
        return hot_people;
    }

    public void setHot_people(List<HotPeopleBean> hot_people) {
        this.hot_people = hot_people;
    }

    public List<FollowsListBean> getFollows_list() {
        return follows_list;
    }

    public void setFollows_list(List<FollowsListBean> follows_list) {
        this.follows_list = follows_list;
    }

    public static class HotPeopleBean {
        /**
         * uid : 1
         * is_media : 1
         * nickname : 广电官方账号
         * avatar : http://www.hlx.com/storage/media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg
         * autograph : 没啥能说的。
         * count : 1
         */

        private String uid;
        private int is_media;
        private String nickname;
        private String avatar;
        private String autograph;
        private int count;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public int getIs_media() {
            return is_media;
        }

        public void setIs_media(int is_media) {
            this.is_media = is_media;
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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class FollowsListBean {
        /**
         * id : 1
         * sex : 1
         * uid : 1
         * nickname : 广电官方账号
         * avatar : http://www.hlx.com/storage/media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg
         * autograph : 没啥能说的。
         * is_fol : 1
         */

        private int id;
        private int sex;
        private String uid;
        private String nickname;
        private String avatar;
        private String autograph;
        private int is_fol;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
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

        public int getIs_fol() {
            return is_fol;
        }

        public void setIs_fol(int is_fol) {
            this.is_fol = is_fol;
        }
    }
}
