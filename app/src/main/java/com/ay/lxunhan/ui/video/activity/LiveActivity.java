package com.ay.lxunhan.ui.video.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.LiveBean;
import com.ay.lxunhan.contract.LiveTypeContract;
import com.ay.lxunhan.presenter.LiveTypePresenter;
import com.ay.lxunhan.ui.public_ac.activity.SearchActivity;
import com.ay.lxunhan.ui.video.fragment.LiveFragment;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LiveActivity extends BaseActivity<LiveTypeContract.LiveTypeView, LiveTypePresenter> implements LiveTypeContract.LiveTypeView {


    @BindView(R.id.tl_label)
    TabLayout tlLabel;
    @BindView(R.id.view_page)
    NoScrollViewPager viewPage;
    private List<LiveBean> liveBeans=new ArrayList<>();
    private FragmentPagerAdapter vpAdapter;

    @Override
    public LiveTypePresenter initPresenter() {
        return new LiveTypePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getLiveType();
    }

    @Override
    protected void initView() {
        super.initView();
        loadVp();
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    public void loadVp() {
        vpAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        tlLabel.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_fc5a8e));
        tlLabel.setupWithViewPager(viewPage);
        tlLabel.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @OnClick({R.id.rl_finish, R.id.rl_search, R.id.iv_live})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.rl_search:
                SearchActivity.startSearchActivity(this, Contacts.HISTORY_LIVE);
                break;
            case R.id.iv_live:
                break;
        }
    }

    @Override
    public void getLiveTypeFinish(List<LiveBean> list) {
        liveBeans.clear();
        liveBeans.addAll(list);
        vpAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }


    public static void startLiveActivity(Context context){
        Intent intent=new Intent(context,LiveActivity.class);
        context.startActivity(intent);
    }
}
