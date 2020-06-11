package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.AttentionBean;
import com.ay.lxunhan.contract.AttentionContract;
import com.ay.lxunhan.contract.FansContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/10
 */
public class AttentionPresenter extends BasePresenter<AttentionContract.AttentionView>implements AttentionContract.AttentionPresenter {



    public AttentionPresenter(AttentionContract.AttentionView view) {
        super(view);
    }

    @Override
    public void getAttention(int page) {
        addDisposable(HttpMethods.getInstance().attentionList(page).subscribeWith(new BaseSubscriber<List<AttentionBean>>(){
            @Override
            public void onNext(List<AttentionBean> o) {
                super.onNext(o);
                getView().getAttentionFinish(o);
            }
        }));
    }

    @Override
    public void getMediaAttention(String uzid, int page) {
        addDisposable(HttpMethods.getInstance().mediaAttentionList(uzid,page).subscribeWith(new BaseSubscriber<List<AttentionBean>>(){
            @Override
            public void onNext(List<AttentionBean> o) {
                super.onNext(o);
                getView().getAttentionFinish(o);
            }
        }));
    }

    @Override
    public void attention(String uzid) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().attention(uzid).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().hideProgress();
                getView().attention();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));

    }
}
