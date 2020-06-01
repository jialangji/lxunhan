package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.TwoCommentBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.TwoCommentConytract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/5/28
 */
public class TwoCommentPresenter extends BasePresenter<TwoCommentConytract.TwoCommentView> implements TwoCommentConytract.TwoCommentPresenter {
    public TwoCommentPresenter(TwoCommentConytract.TwoCommentView view) {
        super(view);
    }

    @Override
    public void getTwoComment(String id,int page) {
       addDisposable(HttpMethods.getInstance().getTwoComment(id,page).subscribeWith(new BaseSubscriber<TwoCommentBean>(){
           @Override
           public void onNext(TwoCommentBean o) {
               super.onNext(o);
               getView().getTwoCommentFinish(o);
           }

           @Override
           public void onError(Throwable t) {
               super.onError(t);
           }
       }));
    }

    @Override
    public void commentLike(SendCommentModel model, boolean isTwo) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().commentLike(model).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().hideProgress();
                getView().addLikeFinish(isTwo);
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
        addDisposable(HttpMethods.getInstance().sendTwoComment(model).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().sendTwoCommentFinish();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        }));

    }
}
