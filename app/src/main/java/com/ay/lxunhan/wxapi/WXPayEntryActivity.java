package com.ay.lxunhan.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ay.lxunhan.ui.public_ac.activity.PayResultActivity;
import com.ay.lxunhan.utils.Contacts;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Author:高天宇
 * Date:2019/7/23
 */
public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Contacts.WX_APP_ID,false);
        api.handleIntent(getIntent(),this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String str = resp.errStr;
            int i = resp.errCode;
            switch (i){
                case 0:
                    PayResultActivity.startPayResultActivity(this,1);
                    finish();
                    break;
                case -1:
                    PayResultActivity.startPayResultActivity(this,2);
                    finish();
                    break;
                case -2:
                    PayResultActivity.startPayResultActivity(this,3);
                    finish();
                    break;
            }


        }
    }
}
