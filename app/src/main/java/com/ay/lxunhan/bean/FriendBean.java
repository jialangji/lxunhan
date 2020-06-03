package com.ay.lxunhan.bean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/2
 */
public class FriendBean {

    /**
     * uid : 1
     * sex : 1
     * nickname : 广电官方账号
     * avatar : http://www.hlx.com/storage/media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg
     * is_media : 1
     */

    private String uid;
    private int sex;
    private String nickname;
    private String avatar;
    private int is_media;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean getSex() {
        return sex==1;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public boolean getIs_media() {
        return is_media==1;
    }

    public void setIs_media(int is_media) {
        this.is_media = is_media;
    }
}
