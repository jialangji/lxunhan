package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.model.LbModel;
import com.ay.lxunhan.contract.LbExchangeContract;
import com.ay.lxunhan.presenter.LbExchangePresenter;
import com.ay.lxunhan.utils.BigDecimalUtils;
import com.ay.lxunhan.utils.ButtonUtils;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ExChangeActivity extends BaseActivity<LbExchangeContract.LbExchangeView, LbExchangePresenter> implements LbExchangeContract.LbExchangeView {

    @BindView(R.id.tv_lb)
    TextView tvLb;
    @BindView(R.id.tv_cash)
    TextView tvCash;
    @BindView(R.id.et_count)
    EditText etCount;
    private LbBean mLbBean;

    @Override
    public LbExchangePresenter initPresenter() {
        return new LbExchangePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ex_change;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    public static void startExChangeActivity(Context context) {
        Intent intent = new Intent(context, ExChangeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getShow();
    }

    @OnClick({R.id.rl_finish, R.id.tv_exchange_record, R.id.tv_exchange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_exchange_record:
                BillActivity.startBillActivity(this,1);
                break;
            case R.id.tv_exchange:
                if (TextUtils.isEmpty(StringUtil.getFromEdit(etCount))){
                    return;
                }
                if (ButtonUtils.isFastDoubleClick(R.id.tv_exchange)){
                    return;
                }
                if (BigDecimalUtils.compareIsBig(mLbBean.getGold(),StringUtil.getFromEdit(etCount))){
                    LbModel lbModel=new LbModel(StringUtil.getFromEdit(etCount));
                    presenter.exchange(lbModel);
                }else {
                    ToastUtil.makeShortText(this,"兑换数量不能大于持有数量");
                }


                break;
        }
    }

    @Override
    public void getShowFinish(LbBean lbBean) {
        mLbBean=lbBean;
        tvLb.setText(lbBean.getGold());
        tvCash.setText(String.format("约%s元", lbBean.getMoney()));
    }

    @Override
    public void exchangeFinish() {
        presenter.getShow();
        etCount.setText("");
        ToastUtil.makeShortText(this,"兑换成功");
    }

    @Override
    public void exChangeErro() {

    }
}
