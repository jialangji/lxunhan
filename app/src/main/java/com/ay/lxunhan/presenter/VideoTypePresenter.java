package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.contract.VideoTypeContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/5/29
 */
public class VideoTypePresenter extends BasePresenter<VideoTypeContract.VideoTypeView> implements VideoTypeContract.VideoTypePresenter {
    public VideoTypePresenter(VideoTypeContract.VideoTypeView view) {
        super(view);
    }

    @Override
    public void getVideoType(String plate_id, int page) {
        addDisposable(HttpMethods.getInstance().videoTypeList(plate_id, page).subscribeWith(new BaseSubscriber<List<VideoBean>>(){
            @Override
            public void onNext(List<VideoBean> o) {
                super.onNext(o);
                getView().getVideoTypeFinish(o);
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
