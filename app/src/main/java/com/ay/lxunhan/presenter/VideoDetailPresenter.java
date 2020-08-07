package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.VideoDetailBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.VideoDetailContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/2
 */
public class VideoDetailPresenter extends BasePresenter<VideoDetailContract.VideoDetailView> implements VideoDetailContract.VideoDetailPresenter {
    public VideoDetailPresenter(VideoDetailContract.VideoDetailView view) {
        super(view);
    }

    @Override
    public void getVideoDetail(String id) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().videoDetail(id).subscribeWith(new BaseSubscriber<VideoDetailBean>() {
            @Override
            public void onNext(VideoDetailBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getVideoDetailFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));

    }

    @Override
    public void getOneComment(String id, int type, int page) {
        addDisposable(HttpMethods.getInstance().getOneComment(id, type, page).subscribeWith(new BaseSubscriber<List<CommentBean>>() {
            @Override
            public void onNext(List<CommentBean> o) {
                super.onNext(o);

                getView().getOneCommentFinsh(o);
            }

        }));
    }

    @Override
    public void sendOneComment(SendCommentModel model) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().sendOneComment(model).subscribeWith(new BaseSubscriber<Object>() {
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().hideProgress();
                getView().sendCommentFinish();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void addLike(SendCommentModel model) {
        addDisposable(HttpMethods.getInstance().addLike(model).subscribeWith(new BaseSubscriber<Object>() {
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().addLikeFinish();
            }

        }));
    }

    @Override
    public void commentLike(SendCommentModel model) {
        addDisposable(HttpMethods.getInstance().commentLike(model).subscribeWith(new BaseSubscriber<Object>() {
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().commentLikeFinish();
            }
        }));
    }

    @Override
    public void attention(String uid) {
        addDisposable(HttpMethods.getInstance().attention(uid).subscribeWith(new BaseSubscriber<Object>() {
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().attentionFinish();
            }
        }));
    }

    @Override
    public void addCollect(String aid, int type) {
        addDisposable(HttpMethods.getInstance().addCollect(aid, type).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().addCollect();
            }
        }));
    }

    @Override
    public void share(int type, String aid) {
        addDisposable(HttpMethods.getInstance().share(type,aid).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
            }
        }));
    }
}
