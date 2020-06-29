package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.contract.SmallVideoListContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/2
 */
public class SmallVideoListPresenter extends BasePresenter<SmallVideoListContract.SmallVideoListView> implements SmallVideoListContract.SmallVideoListPrenster {
    public SmallVideoListPresenter(SmallVideoListContract.SmallVideoListView view) {
        super(view);
    }

    @Override
    public void getSmallVideo(int page, int id) {
        addDisposable(HttpMethods.getInstance().smallVideoList(page,id).subscribeWith(new BaseSubscriber<List<VideoBean>>(){
            @Override
            public void onNext(List<VideoBean> o) {
                super.onNext(o);
                getView().getSmallVideoFinish(o);
            }
        }));
    }

    @Override
    public void getSmallWatch(int id) {
        addDisposable(HttpMethods.getInstance().smallVideoWatch(id).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().getSmallWatchFinish();
            }
        }));

    }
}
