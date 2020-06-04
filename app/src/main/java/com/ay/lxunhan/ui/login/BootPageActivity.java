package com.ay.lxunhan.ui.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.ay.lxunhan.MainActivity;
import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.utils.UserInfo;

import butterknife.OnClick;

public class BootPageActivity extends BaseActivity {

    @Override
    protected void initView() {
        super.initView();
        if (UserInfo.getInstance().isLogin()){
            MainActivity.startMainActivity(this);
            finish();
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_boot_page;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.tv_skip, R.id.tv_register, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                MainActivity.startMainActivity(this);
                finish();
                break;
            case R.id.tv_register:
                RegisterActivity.startRegisterActivity(this,1);
                break;
            case R.id.tv_login:
                LoginActivity.startLoginActivity(this);
                break;
        }
    }

    public static void startBootPageActivity(Context context){
        Intent intent=new Intent(context,BootPageActivity.class);
        context.startActivity(intent);
    }
}
