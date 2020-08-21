package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.COrHContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/12
 */
public class CorHpRresenter extends BasePresenter<COrHContract.CorHView> implements COrHContract.COrHPresenter {
    public CorHpRresenter(COrHContract.CorHView view) {
        super(view);
    }

    @Override
    public void collect(int status,int page) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().collect(status,page).subscribeWith(new BaseSubscriber<List<UserMediaListBean>>(){
            @Override
            public void onNext(List<UserMediaListBean> o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getListFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void history(int page) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().history(page).subscribeWith(new BaseSubscriber<List<UserMediaListBean>>(){
            @Override
            public void onNext(List<UserMediaListBean> o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getListFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void clearHistory() {
        addDisposable(HttpMethods.getInstance().clearHistory().subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().clearFinish();
            }
        }));
    }

    @Override
    public void deleteCollect(String aid, String type) {
        addDisposable(HttpMethods.getInstance().deleteCollect(aid, type).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().clearFinish();
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
}
