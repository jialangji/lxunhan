package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.VideoBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/5/29
 */
public interface VideoTypeContract {
    interface VideoTypePresenter{
        void getVideoType(String plate_id,int page);
        void attention(String uid);
    }
    interface VideoTypeView{
        void getVideoTypeFinish(List<VideoBean> list);
        void attentionFinish();
    }
}
