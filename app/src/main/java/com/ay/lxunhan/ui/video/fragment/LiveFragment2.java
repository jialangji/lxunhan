package com.ay.lxunhan.ui.video.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.LiveBean;
import com.ay.lxunhan.contract.LiveTypeContract;
import com.ay.lxunhan.presenter.LiveTypePresenter;
import com.ay.lxunhan.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.video.fragment
 * @date 2020/8/5
 */
public class LiveFragment2 extends BaseFragment<LiveTypeContract.LiveTypeView, LiveTypePresenter> implements LiveTypeContract.LiveTypeView {
    @BindView(R.id.tl_label)
    TabLayout tlLabel;
    @BindView(R.id.view_page_live)
    NoScrollViewPager viewPage;
    private List<LiveBean> liveBeans=new ArrayList<>();
    private FragmentPagerAdapter vpAdapter;
    @Override
    public LiveTypePresenter initPresenter() {
        return new LiveTypePresenter(this);
    }

    public static LiveFragment2 newInstance() {
        Bundle args = new Bundle();
        LiveFragment2 fragment = new LiveFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live2;
    }


    @Override
    protected void initData() {
        super.initData();
        presenter.getLiveType(0);
    }

    @Override
    protected void initView() {
        super.initView();
        loadVp();
    }

    public void loadVp() {
        vpAdapter = new FragmentPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return LiveFragment.newInstance(String.valueOf(liveBeans.get(position).getId()));
            }

            @Override
            public int getCount() {
                return liveBeans.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return liveBeans.get(position).getName();
            }
        };
        viewPage.setAdapter(vpAdapter);
        viewPage.setNoScroll(true);
        tlLabel.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_fc5a8e));
        tlLabel.setupWithViewPager(viewPage);
        tlLabel.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @Override
    public void getLiveTypeFinish(List<LiveBean> list) {
        liveBeans.clear();
        liveBeans.addAll(list);
        vpAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {
    }

}
