package com.ay.lxunhan.bean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/29
 */
public class InviteBean {


    /**
     * data : [{"id":4,"signal":"165469501198","gold":10,"created_date":"2020-06-29"},{"id":5,"signal":"165469501198","gold":10,"created_date":"2020-06-28"}]
     * gold : {"value":"10"}
     */

    private GoldBean gold;
    private List<DataBean> data;
    /**
     * user : {"invite_code":"123456"}
     */

    private UserBean user;

    public GoldBean getGold() {
        return gold;
    }

    public void setGold(GoldBean gold) {
        this.gold = gold;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class GoldBean {
        /**
         * value : 10
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class DataBean {
        /**
         * id : 4
         * signal : 165469501198
         * gold : 10
         * created_date : 2020-06-29
         */

        private int id;
        private String signal;
        private int gold;
        private String created_date;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSignal() {
            return signal;
        }

        public void setSignal(String signal) {
            this.signal = signal;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }
    }

    public static class UserBean {
        /**
         * invite_code : 123456
         */

        private String invite_code;

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }
    }
}
