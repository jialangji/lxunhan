package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.bean.model.WithdrawModel;

public interface WithdrawContract {
    interface WithdrawPresenter{
        void getUserInfo();
        void withdraw(WithdrawModel withdrawModel);
    }
    interface WithdrawView{
        void getUserInfoFinish(UserInfoBean userInfoBean);
        void withdrawFinish();
        void showProgress();
        void hideProgress();
    }
}
