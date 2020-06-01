package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.LoginBean;
import com.ay.lxunhan.bean.model.PublicModel;

public interface RegisterContract {
    interface RegisterPresenter{
        void getCode(String phone,int type);
        void register(PublicModel publicModel);
    }
    interface RegisterView{
        void getCodeFinish();
        void registerFinish(LoginBean loginBean);
        void getCodeError();
        void showProgress();
        void hideProgress();
    }


}
