package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.InviteBean;
import com.ay.lxunhan.contract.InviteContract;
import com.ay.lxunhan.presenter.InvitePresenter;
import com.ay.lxunhan.utils.ShareUtils;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.widget.ShareFriendDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InviteFriendActivity extends BaseActivity<InviteContract.InviteView, InvitePresenter> implements InviteContract.InviteView {


    @BindView(R.id.tv_lb)
    TextView tvLb;
    @BindView(R.id.rv_invite)
    RecyclerView rvInvite;
    @BindView(R.id.ll_nohave)
    LinearLayout llNohave;
    @BindView(R.id.ll_have)
    LinearLayout llHave;
    @BindView(R.id.tv_more)
    TextView tvMore;
    private ShareFriendDialog shareDialog;
    private String code;
    private BaseQuickAdapter baseQuickAdapter;
    private List<InviteBean.DataBean> dataBeans = new ArrayList<>();

    @Override
    protected void initData() {
        super.initData();
        presenter.getInviteCoin(1);
    }

    @Override
    protected void initView() {
        super.initView();
        baseQuickAdapter = new BaseQuickAdapter<InviteBean.DataBean, BaseViewHolder>(R.layout.item_invite, dataBeans) {
            @Override
            protected void convert(BaseViewHolder helper, InviteBean.DataBean item) {
                helper.setText(R.id.tv_friend_lxh, item.getSignal());
                helper.setText(R.id.tv_invite_time, item.getCreated_date());
                helper.setText(R.id.tv_get, item.getGold() + "乐讯币");
            }
        };
        rvInvite.setLayoutManager(new LinearLayoutManager(this));
        rvInvite.setAdapter(baseQuickAdapter);
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


    @OnClick({R.id.rl_finish, R.id.tv_more, R.id.tv_invite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_more:
                InviteListActivity.startInviteListActivity(this);
                break;
            case R.id.tv_invite:
                if (code != null) {
                    String url = "http://sanyserver.51appdevelop.com/yaoqing?yaoqingma=" + code;
                    showDialog(url);
                }
                break;
        }
    }


    private void showDialog(String url) {
        if (shareDialog == null) {
            shareDialog = new ShareFriendDialog(this, R.style.selectPicDialogstyle);
        }
        shareDialog.show();
        shareDialog.setItemClickListener(new ShareFriendDialog.ItemClickListener() {
            @Override
            public void shareQQ() {
                ShareUtils.shareToQQ(InviteFriendActivity.this, url);

            }

            @Override
            public void shareWx() {
                ShareUtils.shareToWx(InviteFriendActivity.this, url);
            }
        });
    }

    @Override
    public void sendInviteCodeFinish() {
        ToastUtil.makeShortText(this, "您已被邀请成功");
    }

    @Override
    public void getInviteCoinFinish(InviteBean bean) {
        tvLb.setText(bean.getGold().getValue());
        code = bean.getUser().getInvite_code();
        if (bean.getData().size() > 0) {
            llHave.setVisibility(View.VISIBLE);
            llNohave.setVisibility(View.GONE);
        } else {
            llNohave.setVisibility(View.VISIBLE);
            llHave.setVisibility(View.GONE);
            tvMore.setVisibility(View.GONE);
        }
        dataBeans.clear();
        for (int i = 0; i < bean.getData().size(); i++) {
            if (i > 3) {
                dataBeans.add(bean.getData().get(i));
            }
        }
        baseQuickAdapter.notifyDataSetChanged();

    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }

    public static void startInviteFriendActivity(Context context) {
        Intent intent = new Intent(context, InviteFriendActivity.class);
        context.startActivity(intent);
    }

}
