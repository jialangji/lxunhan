package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.VideoBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/5/29
 */
public interface SmallVideoContract {
    interface SmallVideoPresenter{
        void getSmallVideo(int page);
    }
    interface SmallVideoView{
        void getSmallVideoFinish(List<VideoBean> list);
    }
}
