package com.ay.lxunhan.bean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/7/13
 */
public class GiftBean {


    /**
     * id : 2
     * name : 气球
     * cover : http://www.hanlx.com/storage/gifts/Vb7dQ4H5yZaexy3a71XRL7mSxKliTIHOLTbVEVYE.png
     * gold : 1
     * count : 3
     */

    private int id;
    private String name;
    private String cover;
    private int gold;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
