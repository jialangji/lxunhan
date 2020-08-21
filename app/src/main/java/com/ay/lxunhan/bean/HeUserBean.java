package com.ay.lxunhan.bean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/8
 */
public class HeUserBean {

    /**
     * avatar : avatar/RPIudn6sGy8J4JhsWIMRzqkm8OpceFKa82Sr3HSR.jpeg
     * nickname : 卡卡
     * sex : 1
     * date_birth : 32
     * province : 贵州
     * city : 嘉峪关
     * is_media : 1
     * is_fol : 0
     * likeCount : 7
     * folCount : 1
     * beFolCount : 1
     *  :
     */
    private int isMine;

    private String avatar;
    private String nickname;
    private int sex;
    private String date_birth;
    private String province;
    private String city;
    private int is_media;
    private int is_fol;
    private int likeCount;
    private String age;
    private int folCount;
    private int beFolCount;
    private String uqid;

    public String getUqid() {
        return uqid;
    }

    public void setUqid(String uqid) {
        this.uqid = uqid;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    /**
     * signal : 171569501143
     * id : 1
     * media_into : 没啥能说的。
     */


    private String signal;
    private int id;
    private String media_into;


    public boolean getIsMine() {
        return isMine==1;
    }

    public void setIsMine(int isMine) {
        this.isMine = isMine;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean getSex() {
        return sex==1;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(String date_birth) {
        this.date_birth = date_birth;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean getIs_media() {
        return is_media==1;
    }

    public void setIs_media(int is_media) {
        this.is_media = is_media;
    }

    public int getIs_fol() {
        return is_fol;
    }

    public void setIs_fol(int is_fol) {
        this.is_fol = is_fol;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getFolCount() {
        return folCount;
    }

    public void setFolCount(int folCount) {
        this.folCount = folCount;
    }

    public int getBeFolCount() {
        return beFolCount;
    }

    public void setBeFolCount(int beFolCount) {
        this.beFolCount = beFolCount;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedia_into() {
        return media_into;
    }

    public void setMedia_into(String media_into) {
        this.media_into = media_into;
    }
}
