package com.ay.lxunhan.bean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/11
 */
public class PyqCommentBean {

    /**
     * id : 5
     * aid : 1
     * cid : 1
     * bid : 2
     * fid : 0
     * content : 我评论你的说说了
     * created_at : 2020-05-08 10:25:35
     * timeText : 2020-05-08
     * c_nickname : 卡卡
     * b_nickname : 无噢诶
     */

    private int id;
    private int aid;
    private int cid;
    private int bid;
    private int fid;
    private String avatar;
    private String content;
    private String created_at;
    private String timeText;
    private String c_nickname;
    private String b_nickname;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public String getC_nickname() {
        return c_nickname;
    }

    public void setC_nickname(String c_nickname) {
        this.c_nickname = c_nickname;
    }

    public String getB_nickname() {
        return b_nickname;
    }

    public void setB_nickname(String b_nickname) {
        this.b_nickname = b_nickname;
    }
}
