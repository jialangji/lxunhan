package com.ay.lxunhan.bean.model;

import com.ay.lxunhan.utils.UserInfo;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean.model
 * @date 2020/6/2
 */
public class SmallVideoWatchModel {
    private String uqid;
    private int id;

    public SmallVideoWatchModel(int id) {
        this.id=id;
        this.uqid = UserInfo.getInstance().getUserId();
    }
}
