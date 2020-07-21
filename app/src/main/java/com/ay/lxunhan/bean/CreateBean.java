package com.ay.lxunhan.bean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/7/15
 */
public class CreateBean {

    /**
     * id : 1
     * uid : 1
     * ltype : 3
     * lname : XXXX
     * cover : http://www.hlx.com/storage/XXXX
     * lid : 111
     * lstate : 1
     * pushurl : 1
     * httpPullUrl : 1
     * hlsPullUrl : 1
     * rtmpPullUrl : 1
     * created_at : 2020-07-02 18:05:15
     */

    private int id;
    private int uid;
    private int ltype;
    private String lname;
    private String cover;
    private String lid;
    private int lstate;
    private String pushurl;
    private String httpPullUrl;
    private String hlsPullUrl;
    private String rtmpPullUrl;
    private String created_at;
    private String roomcode;

    public String getRoomcode() {
        return roomcode;
    }

    public void setRoomcode(String roomcode) {
        this.roomcode = roomcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getLtype() {
        return ltype;
    }

    public void setLtype(int ltype) {
        this.ltype = ltype;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public int getLstate() {
        return lstate;
    }

    public void setLstate(int lstate) {
        this.lstate = lstate;
    }

    public String getPushurl() {
        return pushurl;
    }

    public void setPushurl(String pushurl) {
        this.pushurl = pushurl;
    }

    public String getHttpPullUrl() {
        return httpPullUrl;
    }

    public void setHttpPullUrl(String httpPullUrl) {
        this.httpPullUrl = httpPullUrl;
    }

    public String getHlsPullUrl() {
        return hlsPullUrl;
    }

    public void setHlsPullUrl(String hlsPullUrl) {
        this.hlsPullUrl = hlsPullUrl;
    }

    public String getRtmpPullUrl() {
        return rtmpPullUrl;
    }

    public void setRtmpPullUrl(String rtmpPullUrl) {
        this.rtmpPullUrl = rtmpPullUrl;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
