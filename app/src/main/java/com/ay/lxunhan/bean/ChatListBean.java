package com.ay.lxunhan.bean;

public class ChatListBean {

    /**
     * newMessage : 3
     * toAccount : xr2o2APUV3vSeE53gwEl5imuWosqKtpv
     * body : 或许吧
     * created_at : 2020-06-04 10:41:05
     * msgType : 1
     * nickname : 卡卡
     * avatar : http://www.hlx.com/storage/avatar/RPIudn6sGy8J4JhsWIMRzqkm8OpceFKa82Sr3HSR.jpeg
     * timeText :
     */

    private int newMessage;
    private String toAccount;
    private String body;
    private String created_at;
    private String msgType;
    private String nickname;
    private String avatar;
    private String timeText;
    private int is_media;
    private String uqid;
    private int sex;

    public String getUqid() {
        return uqid;
    }

    public void setUqid(String uqid) {
        this.uqid = uqid;
    }

    public boolean getSex() {
        return sex==1;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public boolean getIs_media() {
        return is_media==1;
    }

    public void setIs_media(int is_media) {
        this.is_media = is_media;
    }

    public int getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(int newMessage) {
        this.newMessage = newMessage;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }
}
