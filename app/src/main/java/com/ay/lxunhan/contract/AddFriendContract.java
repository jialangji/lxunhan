package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.AddFriendBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/3
 */
public interface AddFriendContract {
    interface AddFriendPresenter{
        void getAddFriend(String keys,int page);
        void attentions(List<String> data);
        void attention(String id);
    }
    interface AddFriendView{
        void getAddFriendFinish(AddFriendBean addFriendBean);
        void attentionsFinish();
        void attentionFinish();
        void showProgress();
        void hideProgress();
    }
}
