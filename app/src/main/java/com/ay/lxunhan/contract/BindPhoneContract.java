package com.ay.lxunhan.contract;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/7/13
 */
public interface BindPhoneContract {
    interface BindPhonePresenter{
        void getCode(String phone);
        void getBindPhone(String phone,String code);
    }

    interface BindPhoneView{
        void getCodeFinish();
        void bindPhoneFinish();
        void showProgress();
        void hideProgress();
    }
}
