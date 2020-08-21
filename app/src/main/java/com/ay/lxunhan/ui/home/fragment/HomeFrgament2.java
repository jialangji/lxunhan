package com.ay.lxunhan.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.MultiItemBaseBean;
import com.ay.lxunhan.bean.TypeBean;
import com.ay.lxunhan.contract.HomeContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.HomePresenter;
import com.ay.lxunhan.ui.home.activity.ChannelManageActivity;
import com.ay.lxunhan.ui.public_ac.activity.Search2Activity;
import com.ay.lxunhan.widget.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.home.fragment
 * @date 2020/8/4
 */
public class HomeFrgament2 extends BaseFragment<HomeContract.HomeView, HomePresenter> implements HomeContract.HomeView  {
    @BindView(R.id.tl_label)
    SlidingTabLayout tlLabel;
    @BindView(R.id.view_page)
    NoScrollViewPager viewPage;
    private FragmentPagerAdapter vpAdapter;
    private List<TypeBean> arr = new ArrayList<>();
    private int prePosition;

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home2;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    public static HomeFrgament2 newInstance() {
        Bundle args = new Bundle();
        HomeFrgament2 fragment = new HomeFrgament2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getHomeType();

    }

    public void loadVp() {
        vpAdapter = new FragmentPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (arr.get(position).getId() == 1) {
                    return HomeAttentionFragment.newInstance();
                } else if (arr.get(position).getId() == 2) {
                    return HomeTypeFragment.newInstance("");
                } else if (arr.get(position).getId() == 3) {
                    return HomeAskFragment.newInstance();
                } else if (arr.get(position).getId() == 4){
                    return HomeQuizFragment.newInstance();
                }else {
                    return HomeTypeFragment.newInstance(String.valueOf(arr.get(position).getId()));
                }
            }

            @Override
            public int getCount() {
                return arr.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return arr.get(position).getName();
            }
        };
        viewPage.setAdapter(vpAdapter);
        viewPage.setNoScroll(false);
        tlLabel.setViewPager(viewPage);
        TextView tvTitle = tlLabel.getTitleView(prePosition);
        tvTitle.setTextSize(20);

    }

    @Override
    protected void initListener() {
        super.initListener();
        tlLabel.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                TextView tvTitle = tlLabel.getTitleView(position);
                tvTitle.setTextSize(20);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (prePosition != i) {
                    TextView tvTitle = tlLabel.getTitleView(prePosition);
                    tvTitle.setTextSize(16);
                }
                TextView tvTitle = tlLabel.getTitleView(i);
                tvTitle.setTextSize(20);
                prePosition = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick({R.id.rl_search, R.id.iv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_search:
                Search2Activity.startSearch2Activity(getActivity());
                break;
            case R.id.iv_more:
                if (isLogin()){
                    ChannelManageActivity.stratChannelManageActivity(getActivity(), false);
                }
                break;
        }
    }

    @Override
    public void getHomeListFinish(List<MultiItemBaseBean> homeListBeans) {

    }

    @Override
    public void getHomeTypeFinish(List<TypeBean> typeBeans) {
        arr.clear();
        for (TypeBean typeBean : typeBeans) {
            if (typeBean.getId() != 5) {
                arr.add(typeBean);
            }
        }
        loadVp();
        viewPage.setCurrentItem(1);


    }

    @Override
    public boolean isUserEvent() {
        return true;
    }

    @Override
    protected void getStickyEvent(Object eventModel) {
        super.getStickyEvent(eventModel);
        EventModel model = (EventModel) eventModel;
        switch (model.getMessageType()) {
            case EventModel.CHANGECHANNEL:
                presenter.getHomeType();
                break;
            case EventModel.LOGIN:
            case EventModel.LOGIN_OUT:
                presenter.getHomeType();
                break;


        }
    }

    @Override
    public void attentionFinish() {

    }

    @Override
    public void quziFinish() {

    }

    @Override
    public void addLikeFinish() {

    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }
}
