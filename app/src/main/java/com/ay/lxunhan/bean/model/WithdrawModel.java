package com.ay.lxunhan.bean.model;

import com.ay.lxunhan.utils.UserInfo;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean.model
 * @date 2020/6/11
 */
public class WithdrawModel {
    private String uqid;
    private int type;
    private String balance;
    private String account;

    public WithdrawModel(int type, String balance, String account) {
        this.uqid = UserInfo.getInstance().getUserId();
        this.type = type;
        this.balance = balance;
        this.account = account;
    }
}
