package com.ay.lxunhan.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private static String TAG = "MicroMsg.WXEntryActivity";
    private IWXAPI api;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Contacts.WX_APP_ID, false);
        Log.e(TAG, "解决微信问题...这里设置true还是false...都没解决问题");
        try {
            boolean result = api.handleIntent(getIntent(), this);
            if(!result){
                ToastUtil.makeShortText(this,"分享成功");
                finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "这个有点恶心...你要报什么错 e = " + e.toString());
            e.printStackTrace();
        }
        Log.e(TAG, "onCreate  结束 ");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
 
    @Override
    public void onReq(BaseReq req) {
//        finish();
    }
 
    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        Log.e(TAG, "进入    WXEntryActivity   onResp   我们要的就是进入这里...以便我们做监听啊 ");
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
//			result = R.string.errcode_success;
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        UserInfo.getInstance().setWxCode(code);
                        UserInfo.getInstance().setWxLogin(true);
                        UserInfo.getInstance().setLoginType(Contacts.WXLOGIN);
                        finish();
//                        getAccessToken(code);
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        ToastUtil.makeShortText(this,"微信分享成功");
                        finish();
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
//			result = R.string.errcode_unsupported;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType())
                    ToastUtil.makeShortText(this,"分享失败");
                else ToastUtil.makeShortText(this,"登录失败");
                break;
            default:
//			result = R.string.errcode_unknown;
                break;
        }
 
//        finish();
    }
}