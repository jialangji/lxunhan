package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.HotSearchBean;
import com.ay.lxunhan.contract.Search2Contract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/8/14
 */
public class Search2Presenter extends BasePresenter<Search2Contract.Search2View> implements Search2Contract.Search2Presenter {

    public Search2Presenter(Search2Contract.Search2View view) {
        super(view);
    }

    @Override
    public void getHot() {
        addDisposable(HttpMethods.getInstance().hotSearch().subscribeWith(new BaseSubscriber<List<HotSearchBean>>(){
            @Override
            public void onNext(List<HotSearchBean> o) {
                super.onNext(o);
                getView().getHotFinish(o);
            }
        }));
    }
}
