package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.FriendBean;
import com.ay.lxunhan.contract.FriendContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/2
 */
public class FriendsPresenter extends BasePresenter<FriendContract.FriendsView> implements FriendContract.FriendPresenter {
    public FriendsPresenter(FriendContract.FriendsView view) {
        super(view);
    }

    @Override
    public void getFriendsList() {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().friendList().subscribeWith(new BaseSubscriber<List<FriendBean>>(){
            @Override
            public void onNext(List<FriendBean> o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getFriendsFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));

    }
}
