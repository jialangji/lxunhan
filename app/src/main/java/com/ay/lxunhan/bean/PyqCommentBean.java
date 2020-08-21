package com.ay.lxunhan.bean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/11
 */
public class PyqCommentBean {

    /**
     * com_list_count : 4
     * comment_list : [{"aid":29,"avatar":"https://hlx.51appdevelop.com/storage/avatar/MnqbOPCXDzlPBT6dQVn0zAaWPG8NEnQyP4Ve2kkS.png","b_nickname":"阿丽娜","bid":3,"c_nickname":"灰灰","cid":5,"content":"勋勋","created_at":"2020-08-07 14:15:58","fid":0,"id":20,"timeText":"2020-08-07"},{"aid":29,"avatar":"https://hlx.51appdevelop.com/storage/avatar/MnqbOPCXDzlPBT6dQVn0zAaWPG8NEnQyP4Ve2kkS.png","b_nickname":"阿丽娜","bid":3,"c_nickname":"灰灰","cid":5,"content":"呵呵呵金额","created_at":"2020-08-07 15:42:38","fid":0,"id":22,"timeText":"2020-08-07"},{"aid":29,"avatar":"https://hlx.51appdevelop.com/storage/avatar/MnqbOPCXDzlPBT6dQVn0zAaWPG8NEnQyP4Ve2kkS.png","b_nickname":"阿丽娜","bid":3,"c_nickname":"灰灰","cid":5,"content":"想减肥结婚的还打电话","created_at":"2020-08-07 15:45:13","fid":0,"id":23,"timeText":"2020-08-07"},{"aid":29,"avatar":"https://hlx.51appdevelop.com/storage/avatar/MnqbOPCXDzlPBT6dQVn0zAaWPG8NEnQyP4Ve2kkS.png","b_nickname":"阿丽娜","bid":3,"c_nickname":"灰灰","cid":5,"content":"想减肥结婚的还打电话","created_at":"2020-08-07 15:45:22","fid":0,"id":24,"timeText":"2020-08-07"}]
     */

    private int com_list_count;
    private List<CommentListBean> comment_list;

    public int getCom_list_count() {
        return com_list_count;
    }

    public void setCom_list_count(int com_list_count) {
        this.com_list_count = com_list_count;
    }

    public List<CommentListBean> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<CommentListBean> comment_list) {
        this.comment_list = comment_list;
    }

    public static class CommentListBean {
        /**
         * aid : 29
         * avatar : https://hlx.51appdevelop.com/storage/avatar/MnqbOPCXDzlPBT6dQVn0zAaWPG8NEnQyP4Ve2kkS.png
         * b_nickname : 阿丽娜
         * bid : 3
         * c_nickname : 灰灰
         * cid : 5
         * content : 勋勋
         * created_at : 2020-08-07 14:15:58
         * fid : 0
         * id : 20
         * timeText : 2020-08-07
         */

        private int aid;
        private String avatar;
        private String b_nickname;
        private int bid;
        private String c_nickname;
        private int cid;
        private String content;
        private String created_at;
        private int fid;
        private int id;
        private String timeText;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getB_nickname() {
            return b_nickname;
        }

        public void setB_nickname(String b_nickname) {
            this.b_nickname = b_nickname;
        }

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
        }

        public String getC_nickname() {
            return c_nickname;
        }

        public void setC_nickname(String c_nickname) {
            this.c_nickname = c_nickname;
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

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTimeText() {
            return timeText;
        }

        public void setTimeText(String timeText) {
            this.timeText = timeText;
        }
    }
}
