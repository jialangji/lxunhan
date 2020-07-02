package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.ChatListBean;
import com.ay.lxunhan.contract.MessageContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/4
 */
public class MessagePresenter extends BasePresenter<MessageContract.MessageView> implements MessageContract.MessagePresenter {
    public MessagePresenter(MessageContract.MessageView view) {
        super(view);
    }

    @Override
    public void getChatList(int page) {
        addDisposable(HttpMethods.getInstance().chatList(page).subscribeWith(new BaseSubscriber<List<ChatListBean>>(){
            @Override
            public void onNext(List<ChatListBean> o) {
                super.onNext(o);
                getView().getChatListFinish(o);
            }
        }));

    }
}
