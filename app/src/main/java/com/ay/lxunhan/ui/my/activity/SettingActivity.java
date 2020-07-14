package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.TextView;

import com.ay.lxunhan.MainActivity;
import com.ay.lxunhan.R;
import com.ay.lxunhan.base.AppContext;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideCacheUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

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
                AccountSecurityActivity.startAccountSecurity(this);
                break;
            case R.id.ll_msg_setting:
                boolean Jurisdiction = NotificationManagerCompat.from(AppContext.context()).areNotificationsEnabled();
                if (!Jurisdiction) {
                    gotoSet();
                }else {
                    ToastUtil.makeShortText(SettingActivity.this,"您已开启通知");
                }
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
                switch (UserInfo.getInstance().getLoginType()){
                    case 0:
                        break;
                    case  Contacts.QQLOGIN://QQ登录
                        AppContext.mTencent.logout(this);
                        break;
                    case  Contacts.WXLOGIN://微信退出
                        AppContext.mWxApi.unregisterApp();
                        break;
                    case Contacts.WBLOGIN:
                        break;
                }
                UserInfo.getInstance().clearAccess();
                NIMClient.getService(AuthService.class).logout();
                EventBus.getDefault().postSticky(new EventModel<>(EventModel.LOGIN_OUT));
                MainActivity.startMainActivity(this,0);
                break;
        }
    }

    //设置页面
    private void gotoSet() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", SettingActivity.this.getPackageName());
            intent.putExtra("app_uid", SettingActivity.this.getApplicationInfo().uid);
            startActivity(intent);
        }
//        else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
//            Intent intent = new Intent();
//            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
//            intent.setData(Uri.parse("package:" + Home2Activity.this.getPackageName()));
//            startActivity(intent);
//        }
        else {
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", SettingActivity.this.getPackageName(), null));
            startActivity(localIntent);
        }

    }
}
