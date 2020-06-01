package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.UserInfoBean;

public interface MainContract {
    interface  MainPresenter{
        void getUserInfo();
    }
    interface  MainView{
        void getUserInfoFinish(UserInfoBean userInfoBean);
        void showProgress();
        void hideProgress();
    }
}
