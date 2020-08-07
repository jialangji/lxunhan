package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.PeopleBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.bean.model.SendCommentModel;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/5/29
 */
public interface VideoHomeContract {

    interface VideoHomePresenter{
        void getVideoHomeList(String title,int page);
        void videoRecords();
        void attention(String uid);
        void  share(int type,String aid);
        void addCollect(String aid,int type);
        void  addLike(SendCommentModel model);
    }
    interface VideoHomeView{
        void getVideoHomeListFinish(List<VideoBean> list);
        void videoRecords(List<PeopleBean> peopleBeans);
        void addCollectFinish();
        void attentionFinish();
        void addLikeFinish();
    }
}
