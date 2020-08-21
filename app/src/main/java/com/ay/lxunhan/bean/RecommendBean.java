package com.ay.lxunhan.bean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/8/20
 */
public class RecommendBean {


    /**
     * id : 33
     * type : 2
     * title : 哈哈哈哈哈哈
     * cover_arr : ["http://www.hlx.com/storage/article/WR88lbdGSEw7QcRdid61xdYWcBDLPmlLY3O0Iz6G.jpeg"]
     */

    private int id;
    private int type;
    private String title;
    private List<String> cover_arr;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCover_arr() {
        return cover_arr;
    }

    public void setCover_arr(List<String> cover_arr) {
        this.cover_arr = cover_arr;
    }
}
