package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.InviteBean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/29
 */
public interface InviteContract {
    interface InvitePresenter{
        void sendInviteCode(String code);
        void getInviteCoin(int page);
    }
    interface InviteView{
        void sendInviteCodeFinish();
        void getInviteCoinFinish(InviteBean bean);
        void showProgress();
        void hideProgress();
    }
}
