package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.PyqCommentBean;
import com.ay.lxunhan.bean.PyqDetailBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.PyqDetailContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/11
 */
public class PyqDetailPresenter extends BasePresenter<PyqDetailContract.PyqDetailView> implements PyqDetailContract.PyqDetailPresenter {


    public PyqDetailPresenter(PyqDetailContract.PyqDetailView view) {
        super(view);
    }

    @Override
    public void getPyqDetailShow(String id) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().pyqDetail(id).subscribeWith(new BaseSubscriber<PyqDetailBean>(){
            @Override
            public void onNext(PyqDetailBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getPyqDetailFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
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
    public void sendTwoComment(SendCommentModel model) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().sendTwoComment(model).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().hideProgress();
                getView().sendTwoCommentFinish();
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
    public void getComment(String id, int page) {
        addDisposable(HttpMethods.getInstance().pyqComment(id, page).subscribeWith(new BaseSubscriber<List<PyqCommentBean>>(){
            @Override
            public void onNext(List<PyqCommentBean> o) {
                super.onNext(o);
                getView().getCommentFinish(o);
            }
        }));
    }

}
