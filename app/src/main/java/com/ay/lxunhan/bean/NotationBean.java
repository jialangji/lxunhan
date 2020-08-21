package com.ay.lxunhan.bean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/12
 */
public class NotationBean {


    /**
     * news_list : [{"aid":23,"avatar":"","content":"6666","cover":"","created_at":"2020-08-07 10:14:49","descs":"评论了你的朋友圈","genre":6,"id":34,"is_media":"","is_read":1,"nickname":"","sex":"","timeText":"2020-08-07","title":"啦啦啦啦"}]
     * tz_content :
     * tz_count : 0
     * tz_timeText :
     */

    private String tz_content;
    private String tz_count;
    private String tz_timeText;
    private List<NewsListBean> news_list;

    public String getTz_content() {
        return tz_content;
    }

    public void setTz_content(String tz_content) {
        this.tz_content = tz_content;
    }

    public String getTz_count() {
        return tz_count;
    }

    public void setTz_count(String tz_count) {
        this.tz_count = tz_count;
    }

    public String getTz_timeText() {
        return tz_timeText;
    }

    public void setTz_timeText(String tz_timeText) {
        this.tz_timeText = tz_timeText;
    }

    public List<NewsListBean> getNews_list() {
        return news_list;
    }

    public void setNews_list(List<NewsListBean> news_list) {
        this.news_list = news_list;
    }

    public static class NewsListBean {
        /**
         * aid : 23
         * avatar :
         * content : 6666
         * cover :
         * created_at : 2020-08-07 10:14:49
         * descs : 评论了你的朋友圈
         * genre : 6
         * id : 34
         * is_media :
         * is_read : 1
         * nickname :
         * sex :
         * timeText : 2020-08-07
         * title : 啦啦啦啦
         */

        private String aid;
        private String avatar;
        private String content;
        private String cover;
        private String created_at;
        private String descs;
        private String genre;
        private String id;
        private String is_media;
        private int is_read;
        private String nickname;
        private String sex;
        private String timeText;
        private String title;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getDescs() {
            return descs;
        }

        public void setDescs(String descs) {
            this.descs = descs;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean getIs_media() {
            return is_media.equals("1");
        }

        public void setIs_media(String is_media) {
            this.is_media = is_media;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getTimeText() {
            return timeText;
        }

        public void setTimeText(String timeText) {
            this.timeText = timeText;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
