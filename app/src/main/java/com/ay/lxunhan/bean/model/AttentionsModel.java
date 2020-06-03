package com.ay.lxunhan.bean.model;

import com.ay.lxunhan.utils.UserInfo;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean.model
 * @date 2020/6/2
 */
public class AttentionsModel {
    private String uqid;
    private List<String> beuid_data;

    public AttentionsModel(List<String>  be_uid) {
        this.uqid= UserInfo.getInstance().getUserId();
        this.beuid_data = be_uid;
    }
}
