package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.FriendBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/2
 */
public interface FriendContract {
    interface FriendPresenter{
        void getFriendsList(String keys);
    }
    interface FriendsView{
        void getFriendsFinish(List<FriendBean> friendBeans);
        void showProgress();
        void hideProgress();
    }
}
