package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.ChatImBean;
import com.ay.lxunhan.contract.ChatImContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/30
 */
public class ChatImPresenter extends BasePresenter<ChatImContract.ChatImView> implements ChatImContract.ChatImPresenter {
    public ChatImPresenter(ChatImContract.ChatImView view) {
        super(view);
    }

    @Override
    public void getChatImShow(String userId,int page) {
        addDisposable(HttpMethods.getInstance().chatIm(userId,page).subscribeWith(new BaseSubscriber<ChatImBean>(){
            @Override
            public void onNext(ChatImBean o) {
                super.onNext(o);
                getView().getChatImShowFinish(o);
            }
        }));
    }
}
