package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.CountryBean;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.contract.UserInfoContract;
import com.ay.lxunhan.presenter.UserInfoPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountSecurityActivity extends BaseActivity<UserInfoContract.UserInfoView, UserInfoPresenter> implements UserInfoContract.UserInfoView {

    @BindView(R.id.tv_lxh)
    TextView tvLxh;
    @BindView(R.id.tv_account_type)
    TextView tvAccountType;

    @Override
    public UserInfoPresenter initPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_security;
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.getUserInfo();
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    public static void startAccountSecurity(Context context) {
        Intent intent = new Intent(context, AccountSecurityActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.rl_finish, R.id.ll_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.ll_account:
                UpdatePhoneActivity.startUpdatePhoneActivity(this);
                break;
        }
    }

    @Override
    public void getUserInfoFinish(UserInfoBean userInfoBean) {
        tvLxh.setText(userInfoBean.getSignal());
        tvAccountType.setText(userInfoBean.getPhone());
    }

    @Override
    public void getCountryFinish(List<CountryBean> list) {

    }

    @Override
    public void updateUserInfoFinish() {

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
