package com.ay.lxunhan.bean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/12
 */
public class NotationSystemBean {

    /**
     * id : 1
     * content : 自媒体申请已通过
     * created_at : 2020-06-08 17:24:14
     * aid : 1
     * is_read : 0
     * timeText : 刚刚
     */

    private int id;
    private String content;
    private String created_at;
    private int aid;
    private int is_read;
    private String timeText;

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }
}
