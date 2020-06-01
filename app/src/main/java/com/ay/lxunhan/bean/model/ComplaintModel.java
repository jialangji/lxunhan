package com.ay.lxunhan.bean.model;

import com.ay.lxunhan.utils.UserInfo;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean.model
 * @date 2020/5/28
 */
public class ComplaintModel {
    private String uqid;
    private String aid;
    private int type;
    private int cp_type;
    private String content;

    public ComplaintModel( String aid, int type, int cp_type) {
        this.uqid = UserInfo.getInstance().getUserId();
        this.aid = aid;
        this.type = type;
        this.cp_type = cp_type;
    }

    public ComplaintModel(String aid, int type, int cp_type, String content) {
        this.uqid = UserInfo.getInstance().getUserId();
        this.aid = aid;
        this.type = type;
        this.cp_type = cp_type;
        this.content = content;
    }
}
