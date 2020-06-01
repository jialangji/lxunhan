package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.Country;
import com.ay.lxunhan.bean.CountryBean;
import com.ay.lxunhan.bean.UserInfoBean;

import java.util.List;

public interface UserInfoContract  {

    interface UserInfoPresenter{
        void getUserInfo();
        void getCountry();
    }
    interface UserInfoView{
        void getUserInfoFinish(UserInfoBean userInfoBean);
        void getCountryFinish(List<CountryBean> list);
        void showProgress();
        void hideProgress();
    }
}
