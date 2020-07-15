package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.CreateBean;
import com.ay.lxunhan.bean.LiveBean;
import com.ay.lxunhan.bean.LiveDetailBean;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/7/15
 */
public interface CreateLiveContract {
    interface CreateLivePresenter{
        void getLiveType();
        void getLiveDetail();
        void createLive(String title, String type, MultipartBody.Part part);
    }

    interface CreateLiveView{
        void getLiveTypeFinish(List<LiveBean> beans);
        void getLiveDetailFinish(LiveDetailBean bean);
        void createLiveFinish(CreateBean createBean);
        void showProgress();
        void hideProgress();
    }

}
