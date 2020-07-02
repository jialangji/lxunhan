package com.ay.lxunhan.ui.video.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.TypeBean;
import com.ay.lxunhan.contract.VideoMainContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.VideoMainPresenter;
import com.ay.lxunhan.ui.home.activity.ChannelManageActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.ui.public_ac.activity.IssueActivity;
import com.ay.lxunhan.ui.public_ac.activity.SearchActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.PermissionsUtils;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoFragment extends BaseFragment<VideoMainContract.VideoMainView, VideoMainPresenter> implements VideoMainContract.VideoMainView {


    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tl_label)
    TabLayout tlLabel;
    @BindView(R.id.view_page)
    NoScrollViewPager viewPage;

    private List<TypeBean> arr = new ArrayList<>();
    private FragmentPagerAdapter vpAdapter;


    public static VideoFragment newInstance() {
        Bundle args = new Bundle();
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
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
                if (arr.get(position).getId() == 1) {
                    return VideoHomeFrgament.newInstance();
                } else if (arr.get(position).getId() == 2) {
                    return SmallVideoFragment.newInstance();
                } else if (arr.get(position).getId() == 3) {
                    return VideoTypeFragment.newInstance("");
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
        viewPage.setAdapter(vpAdapter);
        viewPage.setNoScroll(true);
        tlLabel.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
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

    @OnClick({R.id.iv_header, R.id.rl_search, R.id.iv_edit, R.id.iv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
                if (isLogin()){
                    FriendDetailActivity.startUserDetailActivity(getActivity(),"");
                }
                break;
            case R.id.rl_search:
                if(isLogin()){
                    SearchActivity.startSearchActivity(getActivity(), Contacts.HISTORY_VIDEO);
                }
                break;
            case R.id.iv_edit:
                PermissionsUtils.getInstance().chekPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new PermissionsUtils.IPermissionsResult() {
                            @Override
                            public void passPermissons() {
                                IssueActivity.startIssueActivity(getActivity());
                            }

                            @Override
                            public void forbitPermissons() {

                            }
                        });
                break;
            case R.id.iv_more:
                if (isLogin()){
                    ChannelManageActivity.stratChannelManageActivity(getActivity(), true);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GlideUtil.loadCircleImgForHead(getActivity(), ivHeader, UserInfo.getInstance().getAvatar());

    }

    @Override
    public void getVideoTypeFinish(List<TypeBean> typeBeans) {
        arr.clear();
        arr.addAll(typeBeans);
        vpAdapter.notifyDataSetChanged();
    }
}
