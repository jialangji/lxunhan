package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.VideoBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/2
 */
public interface SmallVideoListContract {
    interface SmallVideoListPrenster{
        void getSmallVideo(int page,int id);
        void getSmallWatch(int id);
    }
    interface SmallVideoListView{
        void getSmallWatchFinish();
        void getSmallVideoFinish(List<VideoBean> list);
    }
}
