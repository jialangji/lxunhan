package com.ay.lxunhan.ui.public_ac.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ay.lxunhan.MainActivity;
import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.contract.BindPhoneContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.BindPhonePresenter;
import com.ay.lxunhan.ui.login.BootPageActivity;
import com.ay.lxunhan.utils.AppManager;
import com.ay.lxunhan.utils.RxHelper;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity<BindPhoneContract.BindPhoneView, BindPhonePresenter> implements BindPhoneContract.BindPhoneView {

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
        return R.layout.activity_bind_phone;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            //do something.
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @OnClick({R.id.tv_get, R.id.tv_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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

    public static void startBindPhoneActivity(Context context){
        Intent intent=new Intent(context,BindPhoneActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getCodeFinish() {
        RxHelper.getCode(tvGet);
    }

    @Override
    public void bindPhoneFinish() {
        MainActivity.startMainActivity(this);
        UserInfo.getInstance().setLogin(true);
        EventBus.getDefault().postSticky(new EventModel<>(EventModel.LOGIN));
        AppManager.getAppManager().finishActivity(BootPageActivity.class);
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
}
