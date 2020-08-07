package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.AddFriendBean;
import com.ay.lxunhan.bean.FriendBean;
import com.ay.lxunhan.bean.LiveListBean;
import com.ay.lxunhan.bean.MultiItemBaseBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.bean.model.SendCommentModel;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/7/1
 */
public interface SearchContract {
    interface SearchPresenter{
        void getHomeSearch(String title, int page);
        void getVideoSearch(String title, int page);
        void getFriendSearch(String content);
        void getAddFriendSearch(String keys,int page);
        void getLiveSearch(String type, int page,String name);
        void addCollect(String aid,int type);
        void attention(String uid);
        void quiz(SendCommentModel model);
    }

    interface SearchView{
        void getHomeSearchFinish(List<MultiItemBaseBean> bean);
        void getVideoHomeListFinish(List<VideoBean> list);
        void getAddFriendFinish(AddFriendBean addFriendBean);
        void getFriendsFinish(List<FriendBean> friendBeans);
        void getLiveSearchFinish(List<LiveListBean> beans);
        void addCollectFinish();
        void attentionFinish();
        void quziFinish();
        void showProgress();
        void hideProgress();
    }
}
