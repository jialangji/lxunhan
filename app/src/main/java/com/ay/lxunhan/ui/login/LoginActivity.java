package com.ay.lxunhan.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ay.lxunhan.MainActivity;
import com.ay.lxunhan.R;
import com.ay.lxunhan.base.AppContext;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.LoginBean;
import com.ay.lxunhan.bean.model.PublicModel;
import com.ay.lxunhan.contract.LoginContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.LoginPresenter;
import com.ay.lxunhan.utils.AppManager;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.RxHelper;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.Utils;
import com.netease.nimlib.sdk.RequestCallback;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    private RequestCallback loginRequest;
    private boolean isCode = false;
    private int type = 1;

    private int loginType;
    /**
     * QQ
     */
    private Tencent qqTencent;
    private String qqOpenId;
    private String qqAccess_token;
    private String qqExpires_in;

    /**
     * 新浪微博
     */
    private SsoHandler mSsoHandler;
    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;
    private ProgressDialog mProgressDialog;

    @Override
    protected void initView() {
        super.initView();
//        qqTencent = Tencent.createInstance(Contacts.QQ_APP_ID, this);
//        mSsoHandler = new SsoHandler(this);
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
                if (!isCode) {
                    llCode.setVisibility(View.VISIBLE);
                    etPsw.setVisibility(View.GONE);
                    tvChangeLogin.setText(StringUtil.getString(R.string.psw_login));
                    isCode = true;
                    type = 2;
                } else {
                    llCode.setVisibility(View.GONE);
                    etPsw.setVisibility(View.VISIBLE);
                    tvChangeLogin.setText(StringUtil.getString(R.string.code_login));
                    isCode = false;
                    type = 1;
                }
                break;
            case R.id.tv_code:
                if (!TextUtils.isEmpty(StringUtil.getFromEdit(etPhone))) {
                    if (Utils.isChinaPhoneLegal(StringUtil.getFromEdit(etPhone))) {
                        tvCode.setEnabled(false);
                        presenter.getCode(StringUtil.getFromEdit(etPhone), 2);
                    } else {
                        ToastUtil.makeShortText(this, "请输入正确的手机号");
                    }
                } else {
                    ToastUtil.makeShortText(this, "请输入手机号");
                }
                break;
            case R.id.tv_login:
                if (TextUtils.isEmpty(StringUtil.getFromEdit(etPhone))) {
                    ToastUtil.makeShortText(this, "请输入手机号");
                    return;
                }
                if (!Utils.isChinaPhoneLegal(StringUtil.getFromEdit(etPhone))) {
                    ToastUtil.makeShortText(this, "请输入正确的手机号");
                    return;
                }
                PublicModel publicModel = null;
                switch (type) {
                    case 1:
                        if (TextUtils.isEmpty(StringUtil.getFromEdit(etPsw))) {
                            ToastUtil.makeShortText(this, "请输入密码");
                            return;
                        }
                        publicModel = new PublicModel(StringUtil.getFromEdit(etPhone), StringUtil.getFromEdit(etPsw), "Android", type);
                        break;
                    case 2:
                        if (TextUtils.isEmpty(StringUtil.getFromEdit(etCode))) {
                            ToastUtil.makeShortText(this, "请输入验证码");
                            return;
                        }
                        publicModel = new PublicModel(StringUtil.getFromEdit(etPhone), StringUtil.getFromEdit(etCode), type, "Android");
                        break;
                }
                presenter.login(publicModel);
                break;
            case R.id.tv_forget_psw:
                RegisterActivity.startRegisterActivity(this, 2);
                finish();
                break;
            case R.id.tv_register:
                RegisterActivity.startRegisterActivity(this, 1);
                finish();
                break;
            case R.id.iv_qq:
//                loginType=1;
//                qqTencent.login(this, "all", new LoginUiListener());
                break;
            case R.id.iv_wx:
//                loginType=2;
//                wxLogin();
                break;
            case R.id.iv_wb:
//                loginType=3;
//                loginToSina();
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

        if (loginBean.getIs_perfect()) {
            MainActivity.startMainActivity(LoginActivity.this);
            EventBus.getDefault().postSticky(new EventModel<>(EventModel.LOGIN));
            AppManager.getAppManager().finishActivity(BootPageActivity.class);
            finish();
        } else {
            CompleteInfoActivity.startCompleteInfoActivity(LoginActivity.this);
            finish();
        }
//        loginRequest =  new RequestCallback<LoginInfo>() {
//            @Override
//            public void onSuccess(LoginInfo param) {
//                loginDone();
//                  UserInfo.getInstance().setWyyAccount(param.getAccount());
//        UserInfo.getInstance().setWyyToken(param.getToken());
//            }
//
//            @Override
//            public void onFailed(int code) {
//                loginDone();
//                if (code == 302 || code == 404) {
//                    ToastUtil.makeShortText(AppContext.instance,
//                            "账号或者密码错误");
//                } else {
//                    ToastUtil.makeShortText(AppContext.instance,
//                            "登录失败: " + code);
//                }
//            }
//
//            @Override
//            public void onException(Throwable exception) {
//                loginDone();
//            }
//        };
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

    private void loginToSina() {
        //授权方式有三种，第一种对客户端授权 第二种对Web短授权，第三种结合前两中方式
        mSsoHandler.authorize(new SelfWbAuthListener());
    }

    public void wxLogin() {
        if (!AppContext.mWxApi.isWXAppInstalled()) {
            ToastUtil.makeShortText(this, "您还未安装微信客户端");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_login_hlx";
        AppContext.mWxApi.sendReq(req);
    }

    private void loginDone() {
        loginRequest = null;
    }

    private class LoginUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            //获取openid
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            try {
                UserInfo.getInstance().setLoginType(Contacts.QQLOGIN);
                qqOpenId = ((JSONObject) response).getString("openid");
                qqAccess_token = ((JSONObject) response).getString("access_token");
                qqExpires_in = ((JSONObject) response).getString("expires_in");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {

            Log.e("QQ_LOGIN", "错误码：" + uiError.errorCode + " 错误信息：" + uiError.errorMessage);
        }

        @Override
        public void onCancel() {

        }
    }

    private class SelfWbAuthListener implements com.sina.weibo.sdk.auth.WbAuthListener {
        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            runOnUiThread(() -> {
                mAccessToken = token;
                if (mAccessToken.isSessionValid()) {
                    // 显示 Token
//                        updateTokenView(false);
                    // 保存 Token 到 SharedPreferences
                    UserInfo.getInstance().setLoginType(Contacts.WBLOGIN);
//                        AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
                    Log.e("WB", "授权成功");
                    //获取个人资料
                    //https://api.weibo.com/2/users/show.json
                    getWbUserInfo(mAccessToken.getToken(), mAccessToken.getUid());
                }
            });
        }

        @Override
        public void cancel() {
            ToastUtil.makeShortText(LoginActivity.this, "用户取消");
        }

        @Override
        public void onFailure(WbConnectErrorMessage errorMessage) {
            Toast.makeText(LoginActivity.this, errorMessage.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void getWbUserInfo(String access, String openid) {
        createProgressDialog();
        String getUserInfoUrl = "https://api.weibo.com/2/users/show.json/access_token=" + access + "&uid=" + openid;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(getUserInfoUrl)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("fan12", "onFailure: ");
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo = response.body().string();
//                Log.d("Wx", "onResponse: " + responseInfo);
//                SharedPreferences.Editor editor= getSharedPreferences("userInfo", MODE_PRIVATE).edit();
//                editor.putString("responseInfo", responseInfo);
//                editor.commit();
                finish();
                mProgressDialog.dismiss();
            }
        });
    }

    private void createProgressDialog() {
        mContext = this;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//转盘
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("提示");
        mProgressDialog.setMessage("登录中，请稍后");
        mProgressDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (loginType) {
            case 1:
                Tencent.onActivityResultData(requestCode, resultCode, data, new LoginUiListener());
                if (requestCode == Constants.REQUEST_API) {
                    if (resultCode == Constants.REQUEST_LOGIN) {
                        Tencent.handleResultData(data, new LoginUiListener());
                    }
                }
                break;
            case 2:
                if (mSsoHandler != null) {
                    mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
                }
                break;
        }
    }
}
