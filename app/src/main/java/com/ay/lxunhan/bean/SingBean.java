package com.ay.lxunhan.bean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/12
 */
public class SingBean {

    /**
     * signcount : 0
     * count : 0
     * last_modify_time : 0000-00-00 00:00:00
     * signflg : 0
     * signs : [{"id":1,"cday":1,"gold":11},{"id":2,"cday":2,"gold":12},{"id":3,"cday":3,"gold":13},{"id":4,"cday":4,"gold":14},{"id":5,"cday":5,"gold":15},{"id":6,"cday":6,"gold":16},{"id":7,"cday":7,"gold":17}]
     */

    private String signcount;
    private int count;
    private String last_modify_time;
    private int signflg;
    private List<SignsBean> signs;
    private String gold;

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getSigncount() {
        return signcount;
    }

    public void setSigncount(String signcount) {
        this.signcount = signcount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLast_modify_time() {
        return last_modify_time;
    }

    public void setLast_modify_time(String last_modify_time) {
        this.last_modify_time = last_modify_time;
    }

    public boolean getSignflg() {
        return signflg==1;
    }

    public void setSignflg(int signflg) {
        this.signflg = signflg;
    }

    public List<SignsBean> getSigns() {
        return signs;
    }

    public void setSigns(List<SignsBean> signs) {
        this.signs = signs;
    }

    public static class SignsBean {
        /**
         * id : 1
         * cday : 1
         * gold : 11
         */

        private int id;
        private int cday;
        private int gold;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCday() {
            return cday;
        }

        public void setCday(int cday) {
            this.cday = cday;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }
    }
}
