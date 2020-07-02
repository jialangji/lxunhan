package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.AddFriendBean;
import com.ay.lxunhan.bean.FriendBean;
import com.ay.lxunhan.bean.MultiItemBaseBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.SearchContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/7/1
 */
public class SearchPresenter extends BasePresenter<SearchContract.SearchView> implements SearchContract.SearchPresenter {
    public SearchPresenter(SearchContract.SearchView view) {
        super(view);
    }

    @Override
    public void getHomeSearch(String title, int page) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().getHomeList(2,title,page).subscribeWith(new BaseSubscriber<List<MultiItemBaseBean>>(){
            @Override
            public void onNext(List<MultiItemBaseBean> o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getHomeSearchFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));

    }

    @Override
    public void getVideoSearch(String title, int page) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().videoHome(title, page).subscribeWith(new BaseSubscriber<List<VideoBean>>(){
            @Override
            public void onNext(List<VideoBean> o) {
                super.onNext(o);
                getView().getVideoHomeListFinish(o);
            }
            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void getFriendSearch(String content) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().friendList(content).subscribeWith(new BaseSubscriber<List<FriendBean>>(){
            @Override
            public void onNext(List<FriendBean> o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getFriendsFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void getAddFriendSearch(String keys, int page) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().addFriendBean(keys, page).subscribeWith(new BaseSubscriber<AddFriendBean>(){
            @Override
            public void onNext(AddFriendBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getAddFriendFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
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
}
