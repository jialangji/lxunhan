package com.ay.lxunhan.bean.model;

import com.ay.lxunhan.utils.UserInfo;

public class SendCommentModel {
    private String uqid;
    private String aid;
    private String bid; //被回复人id
    private int type;
    private String content;
    private String fid;
    private String com_id;
    private int id;
    private int oid;


    public SendCommentModel( int id, int oid) {
        this.uqid = UserInfo.getInstance().getUserId();
        this.id = id;
        this.oid = oid;
    }


    public SendCommentModel(String com_id) {//评论点赞
        this.uqid=UserInfo.getInstance().getUserId();
        this.com_id = com_id;
    }

    public SendCommentModel(String aid, int type){//点赞
        this.uqid= UserInfo.getInstance().getUserId();
        this.aid=aid;
        this.type=type;
    }

    public SendCommentModel(String uqid, String fid, String content) {//二级评论
        this.uqid = uqid;
        this.content = content;
        this.fid = fid;
    }

    public SendCommentModel(String uqid, String aid, String bid, int type, String content) {//一级评论
        this.uqid = uqid;
        this.aid = aid;
        this.bid = bid;
        this.type = type;
        this.content = content;
    }
}
