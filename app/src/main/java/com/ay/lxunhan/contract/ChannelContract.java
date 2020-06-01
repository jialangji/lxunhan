package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.ChannelBean;

public interface ChannelContract {
    interface ChannelPresenter{
        void channelManagement();
        void channelVideoManagement();
        void addChannel(String id);
        void deleteChannel(String id);
        void addVideoChannel(String id);
        void deleteVideoChannel(String id);
    }

    interface ChannelView{
        void channelManageFinish(ChannelBean channelBean);
        void addChannelFinish();
        void deleteChannelFinish();
        void showProgress();
        void hideProgress();
    }

}
