package com.ay.lxunhan.bean;

import java.util.List;

public class CommentBean {


    /**
     * comment_list : [{"aid":38,"autograph":"来吧，展示","avatar":"https://hlx.51appdevelop.com/storage/avatar/x4gzfkj9G7GPAbIrLRkaqmsrCqERaKZVlqLlgCc5.png","cid":7,"content":"5555","created_at":"2020-08-16 14:12:38","id":48,"is_like":"0","is_media":0,"is_two":0,"like_count":0,"nickname":"久离","timeText":"刚刚","two_arr":{"count":0,"name":""}},{"aid":38,"avatar":"https://hlx.51appdevelop.com/storage/default_avatar/0CN5bgrBYnweUsB5x08EMaHHj3e7rV6acuqI6KmQ.png","cid":11,"content":"哈哈","created_at":"2020-08-14 19:09:03","id":45,"is_like":"0","is_media":0,"is_two":0,"like_count":0,"nickname":"设计","timeText":"2020-08-14","two_arr":{"count":0,"name":""}},{"aid":38,"avatar":"https://hlx.51appdevelop.com/storage/default_avatar/0CN5bgrBYnweUsB5x08EMaHHj3e7rV6acuqI6KmQ.png","cid":11,"content":"对呀","created_at":"2020-08-14 19:08:57","id":44,"is_like":"0","is_media":0,"is_two":0,"like_count":0,"nickname":"设计","timeText":"2020-08-14","two_arr":{"count":0,"name":""}},{"aid":38,"avatar":"https://hlx.51appdevelop.com/storage/default_avatar/0CN5bgrBYnweUsB5x08EMaHHj3e7rV6acuqI6KmQ.png","cid":10,"content":"李敏OK您咯娄摸头诺同敏敏公民咯OK你影音给我姓名我公公","created_at":"2020-08-03 19:01:39","id":14,"is_like":"0","is_media":1,"is_two":1,"like_count":1,"nickname":"爱炎科技","timeText":"2020-08-03","two_arr":{"count":2,"name":"爱炎科技"}},{"aid":38,"avatar":"https://hlx.51appdevelop.com/storage/default_avatar/0CN5bgrBYnweUsB5x08EMaHHj3e7rV6acuqI6KmQ.png","cid":10,"content":"我送你","created_at":"2020-08-03 19:01:25","id":13,"is_like":"0","is_media":1,"is_two":0,"like_count":0,"nickname":"爱炎科技","timeText":"2020-08-03","two_arr":{"count":0,"name":""}},{"aid":38,"avatar":"https://hlx.51appdevelop.com/storage/default_avatar/0CN5bgrBYnweUsB5x08EMaHHj3e7rV6acuqI6KmQ.png","cid":10,"content":"静默公民红米民工你明明哦咯你莫咯咯咯哦咯咯","created_at":"2020-08-03 19:00:32","id":11,"is_like":"0","is_media":1,"is_two":0,"like_count":0,"nickname":"爱炎科技","timeText":"2020-08-03","two_arr":{"count":0,"name":""}}]
     * count_num : 8
     */

    private int count_num;
    private List<CommentListBean> comment_list;

    public int getCount_num() {
        return count_num;
    }

    public void setCount_num(int count_num) {
        this.count_num = count_num;
    }

    public List<CommentListBean> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<CommentListBean> comment_list) {
        this.comment_list = comment_list;
    }

    public static class CommentListBean {
        /**
         * aid : 38
         * autograph : 来吧，展示
         * avatar : https://hlx.51appdevelop.com/storage/avatar/x4gzfkj9G7GPAbIrLRkaqmsrCqERaKZVlqLlgCc5.png
         * cid : 7
         * content : 5555
         * created_at : 2020-08-16 14:12:38
         * id : 48
         * is_like : 0
         * is_media : 0
         * is_two : 0
         * like_count : 0
         * nickname : 久离
         * timeText : 刚刚
         * two_arr : {"count":0,"name":""}
         */
        private int aid;
        private String autograph;
        private String avatar;
        private int cid;
        private String content;
        private String created_at;
        private int id;
        private int is_like;
        private int is_media;
        private int is_two;
        private int like_count;
        private String nickname;
        private String timeText;
        private TwoArrBean two_arr;
        private int is_adoption;

        public boolean getIs_adoption() {
            return is_adoption==1;
        }

        public void setIs_adoption(int is_adoption) {
            this.is_adoption = is_adoption;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUid() {
            return String.valueOf(cid);
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean getIs_like() {
            return is_like==1;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public boolean getIs_media() {
            return is_media==1;
        }

        public void setIs_media(int is_media) {
            this.is_media = is_media;
        }

        public boolean getIs_two() {
            return is_two==1;
        }

        public void setIs_two(int is_two) {
            this.is_two = is_two;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTimeText() {
            return timeText;
        }

        public void setTimeText(String timeText) {
            this.timeText = timeText;
        }

        public TwoArrBean getTwo_arr() {
            return two_arr;
        }

        public void setTwo_arr(TwoArrBean two_arr) {
            this.two_arr = two_arr;
        }

        public static class TwoArrBean {
            /**
             * count : 0
             * name :
             */

            private int count;
            private String name;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
