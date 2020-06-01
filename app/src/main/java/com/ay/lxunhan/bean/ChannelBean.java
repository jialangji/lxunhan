package com.ay.lxunhan.bean;

import java.util.List;

public class ChannelBean {

    private List<MyChanneBean> my_channe;
    private List<MyChanneBean> no_channe;

    public List<MyChanneBean> getMy_channe() {
        return my_channe;
    }

    public void setMy_channe(List<MyChanneBean> my_channe) {
        this.my_channe = my_channe;
    }

    public List<MyChanneBean> getNo_channe() {
        return no_channe;
    }

    public void setNo_channe(List<MyChanneBean> no_channe) {
        this.no_channe = no_channe;
    }

    public static class MyChanneBean {
        /**
         * id : 1
         * name : 关注
         * icon : http://www.hlx.com/storage/home/k35mh5rf5Gjsqy6Q3AJgLUikJavjXzZ8UgTydfV8.png
         */

        private int id;
        private String name;
        private String icon;
        private boolean isSelect;
        private boolean isShowIcon;

        public boolean isShowIcon() {
            return isShowIcon;
        }

        public void setShowIcon(boolean showIcon) {
            isShowIcon = showIcon;
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
    }
}
