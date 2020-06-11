package com.ay.lxunhan.bean;

import java.util.List;

public class BillBean {

    /**
     * balance_log : [{"id":1,"type":1,"desc":"兑换-至余额","gold":100,"balance":"10.00","status":1,"created_at":"2020-06-05 15:32:19"}]
     * exchange : 10.00
     * withdraw : 0
     */

    private String exchange;
    private int withdraw;
    private List<BalanceLogBean> balance_log;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public int getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(int withdraw) {
        this.withdraw = withdraw;
    }

    public List<BalanceLogBean> getBalance_log() {
        return balance_log;
    }

    public void setBalance_log(List<BalanceLogBean> balance_log) {
        this.balance_log = balance_log;
    }

    public static class BalanceLogBean {
        /**
         * id : 1
         * type : 1
         * desc : 兑换-至余额
         * gold : 100
         * balance : 10.00
         * status : 1
         * created_at : 2020-06-05 15:32:19
         */

        private int id;
        private int type;
        private String desc;
        private int gold;
        private String balance;
        private int status;
        private String created_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
