package com.ay.lxunhan.ui.login;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ay.lxunhan.MainActivity;
import com.ay.lxunhan.R;
import com.ay.lxunhan.base.AppContext;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.LoginBean;
import com.ay.lxunhan.bean.model.PublicModel;
import com.ay.lxunhan.contract.RegisterContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.RegisterPresenter;
import com.ay.lxunhan.utils.AppManager;
import com.ay.lxunhan.utils.JGuangUtil;
import com.ay.lxunhan.utils.RxHelper;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.Utils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterContract.RegisterView, RegisterPresenter> implements RegisterContract.RegisterView {


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.et_sure_psw)
    EditText etSurePsw;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    private int type;

    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            tvRegister.setText(StringUtil.getString(R.string.register));
        } else {
            tvRegister.setText(StringUtil.getString(R.string.sure));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }


    public static void startRegisterActivity(Context context, int type) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_code, R.id.tv_register, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                if (!TextUtils.isEmpty(StringUtil.getFromEdit(etPhone))) {
                    if (Utils.isChinaPhoneLegal(StringUtil.getFromEdit(etPhone))) {
                        tvCode.setEnabled(false);
                        presenter.getCode(StringUtil.getFromEdit(etPhone), 1);
                    } else {
                        ToastUtil.makeShortText(this, "请输入正确的手机号");
                    }
                } else {
                    ToastUtil.makeShortText(this, "请输入手机号");
                }
                break;
            case R.id.tv_register:
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
                if (TextUtils.isEmpty(StringUtil.getFromEdit(etPsw)) || TextUtils.isEmpty(StringUtil.getFromEdit(etSurePsw))) {
                    ToastUtil.makeShortText(this, "密码不能为空");
                    return;
                }
                if (!StringUtil.getFromEdit(etPsw).equals(StringUtil.getFromEdit(etSurePsw))) {
                    ToastUtil.makeShortText(this, "两次密码输入不一致");
                    return;
                }
                PublicModel publicModel = new PublicModel(StringUtil.getFromEdit(etPhone), StringUtil.getFromEdit(etCode),
                        type, StringUtil.getFromEdit(etPsw), StringUtil.getFromEdit(etSurePsw));
                presenter.register(publicModel);
                break;
            case R.id.tv_login:
                LoginActivity.startLoginActivity(this);
                finish();
                break;
        }
    }

    @Override
    public void getCodeFinish() {
        RxHelper.getCode(tvCode);
    }

    @Override
    public void registerFinish(LoginBean loginBean) {
        if (type == 1) {
            wyyxLogin(loginBean);
        } else {
            LoginActivity.startLoginActivity(this);
        }
        finish();
    }

    public void wyyxLogin(LoginBean loginBean) {
        LoginInfo info = new LoginInfo(loginBean.getUqid().toLowerCase(), loginBean.getUqid()); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        JGuangUtil jGuangUtil = new JGuangUtil();
                        jGuangUtil.setJPushAlias(loginBean.getUqid());
                        UserInfo.getInstance().setUserId(loginBean.getUqid());
                        UserInfo.getInstance().setWyyAccount(param.getAccount());
                        UserInfo.getInstance().setWyyToken(param.getToken());
                        if (loginBean.getIs_perfect()) {
                            MainActivity.startMainActivity(RegisterActivity.this);
                            EventBus.getDefault().postSticky(new EventModel<>(EventModel.LOGIN));
                            AppManager.getAppManager().finishActivity(BootPageActivity.class);
                            finish();
                        } else {
                            CompleteInfoActivity.startCompleteInfoActivity(RegisterActivity.this);
                            finish();
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.e("WYYX:", "code:" + code);
                        if (code == 302 || code == 404) {
                            ToastUtil.makeShortText(AppContext.instance,
                                    "账号或者密码错误");
                        } else {
                            ToastUtil.makeShortText(AppContext.instance,
                                    "登录失败: " + code);
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Log.e("WYYX:", exception.getMessage());

                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }

    @Override
    public void getCodeError() {
        tvCode.setEnabled(true);
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
