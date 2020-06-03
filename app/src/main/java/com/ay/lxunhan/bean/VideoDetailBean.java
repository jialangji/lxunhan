package com.ay.lxunhan.bean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/2
 */
public class VideoDetailBean {

    /**
     * id : 32
     * type : 2
     * uid : 1
     * plate_id : 10
     * title : 如何延长寿命？如何延长寿命？如何延长寿命？如何延长寿命？如何延长寿命？如何延长寿命？如何延长寿命？如何延长寿命？如何延长寿命？如何延长寿命？
     * video : http://www.hlx.com/storage/article/DSQvn8fwghRmW0BRRNOeWPtBCn2EtYAuk8ajzbOw.mp4
     * content : 这个关于养生的视频
     * created_at : 2020-05-11 17:07:32
     * plate_name : 美食
     * timeText : 2020-05-11
     * like_count : 1
     * is_fol : 1
     * is_like : 1
     * avatar : media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg
     * nickname : 广电官方账号
     * into : 没啥能说的。
     * sex : 1
     */

    private int id;
    private int type;
    private String uid;
    private int plate_id;
    private String title;
    private String video;
    private String content;
    private String created_at;
    private String plate_name;
    private String timeText;
    private int like_count;
    private int is_fol;
    private int is_like;
    private String avatar;
    private String nickname;
    private String into;
    private int sex;
    private String cover;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPlate_id() {
        return plate_id;
    }

    public void setPlate_id(int plate_id) {
        this.plate_id = plate_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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

    public String getPlate_name() {
        return plate_name;
    }

    public void setPlate_name(String plate_name) {
        this.plate_name = plate_name;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getIs_fol() {
        return is_fol;
    }

    public void setIs_fol(int is_fol) {
        this.is_fol = is_fol;
    }

    public boolean getIs_like() {
        return is_like==1;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
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

    public String getInto() {
        return into;
    }

    public void setInto(String into) {
        this.into = into;
    }

    public boolean getSex() {
        return sex==1;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
