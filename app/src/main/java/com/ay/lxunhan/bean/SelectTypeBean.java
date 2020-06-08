package com.ay.lxunhan.bean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/8
 */
public class SelectTypeBean {
    private String name;
    private int type;
    private boolean isSelect;

    public SelectTypeBean(String name, int type) {
        this.name=name;
        this.type=type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
