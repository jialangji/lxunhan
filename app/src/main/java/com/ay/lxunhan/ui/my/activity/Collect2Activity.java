package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.ui.my.fragment.CollectFragment;
import com.ay.lxunhan.widget.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Collect2Activity extends BaseActivity {

    @BindView(R.id.tl_label)
    SlidingTabLayout tlLabel;
    @BindView(R.id.view_page_collect)
    NoScrollViewPager viewPage;
    private List<String> arr=new ArrayList<>();
    private FragmentPagerAdapter vpAdapter;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect2;
    }

    @Override
    protected void initView() {
        super.initView();
        arr.add("资讯");
        arr.add("视频");
        loadVp();

    }

    public static void startCollectActivity(Context context) {
        Intent intent = new Intent(context, Collect2Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick(R.id.rl_finish)
    public void onViewClicked() {
        finish();
    }

    public void loadVp() {
        vpAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
               return CollectFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return arr.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return arr.get(position);
            }
        };
        viewPage.setAdapter(vpAdapter);
        viewPage.setNoScroll(false);
        tlLabel.setViewPager(viewPage);
    }
}
