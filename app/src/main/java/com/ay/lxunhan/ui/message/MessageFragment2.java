package com.ay.lxunhan.ui.message;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.RelativeLayout;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.ui.message.activity.AddFriendActivity;
import com.ay.lxunhan.ui.message.activity.FriendActivity;
import com.ay.lxunhan.ui.public_ac.activity.IssueActivity;
import com.ay.lxunhan.utils.PermissionsUtils;
import com.ay.lxunhan.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.message
 * @date 2020/8/6
 */
public class MessageFragment2 extends BaseFragment {

    @BindView(R.id.tl_label_video)
    TabLayout tlLabel;
    @BindView(R.id.view_page_message)
    NoScrollViewPager viewPage;
    @BindView(R.id.rl_add_friend)
    RelativeLayout rlAddFriend;
    @BindView(R.id.rl_friend_list)
    RelativeLayout rlFriendList;
    @BindView(R.id.rl_edit)
    RelativeLayout rlEdit;
    private List<String> str;
    private FragmentPagerAdapter vpAdapter;

    public static MessageFragment2 newInstance() {
        Bundle args = new Bundle();
        MessageFragment2 fragment = new MessageFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message2;
    }

    @Override
    protected void initView() {
        super.initView();
        str = new ArrayList<>();
        str.add("消息");
        str.add("动态圈");
        loadVp();
    }

    public void loadVp() {
        vpAdapter = new FragmentPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position==0) {
                    return MessageListFragment.newInstance();
                } else {
                    return PyqFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return str.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return str.get(position);
            }
        };
        viewPage.setAdapter(vpAdapter);
        viewPage.setNoScroll(true);
        tlLabel.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        tlLabel.setupWithViewPager(viewPage);
        tlLabel.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initListener() {
        super.initListener();
        tlLabel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("消息")){
                    rlAddFriend.setVisibility(View.VISIBLE);
                    rlFriendList.setVisibility(View.VISIBLE);
                    rlEdit.setVisibility(View.GONE);
                }else {
                    rlAddFriend.setVisibility(View.GONE);
                    rlFriendList.setVisibility(View.GONE);
                    rlEdit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.rl_add_friend, R.id.rl_friend_list,R.id.rl_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_add_friend:
                AddFriendActivity.startAddFriendActivity(getActivity());
                break;
            case R.id.rl_friend_list:
                FriendActivity.startFriendActivity(getActivity());
                break;
            case R.id.rl_edit:
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
        }
    }

}
