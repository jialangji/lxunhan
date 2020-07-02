package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.ChatImBean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/30
 */
public interface ChatImContract {
    interface ChatImPresenter{
        void getChatImShow(String userId,int page);
    }
    interface ChatImView{
        void getChatImShowFinish(ChatImBean imBean);
    }
}
