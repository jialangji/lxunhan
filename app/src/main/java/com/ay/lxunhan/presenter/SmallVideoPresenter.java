package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.contract.SmallVideoContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/5/29
 */
public class SmallVideoPresenter extends BasePresenter<SmallVideoContract.SmallVideoView> implements SmallVideoContract.SmallVideoPresenter {
    public SmallVideoPresenter(SmallVideoContract.SmallVideoView view) {
        super(view);
    }

    @Override
    public void getSmallVideo(int page,int id) {
        addDisposable(HttpMethods.getInstance().smallVideoList(page,id).subscribeWith(new BaseSubscriber<List<VideoBean>>(){
            @Override
            public void onNext(List<VideoBean> o) {
                super.onNext(o);
                getView().getSmallVideoFinish(o);
            }
        }));
    }
}
