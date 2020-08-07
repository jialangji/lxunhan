package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.GiftBean;
import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.LiveDetail;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/7/21
 */
public interface LiveContract {
    interface LivePresenter {
        void liveSeeCount(String lid, boolean isAdd);

        void giftList(String type);

        void liveClose(String lid);

        void sendGift(String lid, String aid, String count);

        void getLiveInfo(String lid);

        void openLive(String openLive);

        void getGold();

        void attention(String uid);
    }

    interface LiveView{
        void liveSeecountFinish(boolean isAdd);
        void giftListFinish(List<GiftBean> giftBeans);
        void liveCloseFinish();
        void sendGiftFinish();
        void getLiveInfoFinish(LiveDetail liveDetail);
        void openLiveFinish();
        void attentionFinish();
        void getShowFinish(LbBean lbBean);
        void showProgress();
        void hideProgress();
    }
}
