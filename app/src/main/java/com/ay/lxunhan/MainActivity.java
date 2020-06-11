package com.ay.lxunhan;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.contract.MainContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.MainPresenter;
import com.ay.lxunhan.ui.home.HomeFragment;
import com.ay.lxunhan.ui.login.BootPageActivity;
import com.ay.lxunhan.ui.message.MessageFrgament;
import com.ay.lxunhan.ui.my.MyFragment;
import com.ay.lxunhan.ui.video.fragment.VideoFragment;
import com.ay.lxunhan.utils.AppManager;
import com.ay.lxunhan.utils.ResourceUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static android.provider.UserDictionary.Words.APP_ID;

public class MainActivity extends BaseActivity<MainContract.MainView, MainPresenter> implements MainContract.MainView {
    @BindView(R.id.iv_tab1)
    ImageView ivTab1;
    @BindView(R.id.layout_tab1)
    RelativeLayout layoutTab1;
    @BindView(R.id.iv_tab2)
    ImageView ivTab2;
    @BindView(R.id.layout_tab2)
    RelativeLayout layoutTab2;
    @BindView(R.id.iv_tab3)
    ImageView ivTab3;
    @BindView(R.id.layout_tab3)
    RelativeLayout layoutTab3;
    @BindView(R.id.iv_tab4)
    ImageView ivTab4;
    @BindView(R.id.layout_tab4)
    RelativeLayout layoutTab4;
    @BindView(R.id.layout_tab)
    LinearLayout layoutTab;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.layout_content)
    FrameLayout layoutContent;
    @BindView(R.id.tv_tab1)
    TextView tvTab1;
    @BindView(R.id.tv_tab2)
    TextView tvTab2;
    @BindView(R.id.tv_tab3)
    TextView tvTab3;
    @BindView(R.id.tv_tab4)
    TextView tvTab4;
    private View[] tabs = new View[4];
    private View[] tabsTv = new View[4];
    private View[] tabsIv = new View[4];
    private Fragment[] fragments;

    private long exitTime = 0;
    private int currentPosition = -1, prePosition;
    private static final String TAB_SELECT_POS = "tab_select_position";

    private SensorManager sensorManager;
    private Jzvd.JZAutoFullscreenListener jzAutoFullscreenListener;

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
    protected void initView() {
        super.initView();
        ImmersionBar.with(this).init();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        jzAutoFullscreenListener = new Jzvd.JZAutoFullscreenListener();
        //设置全屏播放
        JzvdStd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
        JzvdStd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向
        currentPosition = getIntent().getIntExtra("pox", -1);
        tabs[0] = layoutTab1;
        tabs[1] = layoutTab2;
        tabs[2] = layoutTab3;
        tabs[3] = layoutTab4;
        tabsTv[0] = tvTab1;
        tabsTv[1] = tvTab2;
        tabsTv[2] = tvTab3;
        tabsTv[3] = tvTab4;
        tabsIv[0] = ivTab1;
        tabsIv[1] = ivTab2;
        tabsIv[2] = ivTab3;
        tabsIv[3] = ivTab4;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showAppointFragment(intent.getIntExtra("pos", 0));
    }

    public void showAppointFragment(int position) {
        currentPosition = position;
        setTabSelected(currentPosition);
        showFragment(currentPosition);

    }

    @Override
    protected void initData() {
        super.initData();
        tabs[0].performClick();
        if (UserInfo.getInstance().isLogin()) {
            presenter.getUserInfo();
        }
    }


    private Fragment[] getFragments() {
        Fragment[] fragments = new Fragment[4];
        fragments[0] = getFragment(0);
        fragments[1] = getFragment(1);
        fragments[2] = getFragment(2);
        fragments[3] = getFragment(3);
        return fragments;
    }

    @Override
    protected void afterSetContentView(Bundle savedInstanceState) {
        super.afterSetContentView(savedInstanceState);
        if (savedInstanceState == null) {
            fragments = getFragments();
            prePosition = -1;
        } else {
            fragments = new Fragment[4];
            fragments[0] = getSupportFragmentManager().findFragmentByTag(APP_ID + "0");
            fragments[1] = getSupportFragmentManager().findFragmentByTag(APP_ID + "1");
            fragments[2] = getSupportFragmentManager().findFragmentByTag(APP_ID + "2");
            fragments[3] = getSupportFragmentManager().findFragmentByTag(APP_ID + "3");
            prePosition = savedInstanceState.getInt(TAB_SELECT_POS);
        }
    }

    private void showFragment(int position) {
        if (position != prePosition) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            if (prePosition != -1 && fragments[prePosition] != null) {
                trx.hide(fragments[prePosition]);
            }
            //防止onCreate方法 获取fragment为空
            if (fragments[position] == null) {
                fragments[position] = getFragment(position);
            }
            if (!fragments[position].isAdded()) {
                trx.add(R.id.layout_content, fragments[position], APP_ID + position);
            }
            //trx.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
            trx.show(fragments[position]).commit();

            prePosition = position;
        } else {
            //重新点击了
        }
    }

    private Fragment getFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = HomeFragment.newInstance();
                break;
            case 1:
                fragment = VideoFragment.newInstance();
                break;
            case 2:
                fragment = MessageFrgament.newInstance();
                break;
            case 3:
                fragment = MyFragment.newInstance();
                break;
        }

        return fragment;
    }

    public void setTabSelected(int tabSelected) {
        for (View tab : tabs) {
            if (tab instanceof LinearLayout) {
                int childCount = ((LinearLayout) tab).getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = ((LinearLayout) tab).getChildAt(i);
                    childAt.setSelected(tab == tabs[tabSelected]);
                }
            }
        }
        for (int i = 0; i < tabsTv.length; i++) {
            if (tabsTv[i] instanceof TextView) {
                if (i == tabSelected) {
                    ((TextView) tabsTv[i]).setTextColor(getResources().getColor(R.color.end_color));
                } else {
                    ((TextView) tabsTv[i]).setTextColor(getResources().getColor(R.color.color_C8C8C8));
                }
            }
        }
        for (int i = 0; i < tabsIv.length; i++) {
            if (tabsIv[i] instanceof ImageView) {
                if (i == tabSelected) {
                    tabsIv[i].setSelected(true);
                } else {
                    tabsIv[i].setSelected(false);
                }
            }
        }

    }


    @OnClick({R.id.layout_tab1, R.id.layout_tab2, R.id.layout_tab3, R.id.layout_tab4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_tab1:
                if (currentPosition == 0) {
                    return;
                }
                currentPosition = 0;
                setTabSelected(currentPosition);
                showFragment(currentPosition);
                break;
            case R.id.layout_tab2:
                if (currentPosition == 1) {
                    return;
                }
                currentPosition = 1;
                setTabSelected(currentPosition);
                showFragment(currentPosition);
                break;
            case R.id.layout_tab3:
                if (isLogin()) {
                    if (currentPosition == 2) {
                        return;
                    }
                    currentPosition = 2;
                    setTabSelected(currentPosition);
                    showFragment(currentPosition);
                }
                break;
            case R.id.layout_tab4:
                if (isLogin()) {
                    if (currentPosition == 3) {
                        return;
                    }
                    currentPosition = 3;
                    setTabSelected(currentPosition);
                    showFragment(currentPosition);
                }
                break;
        }
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
            case EventModel.LOGIN_OUT:
                BootPageActivity.startBootPageActivity(this);
                presenter.getUserInfo();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.makeShortText(mContext, ResourceUtil.getContent(mContext, R.string.again_exit));
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().AppExit();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(jzAutoFullscreenListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //播放器重力感应
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(jzAutoFullscreenListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void startMainActivity(Context context, int currentPosition) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("pox", currentPosition);
        context.startActivity(intent);
    }

    @Override
    public void getUserInfoFinish(UserInfoBean userInfoBean) {
        UserInfo.getInstance().setAvatar(userInfoBean.getAvatar());
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
