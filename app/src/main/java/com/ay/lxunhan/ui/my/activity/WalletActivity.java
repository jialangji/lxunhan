package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletActivity extends BaseActivity {


    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @OnClick({R.id.rl_finish,  R.id.rl_withdraw,R.id.rl_bill})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.rl_withdraw:
                WithDrawActivity.startWithdrawActivity(this);
                break;
            case R.id.rl_bill:
                BillActivity.startBillActivity(this);
                break;
        }
    }

    public static void startWalletActivity(Context context){
        Intent intent=new Intent(context,WalletActivity.class);
        context.startActivity(intent);
    }
}
