package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.model.CompleteInfoModel;

import okhttp3.MultipartBody;

public interface CompleteInfoContract {

    interface CompleteInfoPresenter{
        void completeInfo(CompleteInfoModel completeInfoModel);
    }
    interface CompleteInfoView{
        void completeInfoFinish();
        void showProgress();
        void hideProgress();
    }
}
