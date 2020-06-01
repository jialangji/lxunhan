package com.ay.lxunhan.bean;

public class TypeBean {

    /**
     * id : 1
     * name : 关注
     * icon : http://www.hlx.com/storage/home/k35mh5rf5Gjsqy6Q3AJgLUikJavjXzZ8UgTydfV8.png
     * background : home/moAFZFmgWoJbwqnBpdfyh5WXB4i7P9V0fPyji48q.png
     * desc : 推荐你得最爱
     */

    private int id;
    private String name;
    private String icon;
    private String background;
    private String desc;
    private boolean isSelect;

    public TypeBean(int id,String name, String icon, String background, String desc) {
        this.id=id;
        this.name = name;
        this.icon = icon;
        this.background = background;
        this.desc = desc;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
