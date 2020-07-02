package com.ay.lxunhan.bean;

import com.ay.lxunhan.utils.UserInfo;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/6/30
 */
public class ChatImBean {


    /**
     * message_list : [{"avatar":"","body":"好了吧[嘘]","convType":"PERSON","created_at":"2020-06-30 18:08:42","eventType":"1","fromAccount":"cz3egbdtwm2rdjdihvgyfbvvfrxjiheh","fromClientType":"AOS","id":6,"is_del":0,"is_media":"","msgTimestamp":"1593511615275","msgType":"TEXT","nickname":"","read_status":0,"sex":"","toAccount":"dj02gpjtuu3vhqalfv1tjm0zxg8ca8cc"}]
     * user_autograph : 666666
     * user_avatar : http://hlx.51appdevelop.com/storage/default_avatar/vkq3cBwKvtagMx9rnIHt7DeoXrDYqcxzxWXT4KtW.png
     * user_nickname : 大佬
     * user_sex : 1
     */

    private String user_autograph;
    private String user_avatar;
    private String user_nickname;
    private int user_sex;
    private List<MessageListBean> message_list;

    public String getUser_autograph() {
        return user_autograph;
    }

    public void setUser_autograph(String user_autograph) {
        this.user_autograph = user_autograph;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public boolean getUser_sex() {
        return user_sex==1;
    }

    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }

    public List<MessageListBean> getMessage_list() {
        return message_list;
    }

    public void setMessage_list(List<MessageListBean> message_list) {
        this.message_list = message_list;
    }

    public static class MessageListBean implements MultiItemEntity {
        /**
         * avatar :
         * body : 好了吧[嘘]
         * convType : PERSON
         * created_at : 2020-06-30 18:08:42
         * eventType : 1
         * fromAccount : cz3egbdtwm2rdjdihvgyfbvvfrxjiheh
         * fromClientType : AOS
         * id : 6
         * is_del : 0
         * is_media :
         * msgTimestamp : 1593511615275
         * msgType : TEXT
         * nickname :
         * read_status : 0
         * sex :
         * toAccount : dj02gpjtuu3vhqalfv1tjm0zxg8ca8cc
         */

        private String avatar;
        private String body;
        private String convType;
        private String created_at;
        private String eventType;
        private String fromAccount;
        private String fromClientType;
        private int id;
        private int is_del;
        private String is_media;
        private String msgTimestamp;
        private String msgType;
        private String nickname;
        private int read_status;
        private String sex;
        private String toAccount;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getConvType() {
            return convType;
        }

        public void setConvType(String convType) {
            this.convType = convType;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public String getFromAccount() {
            return fromAccount;
        }

        public void setFromAccount(String fromAccount) {
            this.fromAccount = fromAccount;
        }

        public String getFromClientType() {
            return fromClientType;
        }

        public void setFromClientType(String fromClientType) {
            this.fromClientType = fromClientType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public String getIs_media() {
            return is_media;
        }

        public void setIs_media(String is_media) {
            this.is_media = is_media;
        }

        public String getMsgTimestamp() {
            return msgTimestamp;
        }

        public void setMsgTimestamp(String msgTimestamp) {
            this.msgTimestamp = msgTimestamp;
        }

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getRead_status() {
            return read_status;
        }

        public void setRead_status(int read_status) {
            this.read_status = read_status;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getToAccount() {
            return toAccount;
        }

        public void setToAccount(String toAccount) {
            this.toAccount = toAccount;
        }

        @Override
        public int getItemType() {
            if (fromAccount.equals(UserInfo.getInstance().getUserId().toLowerCase())){
                return 1;
            }else {
                return 0;
            }

        }
    }
}
