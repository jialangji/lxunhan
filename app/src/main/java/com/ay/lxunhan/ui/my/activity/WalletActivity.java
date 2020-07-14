package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.contract.MainContract;
import com.ay.lxunhan.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletActivity extends BaseActivity<MainContract.MainView, MainPresenter> implements MainContract.MainView {

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter(this);
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

    @Override
    protected void initView() {
        super.initView();
        presenter.getUserInfo();
    }

    @OnClick({R.id.rl_finish,  R.id.rl_withdraw,R.id.rl_bill,R.id.rl_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.rl_recharge:
                BuyCoinActivity.startBuyCoinActivity(this);
                break;
            case R.id.rl_withdraw:
                WithDrawActivity.startWithdrawActivity(this);
                break;
            case R.id.rl_bill:
                BillActivity.startBillActivity(this,2);
                break;
        }
    }

    public static void startWalletActivity(Context context){
        Intent intent=new Intent(context,WalletActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getUserInfoFinish(UserInfoBean userInfoBean) {
        tvBalance.setText(userInfoBean.getBalance());
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
