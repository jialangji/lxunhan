package com.ay.lxunhan.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ay.lxunhan.R;
import com.ay.lxunhan.utils.StatusBarUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment {
    protected P presenter;
    protected BaseActivity mActivity;
    public KProgressHUD hudLoader;
    Unbinder unbinder;
    protected ImmersionBar immersionBar;
    protected View titleBar;

    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mContext = getActivity();
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewBefore();
        titleBar = view.findViewById(R.id.view_title);

        initBar();
        initView();
        initData();
        initListener();
        if (isUserEvent()) {
            registerEvent();
        }
    }

    private void initBar() {
        if (isSetStatusBarAlpha()) {
            immersionBar = ImmersionBar.with(this)
                    .statusBarColor(getBarColor())     //状态栏颜色，不写默认透明色
                    .navigationBarColor(R.color.black); //导航栏颜色，不写默认黑色;
//            immersionBar.barColor(getBarColor());  //透明状态栏，不写默认透明色
            immersionBar.statusBarDarkFont(barTextIsDark());  //状态栏字体是深色，不写默认为亮色
            //immersionBar.hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR); //隐藏状态栏或导航栏或两者，不写默认不隐藏
            immersionBar.keyboardEnable(isKeyboardEnable()); //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
//            titleBar = findViewById(R.id.view_title);
            if (titleBar != null) {
                immersionBar.titleBar(titleBar);
            }
            immersionBar.init();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initBar();
        }
    }


    public abstract P initPresenter();

    protected abstract int getLayoutId();

    protected void initViewBefore() {
    }

    protected void initView() {
        if (null != mContext) {
            hudLoader = KProgressHUD.create(mContext)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    //.setLabel(ResourceUtil.getContent(mContext, R.string.loading))
                    //.setDetailsLabel(" 正在加载 ... ")
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f);
        }
        presenter = initPresenter();

    }

    public void setImmersionBarIsDarkFont(boolean isDark) {
        if (null != immersionBar) {
            immersionBar.statusBarDarkFont(isDark);
            immersionBar.init();
        }
    }

    protected void initData() {
    }

    protected void initListener() {
    }


    protected boolean isSetStatusBarAlpha() {
        return true;
    }

    protected abstract int getBarColor();


    protected abstract boolean barTextIsDark();

    public boolean isUserEvent() {
        return false;
    }

    public void registerEvent() {
        EventBus.getDefault().register(this);
    }

    public void unRegisterEvent() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected void getEvent(Object eventModel) {
    }

    protected void getStickyEvent(Object eventModel) {
    }
    public boolean isKeyboardEnable() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != hudLoader) {
            hudLoader.dismiss();
        }
        if (null != unbinder) {
            unbinder.unbind();
        }
        mContext = null;
        if (null != presenter) {
            presenter.stop();
        }
        if (isUserEvent()) {
            unRegisterEvent();
        }
    }

    //接收到刷新未读消息数据的通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Object eventModel) {
        if (null != eventModel) {
            getEvent(eventModel);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)//粘性事件接收处理
    public void onStickyEvent(Object eventModel) {
        if (null != eventModel) {
            getStickyEvent(eventModel);
        }
    }

}
