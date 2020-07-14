package com.ay.lxunhan.bean;

import java.io.Serializable;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/7/13
 */
public class RechargeBean implements Serializable {
    private boolean isSelect;
    /**
     * id : 1
     * money : 10
     * gold : 100
     */

    private String id;
    private String money;
    private String gold;


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }
}
