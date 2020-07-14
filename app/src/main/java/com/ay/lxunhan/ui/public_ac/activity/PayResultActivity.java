package com.ay.lxunhan.ui.public_ac.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.ui.my.activity.PayActivity;
import com.ay.lxunhan.utils.AppManager;

import butterknife.BindView;
import butterknife.OnClick;

public class PayResultActivity extends BaseActivity {

    @BindView(R.id.iv_pay)
    ImageView ivPay;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_result;
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
    protected void initView() {
        super.initView();
        int payType=getIntent().getIntExtra("type",-1);
        switch (payType){
            case 1:
                ivPay.setImageResource(R.drawable.ic_pay_success);
                break;
            case 2:
                ivPay.setImageResource(R.drawable.ic_pay_error);
                break;
            case 3:
                ivPay.setImageResource(R.drawable.ic_pay_cancel);
                break;
        }
    }

    public static void startPayResultActivity(Context context,int type){
        Intent intent=new Intent(context,PayResultActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            //do something.
            AppManager.getAppManager().finishActivity(PayActivity.class);
            finish();
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @OnClick(R.id.rl_finish)
    public void onViewClicked() {
        AppManager.getAppManager().finishActivity(PayActivity.class);
        finish();
    }
}
