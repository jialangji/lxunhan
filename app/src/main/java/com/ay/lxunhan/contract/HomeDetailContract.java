package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.HomeDetailBean;
import com.ay.lxunhan.bean.HomeQuizDetailBean;
import com.ay.lxunhan.bean.model.AcceptModel;
import com.ay.lxunhan.bean.model.SendCommentModel;

import java.util.List;

public interface HomeDetailContract {
    interface HomeDetailPresenter{
        void  getHomeDetail(int type,int id);
        void  getHomeQuizDetail(int id);
        void  getOneComment(String id,int type, int page);
        void  sendOneComment(SendCommentModel model);
        void  addLike(SendCommentModel model);
        void  commentLike(SendCommentModel model);
        void  quiz(SendCommentModel model);
        void  accept(AcceptModel model);
        void  attention(String uid);
    }

    interface HomeDetailView{
        void getHomeDetailFinish(HomeDetailBean homeDetailBean);
        void getHomeDetailQuizFinish(HomeQuizDetailBean homeQuizDetailBean);
        void getOneCommentFinsh(List<CommentBean> commentBeans);
        void sendCommentFinish();
        void addLikeFinish();
        void commentLikeFinish();
        void quziFinish();
        void acceptFinish();
        void attentionFinish();
        void showProgress();
        void hideProgress();
    }
}
