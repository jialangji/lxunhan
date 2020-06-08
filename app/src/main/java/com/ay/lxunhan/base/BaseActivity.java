package com.ay.lxunhan.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.ay.lxunhan.R;
import com.ay.lxunhan.ui.login.LoginActivity;
import com.ay.lxunhan.utils.AppManager;
import com.ay.lxunhan.utils.DisplayUtil;
import com.ay.lxunhan.utils.StatusBarUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.blankj.utilcode.util.NetworkUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {
    protected P presenter;
    private Unbinder bind;
    public KProgressHUD hudLoader;
    protected Context mContext;
    protected ImmersionBar immersionBar;
    protected View titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        beforeSetContentView();

        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);

        mContext = this;
        presenter = initPresenter();
        afterSetContentView(savedInstanceState);
        initBar();
        initView();
        initData();
        initListener();
        if (isUserEvent()) {
            registerEvent();
        }

    }

    public boolean isUserEvent() {
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        initBar();
        if (!NetworkUtils.isConnected()) {
            if (null != AppContext.context()) {
                ToastUtil.makeShortText(AppContext.context(),"网络异常");
            }
        }
    }

    private void initBar() {
        if (isSetStatusBarAlpha()) {
            immersionBar = ImmersionBar.with(this)
                    .statusBarColor(getBarColor())     //状态栏颜色，不写默认透明色
                    .navigationBarColor(R.color.black); //导航栏颜色，不写默认黑色;
            //immersionBar.barColor(getBarColor());  //透明状态栏，不写默认透明色
            immersionBar.statusBarDarkFont(barTextIsDark());  //状态栏字体是深色，不写默认为亮色
            //immersionBar.hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR); //隐藏状态栏或导航栏或两者，不写默认不隐藏
            immersionBar.keyboardEnable(isKeyboardEnable()); //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
            titleBar = findViewById(R.id.view_title);
            if (titleBar != null) {
                immersionBar.titleBar(titleBar);
            }
            immersionBar.init();
        }
    }

    public boolean isKeyboardEnable() {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        DisplayUtil.handleAutoCloseKeyboard(isAutoCloseKeyboard(), getCurrentFocus(), ev, this);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        if (null != hudLoader) {
            hudLoader.dismiss();
        }
        if (null != bind) {
            bind.unbind();
        }

        if (null != presenter) {
            presenter.stop();
        }
        if (isUserEvent()) {
            unRegisterEvent();
        }
    }

    public void registerEvent() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void unRegisterEvent() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    protected boolean isAutoCloseKeyboard() {
        return true;
    }

    public abstract P initPresenter();

    protected abstract int getLayoutId();

    protected abstract int getBarColor();

    protected abstract boolean barTextIsDark();

    protected boolean isSetStatusBarAlpha() {
        return true;
    }


    protected void beforeSetContentView() {
    }

    protected void afterSetContentView(Bundle savedInstanceState) {
    }

    protected void initView() {
        hudLoader = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                //.setLabel(ResourceUtil.getContent(mContext, R.string.loading))
                //.setDetailsLabel(" 正在加载 ... ")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    protected void initData() {
    }

    protected void initListener() {
    }

    protected void initNoNteListener() {
    }

    protected void getEvent(Object eventModel) {
    }

    protected void getStickyEvent(Object eventModel) {
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


    public boolean isLogin(){
        if (!UserInfo.getInstance().isLogin()){
            LoginActivity.startLoginActivity(this);
            return false;
        }else {
            return true;
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }



    protected boolean isSetNoNetView() {
        return false;
    }

}
