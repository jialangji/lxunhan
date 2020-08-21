package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.contract.SearchResultContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/8/14
 */
public class SearchResultPresenter extends BasePresenter<SearchResultContract.SearchResultView> implements SearchResultContract.SearchResultPresenter {
    public SearchResultPresenter(SearchResultContract.SearchResultView view) {
        super(view);
    }

    @Override
    public void getSearch(int page, int type, String title) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().search(page, type, title).subscribeWith(new BaseSubscriber<List<UserMediaListBean>>(){
            @Override
            public void onNext(List<UserMediaListBean> o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getSearchFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
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
}
