package com.ay.lxunhan.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/8
 */
public class UserMediaListBean implements MultiItemEntity {


    /**
     * id : 2
     * uid : 1
     * nickname : 广电官方账号
     * avatar : media_avatar/A8deylM3715sJHUlxOH9ETANqruUSy8S2F5Av0uv.jpeg
     * into : 没啥能说的。
     * sex : 1
     * title : 你的钱包有多厚？
     * cover : []
     * plate_name : 2
     * type : 4
     * bounty_gold : 0
     * is_solve : 0
     * created_at : 2020-05-18 16:10:40
     * is_like : 0
     * is_fol : 0
     * comment_count : 0
     * like_count : 1
     * timeText : 2020-05-18
     * desc : 说说你的钱包 多厚
     * option_list : [{"id":7,"content":"0cm","is_selected":"0","bfb":0,"count":0},{"id":8,"content":"5cm","is_selected":"0","bfb":0,"count":0},{"id":9,"content":"10cm","is_selected":"0","bfb":0,"count":0}]
     * is_pate : 0
     *  :
     * video : article/QmaFA8yOIXr8owNLqJ7itWkJlPm4C6GbPIc2Uf3N.mp4
     */

    private String searchContent;
    private int id;
    private int cover_type;
    private String uid;
    private String nickname;
    private String avatar;
    private String into;
    private int sex;
    private String title;
    private String plate_name;
    private int type;
    private String bounty_gold;
    private int is_solve;
    private String created_at;
    private int is_like;
    private int is_fol;
    private int comment_count;
    private int like_count;
    private String timeText;
    private String desc;
    private int is_pate;
    private String video;
    private List<String> cover;
    private List<OptionListBean> option_list;

    private String media_nickname;
    private String media_avatar;
    private String fan;

    public String getMedia_nickname() {
        return media_nickname;
    }

    public void setMedia_nickname(String media_nickname) {
        this.media_nickname = media_nickname;
    }

    public String getMedia_avatar() {
        return media_avatar;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public void setMedia_avatar(String media_avatar) {
        this.media_avatar = media_avatar;
    }

    public String getFan() {
        return fan;
    }

    public void setFan(String fan) {
        this.fan = fan;
    }

    public int getCover_type() {
        return cover_type;
    }

    public void setCover_type(int cover_type) {
        this.cover_type = cover_type;
    }

    @Override
    public int getItemType() {
        if (type==1){
            return cover_type;
        }else {
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

    public boolean getSex() {
        return sex==1;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBounty_gold() {
        return bounty_gold;
    }

    public void setBounty_gold(String bounty_gold) {
        this.bounty_gold = bounty_gold;
    }

    public boolean getIs_solve() {
        return is_solve==1;
    }

    public void setIs_solve(int is_solve) {
        this.is_solve = is_solve;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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
         * id : 7
         * content : 0cm
         * is_selected : 0
         * bfb : 0
         * count : 0
         */

        private int id;
        private String content;
        private int is_selected;
        private int bfb;
        private int count;
        private boolean userIsSelect;

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

        public String getCount() {
            return String.valueOf(count);
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
