package com.ay.lxunhan.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class CountryBean implements IPickerViewData {

    /**
     * region_id : 2
     * region_name : 北京
     * list : [{"region_id":52,"region_name":"北京"}]
     */

    private int region_id;
    private String region_name;
    private List<ListBean> list;

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    @Override
    public String getPickerViewText() {
        return this.region_name;
    }

    public static class ListBean implements IPickerViewData{
        /**
         * region_id : 52
         * region_name : 北京
         */

        private int region_id;
        private String region_name;

        public int getRegion_id() {
            return region_id;
        }

        public void setRegion_id(int region_id) {
            this.region_id = region_id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }

        @Override
        public String getPickerViewText() {
            return this.region_name;
        }
    }
}
