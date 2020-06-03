package com.ay.lxunhan.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class MultiItemBaseBean implements MultiItemEntity {


    private int type;
    /**
     * id : 1
     * uid : 1
     * nickname : 广电官方账号
     * avatar : http://www.hlx.com/storage/media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg
     * into : 没啥能说的。
     * title : 这个App你觉得好用吗？
     * cover : []
     * plate_name : 1
     * created_at : 2020-05-13 14:31:16
     * sex : 1
     * cover_type : 0
     * comment_count : 1
     * like_count : 0
     * is_like : 0
     * is_fol : 1
     * timeText : 2020-05-13
     * option_list : [{"id":1,"content":"好用","is_selected":"0","bfb":"0"},{"id":2,"content":"一般","is_selected":"0","bfb":"0"},{"id":3,"content":"很差","is_selected":"0","bfb":"0"}]
     * desc :
     * is_pate :
     */

    private String bounty_gold;
    private int id;
    private String uid;
    private String nickname;
    private String avatar;
    private String into;
    private String title;
    private String plate_name;
    private String created_at;
    private int sex;
    private int cover_type;
    private int comment_count;
    private int like_count;
    private int is_like;
    private int is_fol;
    private String timeText;
    private String desc;
    private int is_pate;
    private List<String> cover;
    private List<OptionListBean> option_list;
    private int is_solve;

    public boolean getIs_solve() {
        return is_solve==1;
    }

    public void setIs_solve(int is_solve) {
        this.is_solve = is_solve;
    }

    public String getBounty_gold() {
        return bounty_gold;
    }

    public void setBounty_gold(String bounty_gold) {
        this.bounty_gold = bounty_gold;
    }

    public MultiItemBaseBean(int i) {
        this.type=i;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        if (type==1){
            return cover_type;
        }else{
            return type;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getInto() {
        return into;
    }

    public void setInto(String into) {
        this.into = into;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlate_name() {
        return plate_name;
    }

    public void setPlate_name(String plate_name) {
        this.plate_name = plate_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean getSex() {
        return sex==1;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getCover_type() {
        return cover_type;
    }

    public void setCover_type(int cover_type) {
        this.cover_type = cover_type;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public boolean getIs_like() {
        return is_like==1;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }

    public int getIs_fol() {
        return is_fol;
    }

    public void setIs_fol(int is_fol) {
        this.is_fol = is_fol;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean getIs_pate() {
        return is_pate==1;
    }

    public void setIs_pate(int is_pate) {
        this.is_pate = is_pate;
    }

    public List<String> getCover() {
        return cover;
    }

    public void setCover(List<String> cover) {
        this.cover = cover;
    }

    public List<OptionListBean> getOption_list() {
        return option_list;
    }

    public void setOption_list(List<OptionListBean> option_list) {
        this.option_list = option_list;
    }

    public static class OptionListBean {
        /**
         * id : 1
         * content : 好用
         * is_selected : 0
         * bfb : 0
         */

        private int id;
        private String content;
        private int is_selected;
        private int bfb;
        private String count;
        private boolean userIsSelect;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public boolean isUserIsSelect() {
            return userIsSelect;
        }

        public void setUserIsSelect(boolean userIsSelect) {
            this.userIsSelect = userIsSelect;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean getIs_selected() {
            return is_selected==1;
        }

        public void setIs_selected(int is_selected) {
            this.is_selected = is_selected;
        }

        public int getBfb() {
            return bfb;
        }

        public void setBfb(int bfb) {
            this.bfb = bfb;
        }
    }
}
