package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.PeopleBean;
import com.ay.lxunhan.bean.VideoBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/5/29
 */
public interface VideoHomeContract {

    interface VideoHomePresenter{
        void getVideoHomeList(String title,int page);
        void videoRecords();
    }
    interface VideoHomeView{
        void getVideoHomeListFinish(List<VideoBean> list);
        void videoRecords(List<PeopleBean> peopleBeans);
    }
}
