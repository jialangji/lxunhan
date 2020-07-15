package com.ay.lxunhan.bean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/7/15
 */
public class LiveDetailBean  {
    /**
     * id : 1
     * uid : 1
     * ltypenm : 没事
     * ltype : 1
     * lname : XXXX
     * cover : http://www.hlx.com/storage/XXXX
     * lid : 111
     * lstate : 1
     */

    private int id;
    private int uid;
    private String ltypenm;
    private int ltype;
    private String lname;
    private String cover;
    private String lid;
    private int lstate;

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

    public String getLtypenm() {
        return ltypenm;
    }

    public void setLtypenm(String ltypenm) {
        this.ltypenm = ltypenm;
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
}
