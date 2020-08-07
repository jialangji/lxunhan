package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.VideoDetailBean;
import com.ay.lxunhan.bean.model.SendCommentModel;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/2
 */
public interface VideoDetailContract {
    interface VideoDetailPresenter {
        void getVideoDetail(String id);

        void getOneComment(String id, int type, int page);

        void sendOneComment(SendCommentModel model);

        void addLike(SendCommentModel model);

        void commentLike(SendCommentModel model);

        void attention(String uid);
        void share(int type, String aid);

        void addCollect(String aid,int type);
    }

    interface VideoDetailView {
        void getVideoDetailFinish(VideoDetailBean videoDetailBean);

        void getOneCommentFinsh(List<CommentBean> commentBeans);

        void sendCommentFinish();

        void addLikeFinish();

        void addCollect();

        void commentLikeFinish();

        void attentionFinish();

        void showProgress();

        void hideProgress();
    }
}
