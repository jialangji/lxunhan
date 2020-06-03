package com.ay.lxunhan.contract;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/2
 */
public interface SmallVideoListContract {
    interface SmallVideoListPrenster{
        void getSmallWatch(int id);
    }
    interface SmallVideoListView{
        void getSmallWatchFinish();

    }
}
