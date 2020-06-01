package com.ay.lxunhan.ui.login;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ay.lxunhan.MainActivity;
import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.LoginBean;
import com.ay.lxunhan.bean.model.PublicModel;
import com.ay.lxunhan.contract.LoginContract;
import com.ay.lxunhan.presenter.LoginPresenter;
import com.ay.lxunhan.utils.AppManager;
import com.ay.lxunhan.utils.RxHelper;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginContract.LoginView, LoginPresenter> implements LoginContract.LoginView {

    @BindView(R.id.tv_change_login)
    TextView tvChangeLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.et_psw)
    EditText etPsw;

    private boolean isCode=false;
    private int type=1;

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_change_login, R.id.tv_code, R.id.tv_login, R.id.tv_forget_psw, R.id.tv_register, R.id.iv_qq, R.id.iv_wx, R.id.iv_wb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_change_login:
                if (!isCode){
                    llCode.setVisibility(View.VISIBLE);
                    etPsw.setVisibility(View.GONE);
                    tvChangeLogin.setText(StringUtil.getString(R.string.psw_login));
                    isCode=true;
                    type=2;
                }else {
                    llCode.setVisibility(View.GONE);
                    etPsw.setVisibility(View.VISIBLE);
                    tvChangeLogin.setText(StringUtil.getString(R.string.code_login));
                    isCode=false;
                    type=1;
                }
                break;
            case R.id.tv_code:
                if (!TextUtils.isEmpty(StringUtil.getFromEdit(etPhone))){
                    if (Utils.isChinaPhoneLegal(StringUtil.getFromEdit(etPhone))){
                        tvCode.setEnabled(false);
                        presenter.getCode(StringUtil.getFromEdit(etPhone),2);
                    }else {
                        ToastUtil.makeShortText(this,"请输入正确的手机号");
                    }
                }else {
                    ToastUtil.makeShortText(this,"请输入手机号");
                }
                break;
            case R.id.tv_login:
                if (TextUtils.isEmpty(StringUtil.getFromEdit(etPhone))){
                    ToastUtil.makeShortText(this,"请输入手机号");
                    return;
                }
                if (!Utils.isChinaPhoneLegal(StringUtil.getFromEdit(etPhone))){
                    ToastUtil.makeShortText(this,"请输入正确的手机号");
                    return;
                }
                PublicModel publicModel = null;
                switch (type) {
                    case 1:
                        if (TextUtils.isEmpty(StringUtil.getFromEdit(etPsw))){
                            ToastUtil.makeShortText(this,"请输入密码");
                            return;
                        }
                        publicModel=new PublicModel(StringUtil.getFromEdit(etPhone),StringUtil.getFromEdit(etPsw),"Android",type);
                        break;
                    case 2:
                        if (TextUtils.isEmpty(StringUtil.getFromEdit(etCode))){
                            ToastUtil.makeShortText(this,"请输入验证码");
                            return;
                        }
                        publicModel=new PublicModel(StringUtil.getFromEdit(etPhone),StringUtil.getFromEdit(etCode),type,"Android");
                        break;
                }
                presenter.login(publicModel);
                break;
            case R.id.tv_forget_psw:
                RegisterActivity.startRegisterActivity(this,2);
                finish();
                break;
            case R.id.tv_register:
                RegisterActivity.startRegisterActivity(this,1);
                finish();
                break;
            case R.id.iv_qq:
                break;
            case R.id.iv_wx:
                break;
            case R.id.iv_wb:
                break;
        }
    }

    @Override
    public void getCodeFinish() {
        RxHelper.getCode(tvCode);
    }

    @Override
    public void loginFinish(LoginBean loginBean) {
        UserInfo.getInstance().setUserId(loginBean.getUqid());
        UserInfo.getInstance().setLogin(true);
        if (loginBean.getIs_perfect()){
            MainActivity.startMainActivity(this);
            AppManager.getAppManager().finishActivity(BootPageActivity.class);
            finish();
        }else {
            CompleteInfoActivity.startCompleteInfoActivity(this);
            finish();
        }

    }

    @Override
    public void getCodeError() {
        tvCode.setEnabled(true);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
