package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.HeUserBean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/8
 */
public interface HeUserInfoContract {
    interface HeUserInfoPresenter{
        void getHeUserInfo(String uzid);
        void getHeUserInfoData(String uzid);
        void attention(String uid);
        void pullBlack(String bid);
    }

    interface HeUserInfoView{
        void getHeUserInfoFinish(HeUserBean bean);
        void getHeUserInfoDataFinish(HeUserBean bean);
        void pullBlackFinish();
        void attentionFinish();
        void showProgress();
        void hideProgress();
    }
}
