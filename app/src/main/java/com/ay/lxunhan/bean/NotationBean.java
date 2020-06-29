package com.ay.lxunhan.bean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/12
 */
public class NotationBean {

    /**
     * tz : 自媒体申请已通过
     * tz_count : 0
     * news_list : [{"id":2,"content":"hahaha","created_at":"2020-06-09 11:38:43","aid":34,"genre":3,"is_read":1,"avatar":"http://www.hlx.com/storage/avatar/TfGFKohSirPSPDV0Q9M0nn6TNr7t0nNCQARFnZJX.png","nickname":"卡卡","is_media":1,"sex":1,"timeText":"9分钟前","title":"有效的治疗方案。有效的治疗方案。有效的治疗方案。有效的治疗方案。","cover":""}]
     * tz_timeText :
     */

    private String tz;
    private int tz_count;
    private String tz_timeText;
    private List<NewsListBean> news_list;

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public int getTz_count() {
        return tz_count;
    }

    public void setTz_count(int tz_count) {
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
         * id : 2
         * content : hahaha
         * created_at : 2020-06-09 11:38:43
         * aid : 34
         * genre : 3
         * is_read : 1
         * avatar : http://www.hlx.com/storage/avatar/TfGFKohSirPSPDV0Q9M0nn6TNr7t0nNCQARFnZJX.png
         * nickname : 卡卡
         * is_media : 1
         * sex : 1
         * timeText : 9分钟前
         * title : 有效的治疗方案。有效的治疗方案。有效的治疗方案。有效的治疗方案。
         * cover :
         */

        private int id;
        private String content;
        private String created_at;
        private int aid;
        private int genre;
        private int is_read;
        private String avatar;
        private String nickname;
        private int is_media;
        private int sex;
        private String timeText;
        private String title;
        private String cover;
        private String descs;

        public String getDescs() {
            return descs;
        }

        public void setDescs(String descs) {
            this.descs = descs;
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

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public int getGenre() {
            return genre;
        }

        public void setGenre(int genre) {
            this.genre = genre;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
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

        public boolean getIs_media() {
            return is_media==1;
        }

        public void setIs_media(int is_media) {
            this.is_media = is_media;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
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

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }
}
