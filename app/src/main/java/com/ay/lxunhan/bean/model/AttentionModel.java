package com.ay.lxunhan.bean.model;

import com.ay.lxunhan.utils.UserInfo;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean.model
 * @date 2020/6/2
 */
public class AttentionModel {
    private String uqid;
    private String be_uid;

    public AttentionModel(String be_uid) {
        this.uqid= UserInfo.getInstance().getUserId();
        this.be_uid = be_uid;
    }
}
