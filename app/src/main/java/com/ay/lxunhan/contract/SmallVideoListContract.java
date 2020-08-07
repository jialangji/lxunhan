package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.bean.model.SendCommentModel;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/2
 */
public interface SmallVideoListContract {
    interface SmallVideoListPrenster {
        void getSmallVideo(int page, int id);

        void getSmallWatch(int id);

        void share(int type, String aid);

        void getOneComment(String id, int type, int page);

        void sendOneComment(SendCommentModel model);

        void addLike(SendCommentModel model);

        void commentLike(SendCommentModel model);

        void attention(String uid);

        void addCollect(String aid, int type);
    }

    interface SmallVideoListView {
        void getSmallWatchFinish();

        void getSmallVideoFinish(List<VideoBean> list);

        void getOneCommentFinsh(List<CommentBean> commentBeans);

        void addCollectFinish();

        void sendCommentFinish();

        void addLikeFinish();

        void commentLikeFinish();

        void attentionFinish();

        void showProgress();

        void hideProgress();
    }
}
