package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.contract.BindPhoneContract;
import com.ay.lxunhan.presenter.BindPhonePresenter;
import com.ay.lxunhan.utils.RxHelper;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePhoneActivity extends BaseActivity<BindPhoneContract.BindPhoneView, BindPhonePresenter> implements BindPhoneContract.BindPhoneView {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_get)
    TextView tvGet;
    @BindView(R.id.et_code)
    EditText etCode;

    @Override
    public BindPhonePresenter initPresenter() {
        return new BindPhonePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_phone;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @Override
    public void getCodeFinish() {
        RxHelper.getCode(tvGet);
    }

    @Override
    public void bindPhoneFinish() {
        finish();
    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }

    @OnClick({R.id.rl_finish, R.id.tv_get, R.id.tv_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_get:
                if (!TextUtils.isEmpty(StringUtil.getFromEdit(etPhone))) {
                    if (Utils.isChinaPhoneLegal(StringUtil.getFromEdit(etPhone))) {
                        tvGet.setEnabled(false);
                        presenter.getCode(StringUtil.getFromEdit(etPhone));
                    } else {
                        ToastUtil.makeShortText(this, "请输入正确的手机号");
                    }
                } else {
                    ToastUtil.makeShortText(this, "请输入手机号");
                }
                break;
            case R.id.tv_bind:
                if (TextUtils.isEmpty(StringUtil.getFromEdit(etPhone))) {
                    ToastUtil.makeShortText(this, "请输入手机号");
                    return;
                }
                if (!Utils.isChinaPhoneLegal(StringUtil.getFromEdit(etPhone))) {
                    ToastUtil.makeShortText(this, "请输入正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(StringUtil.getFromEdit(etCode))) {
                    ToastUtil.makeShortText(this, "请输入验证码");
                    return;
                }
                presenter.getBindPhone(StringUtil.getFromEdit(etPhone),StringUtil.getFromEdit(etCode));
                break;
        }
    }


    public static void startUpdatePhoneActivity(Context context){
        Intent intent=new Intent(context,UpdatePhoneActivity.class);
        context.startActivity(intent);
    }
}
