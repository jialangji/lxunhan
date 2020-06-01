package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.LoginBean;
import com.ay.lxunhan.bean.model.PublicModel;

public interface LoginContract {
    interface LoginPresenter{
        void getCode(String phone,int type);
        void login(PublicModel publicModel);
    }
    interface LoginView{
        void getCodeFinish();
        void loginFinish(LoginBean loginBean);
        void getCodeError();
        void showProgress();
        void hideProgress();
    }
}
