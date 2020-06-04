package com.ay.lxunhan.bean.model;

import com.ay.lxunhan.utils.UserInfo;


public class CompleteInfoModel {
    private String nickname;
    private String date_birth;
    private String sex;
    private String uqid;
    private String plate_id;
    private String city_id;
    private String autograph;
    private String province_id;

    public CompleteInfoModel(String nickname, String date_birth, String sex, String city_id, String autograph, String province_id) {
        this.nickname = nickname;
        this.date_birth = date_birth;
        this.sex = sex;
        this.uqid = UserInfo.getInstance().getUserId();
        this.city_id = city_id;
        this.autograph = autograph;
        this.province_id = province_id;
    }

    public CompleteInfoModel(String uqid, String plate_id) {
        this.uqid = uqid;
        this.plate_id = plate_id;
    }

    public CompleteInfoModel(String nickname, String date_birth, int sex) {
        this.nickname = nickname;
        this.date_birth = date_birth;
        this.sex = String.valueOf(sex);
        this.uqid= UserInfo.getInstance().getUserId();
    }

}
