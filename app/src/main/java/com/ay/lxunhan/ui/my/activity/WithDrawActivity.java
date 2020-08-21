package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.bean.model.WithdrawModel;
import com.ay.lxunhan.contract.WithdrawContract;
import com.ay.lxunhan.presenter.WithDrawPresenter;
import com.ay.lxunhan.utils.BigDecimalUtils;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawActivity extends BaseActivity<WithdrawContract.WithdrawView, WithDrawPresenter> implements WithdrawContract.WithdrawView {

    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.check_alipay)
    CheckBox checkAlipay;
    @BindView(R.id.check_wx)
    CheckBox checkWx;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.ll_account)
    LinearLayout llAccount;
    @BindView(R.id.tv_account_type)
    TextView tvAccountType;
    private String balance;
    private int withdrawType = 0;

    @Override
    public WithDrawPresenter initPresenter() {
        return new WithDrawPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_with_draw;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected void initListener() {
        super.initListener();
        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utils.limitCountInput(etPrice, s, 2);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getUserInfo();
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @OnClick({R.id.rl_finish, R.id.tv_withdraw_all, R.id.ll_alipay, R.id.ll_wx, R.id.tv_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_withdraw_all:
                etPrice.setText(balance);
                break;
            case R.id.ll_alipay:
                if (!checkAlipay.isChecked()) {
                    llAccount.setVisibility(View.VISIBLE);
                    tvAccountType.setText(StringUtil.getString(R.string.alipay_account));
                    withdrawType = 2;
                    checkAlipay.setChecked(true);
                    checkWx.setChecked(false);
                }
                break;
            case R.id.ll_wx:
                if (!checkWx.isChecked()) {
                    llAccount.setVisibility(View.VISIBLE);
                    tvAccountType.setText(StringUtil.getString(R.string.wx_account));
                    withdrawType = 1;
                    checkWx.setChecked(true);
                    checkAlipay.setChecked(false);
                }
                break;
            case R.id.tv_withdraw:
                if (!TextUtils.isEmpty(StringUtil.getFromEdit(etPrice))) {
                    if (BigDecimalUtils.compareIsBig(balance, StringUtil.getFromEdit(etPrice))) {
                        if (checkAlipay.isChecked()||checkWx.isChecked()) {
                            if (!TextUtils.isEmpty(StringUtil.getFromEdit(etAccount))) {
                                WithdrawModel withdrawModel = new WithdrawModel(withdrawType, StringUtil.getFromEdit(etPrice), StringUtil.getFromEdit(etAccount));
                                presenter.withdraw(withdrawModel);
                            } else {
                                ToastUtil.makeShortText(this, "请输入提现账号");
                            }
                        }else {
                            ToastUtil.makeShortText(this, "请选择提现方式");
                        }

                    } else {
                        ToastUtil.makeShortText(this, "余额不足无法提现");
                    }
                } else {
                    ToastUtil.makeShortText(this, "请输入提现金额");
                }

                break;
        }
    }

    public static void startWithdrawActivity(Context context) {
        Intent intent = new Intent(context, WithDrawActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getUserInfoFinish(UserInfoBean userInfoBean) {
        tvBalance.setText(userInfoBean.getBalance());
        balance = userInfoBean.getBalance();
    }

    @Override
    public void withdrawFinish() {
        ToastUtil.makeShortText(this, "提现申请已提交");
        etPrice.setText("");
        checkWx.setChecked(false);
        checkAlipay.setChecked(false);
        llAccount.setVisibility(View.GONE);
        presenter.getUserInfo();
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
