package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.TypeBean;
import com.ay.lxunhan.contract.VideoMainContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/5/29
 */
public class VideoMainPresenter extends BasePresenter<VideoMainContract.VideoMainView> implements VideoMainContract.VideoMainPresenter {
    public VideoMainPresenter(VideoMainContract.VideoMainView view) {
        super(view);
    }

    @Override
    public void getVideoType() {

        addDisposable(HttpMethods.getInstance().getVideoType().subscribeWith(new BaseSubscriber<List<TypeBean>>(){
            @Override
            public void onNext(List<TypeBean> o) {
                super.onNext(o);
                getView().getVideoTypeFinish(o);
            }
        }));

    }
}
