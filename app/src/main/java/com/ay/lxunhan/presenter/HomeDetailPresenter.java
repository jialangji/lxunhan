package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.HomeDetailBean;
import com.ay.lxunhan.bean.HomeQuizDetailBean;
import com.ay.lxunhan.bean.model.AcceptModel;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.HomeDetailContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

public class HomeDetailPresenter extends BasePresenter<HomeDetailContract.HomeDetailView> implements HomeDetailContract.HomeDetailPresenter {
    public HomeDetailPresenter(HomeDetailContract.HomeDetailView view) {
        super(view);
    }

    @Override
    public void getHomeDetail(int type, int id) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().getHomeDetail(type, id).subscribeWith(new BaseSubscriber<HomeDetailBean>(){
            @Override
            public void onNext(HomeDetailBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getHomeDetailFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void getHomeQuizDetail(int id) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().getHomeQuizDetail(id).subscribeWith(new BaseSubscriber<HomeQuizDetailBean>(){
            @Override
            public void onNext(HomeQuizDetailBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getHomeDetailQuizFinish(o);
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
        addDisposable(HttpMethods.getInstance().getOneComment(id, type, page).subscribeWith(new BaseSubscriber<List<CommentBean>>(){
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
        addDisposable(HttpMethods.getInstance().sendOneComment(model).subscribeWith(new BaseSubscriber<Object>(){
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
        addDisposable(HttpMethods.getInstance().addLike(model).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().addLikeFinish();
            }

        }));
    }

    @Override
    public void commentLike(SendCommentModel model) {
        addDisposable(HttpMethods.getInstance().commentLike(model).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().commentLikeFinish();
            }
        }));
    }

    @Override
    public void quiz(SendCommentModel model) {
        addDisposable(HttpMethods.getInstance().quiz(model).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().quziFinish();
            }
        }));
    }

    @Override
    public void accept(AcceptModel model) {
        addDisposable(HttpMethods.getInstance().accept(model).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().acceptFinish();
            }
        }));
    }

    @Override
    public void attention(String uid) {
        addDisposable(HttpMethods.getInstance().attention(uid).subscribeWith(new BaseSubscriber<Object>(){
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
                getView().addCollectFinish();
            }
        }));
    }
}
