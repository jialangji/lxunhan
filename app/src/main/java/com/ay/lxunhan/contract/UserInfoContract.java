package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.CountryBean;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.bean.model.CompleteInfoModel;

import java.util.List;

import okhttp3.MultipartBody;

public interface UserInfoContract  {

    interface UserInfoPresenter{
        void getUserInfo();
        void getCountry();
        void completeInfo(CompleteInfoModel completeInfoModel);
        void completeInfoImg(MultipartBody.Part part);
    }
    interface UserInfoView{
        void getUserInfoFinish(UserInfoBean userInfoBean);
        void getCountryFinish(List<CountryBean> list);
        void updateUserInfoFinish();
        void showProgress();
        void hideProgress();
    }
}
