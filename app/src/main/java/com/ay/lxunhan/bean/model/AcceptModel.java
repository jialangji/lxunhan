package com.ay.lxunhan.bean.model;

import com.ay.lxunhan.utils.UserInfo;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean.model
 * @date 2020/5/26
 */
public class AcceptModel {
    private String uqid;
    private int id;
    private int cid;

    public AcceptModel(int id, int cid) {
        this.uqid= UserInfo.getInstance().getUserId();
        this.id = id;
        this.cid = cid;
    }
}
