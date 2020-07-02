package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.AddFriendBean;
import com.ay.lxunhan.contract.AddFriendContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/3
 */
public class AddFriendPresneter extends BasePresenter<AddFriendContract.AddFriendView> implements AddFriendContract.AddFriendPresenter {
    public AddFriendPresneter(AddFriendContract.AddFriendView view) {
        super(view);
    }

    @Override
    public void getAddFriend(String keys, int page) {

        getView().showProgress();
        addDisposable(HttpMethods.getInstance().addFriendBean(keys, page).subscribeWith(new BaseSubscriber<AddFriendBean>(){
            @Override
            public void onNext(AddFriendBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getAddFriendFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void attentions(List<String> data) {
        addDisposable(HttpMethods.getInstance().attentions(data).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().attentionsFinish();
            }
        }));
    }

    @Override
    public void attention(String id) {
        addDisposable(HttpMethods.getInstance().attention(id).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().attentionFinish();
            }
        }));
    }
}
