package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.InviteBean;
import com.ay.lxunhan.contract.InviteContract;
import com.ay.lxunhan.presenter.InvitePresenter;
import com.ay.lxunhan.utils.ShareUtils;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.widget.ShareFriendDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class InviteFriendActivity extends BaseActivity<InviteContract.InviteView, InvitePresenter> implements InviteContract.InviteView {


    @BindView(R.id.tv_lb)
    TextView tvLb;
    @BindView(R.id.et_code)
    EditText etCode;
    private ShareFriendDialog shareDialog;
    private String code;

    @Override
    protected void initData() {
        super.initData();
        presenter.getInviteCoin(1);
    }

    @Override
    public InvitePresenter initPresenter() {
        return new InvitePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }


    @OnClick({R.id.rl_finish, R.id.tv_invite_list, R.id.tv_invite, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_invite_list:
                InviteListActivity.startInviteListActivity(this);
                break;
            case R.id.tv_invite:
                if(code!=null){
                    String url="http://sanyserver.51appdevelop.com/#/yaoqing?yaoqingma="+code;
                    showDialog(url);
                }
                break;
            case R.id.tv_sure:
                if (TextUtils.isEmpty(StringUtil.getFromEdit(etCode))){
                    ToastUtil.makeShortText(this,"邀请码不能为空");
                    return;
                }
                presenter.sendInviteCode(StringUtil.getFromEdit(etCode));
                break;
        }
    }


    private void showDialog(String url){
        if (shareDialog == null) {
            shareDialog = new ShareFriendDialog(this, R.style.selectPicDialogstyle);
        }
        shareDialog.show();
        shareDialog.setItemClickListener(new ShareFriendDialog.ItemClickListener() {
            @Override
            public void shareQQ() {
                ShareUtils.shareToQQ(InviteFriendActivity.this,url);

            }

            @Override
            public void shareWx() {
                ShareUtils.shareToWx(InviteFriendActivity.this,url);
            }
        });
    }

    @Override
    public void sendInviteCodeFinish() {
        etCode.setText("");
        ToastUtil.makeShortText(this,"您已被邀请成功");
    }

    @Override
    public void getInviteCoinFinish(InviteBean bean) {
        tvLb.setText(bean.getGold().getValue());
        code=bean.getUser().getInvite_code();

    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }

    public static void startInviteFriendActivity(Context context){
        Intent intent=new Intent(context,InviteFriendActivity.class);
        context.startActivity(intent);
    }
}
