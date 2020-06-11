package com.ay.lxunhan.bean.model;

import com.ay.lxunhan.utils.UserInfo;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean.model
 * @date 2020/6/10
 */
public class LbModel {
    private String uqid;
    private String gold;

    public LbModel(String gold) {
        this.uqid= UserInfo.getInstance().getUserId();
        this.gold = gold;
    }
}
