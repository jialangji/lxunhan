package com.ay.lxunhan.ui.public_ac.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.HeUserBean;
import com.ay.lxunhan.contract.HeUserInfoContract;
import com.ay.lxunhan.presenter.HeUserInfoPresenter;
import com.ay.lxunhan.ui.message.activity.ChatP2PActivity;
import com.ay.lxunhan.ui.my.activity.AttentionActivity;
import com.ay.lxunhan.ui.my.activity.FansActivity;
import com.ay.lxunhan.ui.public_ac.fragment.UserDataFragment;
import com.ay.lxunhan.ui.public_ac.fragment.UserDynamicFrgament;
import com.ay.lxunhan.ui.public_ac.fragment.UserHomePageFrgament;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class FriendDetailActivity extends BaseActivity<HeUserInfoContract.HeUserInfoView, HeUserInfoPresenter> implements HeUserInfoContract.HeUserInfoView {

    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.ll_chat)
    LinearLayout llChat;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_media)
    TextView tvMedia;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_like_count)
    TextView tvLikeCount;
    @BindView(R.id.tv_fans_count)
    TextView tvFansCount;
    @BindView(R.id.tv_attention_count)
    TextView tvAttentionCount;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tl_label)
    TabLayout tlLabel;
    @BindView(R.id.view_page)
    NoScrollViewPager viewPage;
    private List<String> arr = new ArrayList<>();
    private String userID;
    private SensorManager sensorManager;
    private Jzvd.JZAutoFullscreenListener jzAutoFullscreenListener;
    private FragmentPagerAdapter vpAdapter;
    private boolean isMedia = true;
    private HeUserBean heUserBean;

    @Override
    public HeUserInfoPresenter initPresenter() {
        return new HeUserInfoPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getHeUserInfo(userID);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        userID = getIntent().getStringExtra("userId");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        jzAutoFullscreenListener = new Jzvd.JZAutoFullscreenListener();
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向\
        loadVp();

    }

    public void loadVp() {
        vpAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    if (isMedia) {
                        return UserHomePageFrgament.newInstance(userID);
                    } else {
                        return UserDynamicFrgament.newInstance(userID);
                    }
                } else {
                    return UserDataFragment.newInstance(userID);
                }
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
        viewPage.setNoScroll(true);
        tlLabel.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_fc5a8e));
        tlLabel.setupWithViewPager(viewPage);
        tlLabel.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.rl_finish, R.id.iv_header, R.id.tv_attention, R.id.ll_chat, R.id.ll_fans, R.id.ll_attention})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_fans:
                FansActivity.startFansActivity(this,userID,heUserBean.getIsMine());
                break;
            case R.id.ll_attention:
                AttentionActivity.startAttentionActivity(this,userID,heUserBean.getIsMine());
                break;
            case R.id.rl_finish:
                finish();
                break;
            case R.id.iv_header:
                UserInfoActivity.startUserInfoActivity(this);
                break;
            case R.id.tv_attention:
                if (isLogin()) {
                    presenter.attention(userID);
                }
                break;
            case R.id.ll_chat:
                ChatP2PActivity.startChat(this,heUserBean.getUqid());
                break;
        }
    }

    public static void startUserDetailActivity(Context context, String uid) {
        Intent intent = new Intent(context, FriendDetailActivity.class);
        intent.putExtra("userId", uid);
        context.startActivity(intent);
    }

    @Override
    public void getHeUserInfoFinish(HeUserBean bean) {
        heUserBean = bean;
        GlideUtil.loadCircleImgForHead(this, ivHeader, bean.getAvatar());
        tvName.setText(bean.getNickname());
        ivSex.setImageDrawable(getResources().getDrawable(bean.getSex() ? R.drawable.ic_man : R.drawable.ic_woman));
        tvAge.setText(bean.getDate_birth());
        tvLikeCount.setText(String.valueOf(bean.getLikeCount()));
        tvAttentionCount.setText(String.valueOf(bean.getBeFolCount()));
        tvFansCount.setText(String.valueOf(bean.getFolCount()));
        if (!TextUtils.isEmpty(bean.getProvince())&&!TextUtils.isEmpty(bean.getCity())){
            tvAddress.setText(String.format("%s·%s", bean.getProvince(), bean.getCity()));
        }else if (!TextUtils.isEmpty(bean.getProvince())){
            tvAddress.setText( bean.getProvince());
        }else if (!TextUtils.isEmpty(bean.getCity())){
            tvAddress.setText( bean.getCity());
        }else {
            tvAddress.setVisibility(View.GONE);

        }
        if (bean.getIs_fol() == 0) {
            tvAttention.setText(StringUtil.getString(R.string.add_attention));
        } else if (bean.getIs_fol() == 1) {
            tvAttention.setText(StringUtil.getString(R.string.attention_to));
        } else if (bean.getIs_fol() == 2) {
            tvAttention.setText(StringUtil.getString(R.string.attention_each_other));
            llChat.setVisibility(View.VISIBLE);
        }
        isMedia = bean.getIs_media();
        if (bean.getIs_media()) {
            arr.add("主页");
        } else {
            tvMedia.setVisibility(View.GONE);
            arr.add("动态");
        }
        arr.add("资料");
        if (bean.getIsMine()) {
            tvAttention.setVisibility(View.GONE);
            llChat.setVisibility(View.INVISIBLE);
        }
        vpAdapter.notifyDataSetChanged();


    }

    @Override
    public void getHeUserInfoDataFinish(HeUserBean bean) {

    }

    @Override
    public void attentionFinish() {
        if (heUserBean.getIs_fol() == 0) {
            heUserBean.setIs_fol(1);
            tvAttention.setText(StringUtil.getString(R.string.attention_to));
        } else {
            heUserBean.setIs_fol(0);
            tvAttention.setText(StringUtil.getString(R.string.add_attention));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //播放器重力感应
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(jzAutoFullscreenListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        JzvdStd.goOnPlayOnResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(jzAutoFullscreenListener);
        Jzvd.releaseAllVideos();
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
