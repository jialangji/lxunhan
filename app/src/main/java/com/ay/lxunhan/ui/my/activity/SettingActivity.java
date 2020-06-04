package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.ay.lxunhan.MainActivity;
import com.ay.lxunhan.R;
import com.ay.lxunhan.base.AppContext;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.utils.AppManager;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideCacheUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.tv_account_type)
    TextView tvAccountType;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.tv_video_type)
    TextView tvVideoType;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        tvCache.setText(GlideCacheUtil.getInstance().getCacheSize(AppContext.context()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    public static void startSettingActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }
    @OnClick({R.id.rl_finish, R.id.ll_account, R.id.ll_msg_setting, R.id.ll_agreement, R.id.ll_clear_cache, R.id.ll_video_load, R.id.ll_about_app, R.id.tv_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.ll_account:
                break;
            case R.id.ll_msg_setting:
                break;
            case R.id.ll_agreement:
                break;
            case R.id.ll_clear_cache:
                tvCache.setText("0.0MB");
                GlideCacheUtil.getInstance().clearImageAllCache(AppContext.context());
                break;
            case R.id.ll_video_load:
                break;
            case R.id.ll_about_app:
                break;
            case R.id.tv_login_out:
                UserInfo.getInstance().clearAccess();
                EventBus.getDefault().postSticky(new EventModel<>(EventModel.LOGIN_OUT));
                MainActivity.startMainActivity(this,0);
                break;
        }
    }
}
