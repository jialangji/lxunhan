package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawActivity extends BaseActivity {

    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.check_alipay)
    CheckBox checkAlipay;
    @BindView(R.id.check_wx)
    CheckBox checkWx;

    @Override
    public BasePresenter initPresenter() {
        return null;
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
                break;
            case R.id.ll_alipay:
                if (!checkAlipay.isChecked()) {
                    checkAlipay.setChecked(true);
                    checkWx.setChecked(false);
                }
                break;
            case R.id.ll_wx:
                if (!checkWx.isChecked()) {
                    checkWx.setChecked(true);
                    checkAlipay.setChecked(false);
                }
                break;
            case R.id.tv_withdraw:
                break;
        }
    }
    public static void startWithdrawActivity(Context context){
        Intent intent=new Intent(context,WithDrawActivity.class);
        context.startActivity(intent);
    }
}
