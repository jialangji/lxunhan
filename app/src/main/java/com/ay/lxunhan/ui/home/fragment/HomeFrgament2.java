package com.ay.lxunhan.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
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
import com.ay.lxunhan.ui.public_ac.activity.SearchActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.widget.NoScrollViewPager;

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
    TabLayout tlLabel;
    @BindView(R.id.view_page)
    NoScrollViewPager viewPage;
    private FragmentPagerAdapter vpAdapter;
    private List<TypeBean> arr = new ArrayList<>();

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
        loadVp();
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
        tlLabel.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_fc5a8e));
        tlLabel.setupWithViewPager(viewPage);
        tlLabel.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void initListener() {
        super.initListener();
        tlLabel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tabLayout选中状态
                View inflate = LayoutInflater.from(getContext()).inflate(R.layout.tab_text, null);
                TextView tv_tab = inflate.findViewById(R.id.tv_tab);
                //设置选中字体大小
                tv_tab.setTextSize(18);
                //替换字体大小
                tv_tab.setText(tab.getText());
                tab.setCustomView(tv_tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tabLayout未选中状态
                tab.setCustomView(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick({R.id.rl_search, R.id.iv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_search:
                if(isLogin()){
                    SearchActivity.startSearchActivity(getActivity(), Contacts.HISTORY_HOME);
                }
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
        vpAdapter.notifyDataSetChanged();

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
            case EventModel.ARTICLELIKE:
            case EventModel.SENDCOMMENT:
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
