package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.LiveListBean;
import com.ay.lxunhan.contract.LiveListContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/7/3
 */
public class LiveListPresenter extends BasePresenter<LiveListContract.LiveListView> implements LiveListContract.LiveListPresenter {
    public LiveListPresenter(LiveListContract.LiveListView view) {
        super(view);
    }

    @Override
    public void getLiveList(String type, int page) {

        addDisposable(HttpMethods.getInstance().getLiveList(type, page,"").subscribeWith(new BaseSubscriber<List<LiveListBean>>(){
            @Override
            public void onNext(List<LiveListBean> o) {
                super.onNext(o);
                getView().getLiveListFinish(o);
            }
        }));

    }
}
