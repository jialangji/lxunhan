package com.ay.lxunhan.ui.public_ac.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.HeUserBean;
import com.ay.lxunhan.contract.HeUserInfoContract;
import com.ay.lxunhan.presenter.HeUserInfoPresenter;

import butterknife.BindView;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.public_ac.fragment
 * @date 2020/6/5
 */
public class UserDataFragment extends BaseFragment<HeUserInfoContract.HeUserInfoView, HeUserInfoPresenter> implements HeUserInfoContract.HeUserInfoView {

    @BindView(R.id.tv_app_id)
    TextView tvAppId;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;

    public static UserDataFragment newInstance(String userid) {
        Bundle args = new Bundle();
        UserDataFragment fragment = new UserDataFragment();
        args.putString("id", userid);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        String uzid=getArguments().getString("id");
        presenter.getHeUserInfoData(uzid);
    }

    @Override
    public HeUserInfoPresenter initPresenter() {
        return new HeUserInfoPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_data;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @Override
    public void getHeUserInfoFinish(HeUserBean bean) {

    }

    @Override
    public void getHeUserInfoDataFinish(HeUserBean bean) {
        tvAppId.setText(bean.getSignal());
        tvAddress.setText(bean.getCity());
        tvInfo.setText(bean.getMedia_into());
        tvSex.setText(bean.getSex()?"男":"女");
        tvAge.setText(bean.getAge());
    }

    @Override
    public void pullBlackFinish() {

    }

    @Override
    public void attentionFinish() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
