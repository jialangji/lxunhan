package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.ChatListBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/4
 */
public interface MessageContract  {
    interface MessagePresenter{
        void getChatList(int page);

    }
    interface MessageView{
        void getChatListFinish(List<ChatListBean> listBeans);
        void showProgress();
        void hideProgress();
    }
}
