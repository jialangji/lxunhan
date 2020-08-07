package com.ay.lxunhan.bean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/7/21
 */
public class LiveDetail {

    /**
     * avatar : http://www.hlx.com/storage/avatar/RPIudn6sGy8J4JhsWIMRzqkm8OpceFKa82Sr3HSR.jpeg
     * lname : zhang的直播间
     * is_fol : 1
     * rd : 0
     * user_list : [{"avatar":"http://www.hlx.com/storage/default_avatar/wlc9r2o1z1h3r0HYRzsffR9ChKT9hkewzSPRCJmr.png","id":2,"gold":0},{"avatar":"http://www.hlx.com/storage/default_avatar/wlc9r2o1z1h3r0HYRzsffR9ChKT9hkewzSPRCJmr.png","id":3,"gold":0}]
     */

    private String avatar;
    private String lname;
    private int is_fol;
    private int rd;
    private String id;
    private List<UserListBean> user_list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getIs_fol() {
        return is_fol;
    }

    public void setIs_fol(int is_fol) {
        this.is_fol = is_fol;
    }

    public int getRd() {
        return rd;
    }

    public void setRd(int rd) {
        this.rd = rd;
    }

    public List<UserListBean> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<UserListBean> user_list) {
        this.user_list = user_list;
    }

    public static class UserListBean {
        /**
         * avatar : http://www.hlx.com/storage/default_avatar/wlc9r2o1z1h3r0HYRzsffR9ChKT9hkewzSPRCJmr.png
         * id : 2
         * gold : 0
         */

        private String avatar;
        private int id;
        private int gold;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }
    }
}
