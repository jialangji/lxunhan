package com.ay.lxunhan.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/3
 */
public class PyqBean implements MultiItemEntity {

    /**
     * id : 1
     * nickname : 无噢诶
     * avatar : http://www.hlx.com/storage/default_avatar/wlc9r2o1z1h3r0HYRzsffR9ChKT9hkewzSPRCJmr.png
     * autograph : 13234234
     * title : 希望半年后的自己也很幸福
     * created_at : 2020-06-02 16:43:35
     * like_count : 0
     * comment_count : 1
     * image_arr : ["http://www.hlx.com/storage/circle/UQ2zvyj1L5Mlg0a7a687pacB7H98zNwV1OENjp4H.jpeg","http://www.hlx.com/storage/circle/bWKC48MnFcf6HxJBCuKBqswiG1T61PLjmxsjjMqD.jpeg"]
     * is_like : 1
     * timeText :
     * sex : int
     */

    private int id;
    private String nickname;
    private String uid;
    private String avatar;
    private String autograph;
    private String title;
    private String created_at;
    private int like_count;
    private int comment_count;
    private int is_like;
    private String timeText;
    private int sex;
    private List<String> image_arr;
    private int type;
    private int is_mine;
    private int is_my;

    public boolean getIs_my() {
        return is_my==1;
    }

    public void setIs_my(int is_my) {
        this.is_my = is_my;
    }

    public boolean getIs_mine() {
        return is_mine==1;
    }

    public void setIs_mine(int is_mine) {
        this.is_mine = is_mine;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * is_cover_type : 1
     */



    private int is_cover_type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public boolean getIs_like() {
        return is_like==1;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public boolean getSex() {
        return sex==1;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public List<String> getImage_arr() {
        return image_arr;
    }

    public void setImage_arr(List<String> image_arr) {
        this.image_arr = image_arr;
    }

    @Override
    public int getItemType() {
        if (is_cover_type==0){
            return 0;
        }else if (is_cover_type==1){
            return 1;
        }else {
            return 2;
        }
    }

    public int getIs_cover_type() {
        return is_cover_type;
    }

    public void setIs_cover_type(int is_cover_type) {
        this.is_cover_type = is_cover_type;
    }
}
