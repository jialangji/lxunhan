package com.ay.lxunhan.ui.video.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.TypeBean;
import com.ay.lxunhan.contract.VideoMainContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.VideoMainPresenter;
import com.ay.lxunhan.ui.public_ac.activity.Search2Activity;
import com.ay.lxunhan.widget.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;

public class VideoFragment extends BaseFragment<VideoMainContract.VideoMainView, VideoMainPresenter> implements VideoMainContract.VideoMainView {
    @BindView(R.id.tl_label_video)
    SlidingTabLayout tlLabel;
    @BindView(R.id.view_page_video)
    NoScrollViewPager viewPage;

    private List<TypeBean> arr = new ArrayList<>();
    private FragmentPagerAdapter vpAdapter;
    private int prePosition;


    public static VideoFragment newInstance() {
        Bundle args = new Bundle();
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    public void loadVp() {
        vpAdapter = new FragmentPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (arr.get(position).getId() == 1) {
                    return VideoHomeFrgament.newInstance();
                } else if (arr.get(position).getId() == 2) {
                    return SmallVideoFragment.newInstance();
                } else if (arr.get(position).getId() == 3) {
                    return VideoTypeFragment.newInstance("");
                }
                if (arr.get(position).getId() == 4) {//直播
                    return LiveFragment.newInstance("4");
                } else {
                    return VideoTypeFragment.newInstance(String.valueOf(arr.get(position).getId()));
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
        viewPage.setNoScroll(false);
        viewPage.setAdapter(vpAdapter);
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

    @Override
    protected void initData() {
        super.initData();
        presenter.getVideoType();
    }

    @Override
    public boolean isUserEvent() {
        return true;
    }


    @Override
    protected void getStickyEvent(Object eventModel) {
        super.getStickyEvent(eventModel);
        EventModel eventModel1 = (EventModel) eventModel;
        switch (eventModel1.getMessageType()) {
            case EventModel.CHANGECHANNEL:
                presenter.getVideoType();
                break;
            case EventModel.LOGIN:
            case EventModel.LOGIN_OUT:
                presenter.getVideoType();
                break;
            case EventModel.OPEN_LIVE:
                viewPage.setCurrentItem(1);
                break;
            case EventModel.OPEN_Video:
                viewPage.setCurrentItem(0);
                break;
        }
    }

    @Override
    public VideoMainPresenter initPresenter() {
        return new VideoMainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.iv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                Search2Activity.startSearch2Activity(getActivity());
                break;
        }
    }

    @Override
    public void getVideoTypeFinish(List<TypeBean> typeBeans) {
        arr.clear();
        for (TypeBean typeBean : typeBeans) {
            if (typeBean.getId() != 3) {
                arr.add(typeBean);
            }
        }
        loadVp();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            JzvdStd.goOnPlayOnPause();
        } else {
            JzvdStd.goOnPlayOnResume();
        }
    }
}
