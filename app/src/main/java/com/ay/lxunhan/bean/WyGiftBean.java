package com.ay.lxunhan.bean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/7/22
 */
public class WyGiftBean {

    /**
     * data : {"count":1,"present":{"count":1,"cover":"http://hlx.51appdevelop.com/storage/gifts/u7noo5ikRlGuVZFVHTfUs5yMRLJWkxytzQKpooYr.png","gold":1,"header":"http://thirdwx.qlogo.cn/mmopen/vi_32/MnmwpBeib0K5sf3bM5qpax3DL5TM6aYC9XjL8LD9eq6pXJ3gpNoceicq6oWdeXhlVN1ot9a3MDGaNbalJXicI8LTQ/132","id":3,"name":"小锤锤","select":true,"userName":"久离"}}
     * type : 5
     */

    private DataBean data;
    private int type;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class DataBean {
        /**
         * count : 1
         * present : {"count":1,"cover":"http://hlx.51appdevelop.com/storage/gifts/u7noo5ikRlGuVZFVHTfUs5yMRLJWkxytzQKpooYr.png","gold":1,"header":"http://thirdwx.qlogo.cn/mmopen/vi_32/MnmwpBeib0K5sf3bM5qpax3DL5TM6aYC9XjL8LD9eq6pXJ3gpNoceicq6oWdeXhlVN1ot9a3MDGaNbalJXicI8LTQ/132","id":3,"name":"小锤锤","select":true,"userName":"久离"}
         */

        private int count;
        private GiftBean present;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public GiftBean getPresent() {
            return present;
        }

        public void setPresent(GiftBean present) {
            this.present = present;
        }

    }
}
