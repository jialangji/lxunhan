package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.MultiItemBaseBean;
import com.ay.lxunhan.bean.TypeBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.HomeContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

public class HomePresenter extends BasePresenter<HomeContract.HomeView> implements HomeContract.HomePresenter {

    public HomePresenter(HomeContract.HomeView view) {
        super(view);
    }

    @Override
    public void getHomeList(int type, String title, int page) {
        addDisposable(HttpMethods.getInstance().getHomeList(type,title,page).subscribeWith(new BaseSubscriber<List<MultiItemBaseBean>>(){
            @Override
            public void onNext(List<MultiItemBaseBean> o) {
                super.onNext(o);
                getView().getHomeListFinish(o);
            }

        }));
    }

    @Override
    public void getHomeListAsk(int page) {
        addDisposable(HttpMethods.getInstance().getHomeListAsk(page).subscribeWith(new BaseSubscriber<List<MultiItemBaseBean>>(){
            @Override
            public void onNext(List<MultiItemBaseBean> o) {
                super.onNext(o);
                getView().getHomeListFinish(o);
            }
        }));
    }

    @Override
    public void getHomeListQuiz(int page) {
        addDisposable(HttpMethods.getInstance().getHomeListQuiz(page).subscribeWith(new BaseSubscriber<List<MultiItemBaseBean>>(){
            @Override
            public void onNext(List<MultiItemBaseBean> o) {
                super.onNext(o);
                getView().getHomeListFinish(o);
            }
        }));
    }

    @Override
    public void getHomeListArticle(int page, String id) {
        addDisposable(HttpMethods.getInstance().getHomeListArticle(id,page).subscribeWith(new BaseSubscriber<List<MultiItemBaseBean>>(){
            @Override
            public void onNext(List<MultiItemBaseBean> o) {
                super.onNext(o);
                getView().getHomeListFinish(o);
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
    public void getHomeType() {
       addDisposable(HttpMethods.getInstance().getHomeType().subscribeWith(new BaseSubscriber<List<TypeBean>>(){
           @Override
           public void onNext(List<TypeBean> o) {
               super.onNext(o);
               getView().getHomeTypeFinish(o);
           }

           @Override
           public void onError(Throwable t) {
               super.onError(t);
           }
       }));
    }
}
