package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.AttentionBean;
import com.ay.lxunhan.contract.AttentionContract;
import com.ay.lxunhan.presenter.AttentionPresenter;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AttentionActivity extends BaseActivity<AttentionContract.AttentionView, AttentionPresenter> implements AttentionContract.AttentionView {

    @BindView(R.id.rl_attention)
    RecyclerView rlAttention;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<AttentionBean> attentionBeans = new ArrayList<>();
    private BaseQuickAdapter attentionAdapter;
    private int page=1;
    private boolean isRefresh=true;
    private int mPosition;
    private String uzid;
    private boolean isMine;


    @Override
    protected void initView() {
        super.initView();
        uzid=getIntent().getStringExtra("uzid");
        isMine=getIntent().getBooleanExtra("isMine",false);
        attentionAdapter = new BaseQuickAdapter<AttentionBean, BaseViewHolder>(R.layout.item_attention, attentionBeans) {
            @Override
            protected void convert(BaseViewHolder helper, AttentionBean item) {
                GlideUtil.loadCircleImgForHead(AttentionActivity.this,helper.getView(R.id.iv_header),item.getAvatar());
                helper.setImageResource(R.id.iv_sex, item.getSex() ? R.drawable.ic_man : R.drawable.ic_woman);
                helper.setText(R.id.tv_name,item.getNickname());
                helper.setText(R.id.tv_fans_count,"粉丝 "+item.getFcount());
                TextView tvAttention=helper.getView(R.id.tv_attention);
                helper.setVisible(R.id.tv_attention,item.getIs_fol()!=3);
                tvAttention.setTextColor(getResources().getColor(item.getIs_fol()==2?R.color.white:R.color.color_fc5a8e));
                tvAttention.setBackground(getResources().getDrawable(item.getIs_fol()==2?R.drawable.shape_radius_pink_10:R.drawable.shape_radius_pink_line10));
                if (item.getIs_fol()==2){
                    helper.setText(R.id.tv_attention, StringUtil.getString(R.string.attention_each_other));
                }else if (item.getIs_fol()==1){
                    helper.setText(R.id.tv_attention, StringUtil.getString(R.string.attention_to));
                }else {
                    helper.setText(R.id.tv_attention, StringUtil.getString(R.string.add_attention));
                }
                helper.addOnClickListener(R.id.tv_attention);
                helper.addOnClickListener(R.id.iv_header);
            }
        };
        rlAttention.setLayoutManager(new LinearLayoutManager(this));
        rlAttention.setAdapter(attentionAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        attentionAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_attention:
                    mPosition=position;
                    presenter.attention(attentionBeans.get(position).getUid());
                    break;
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(AttentionActivity.this, attentionBeans.get(position).getUid());
                    break;
            }
        });
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                isRefresh=true;
                page=1;
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page* Contacts.LIMIT==attentionBeans.size()){
                    isRefresh=false;
                    page=page+1;
                    getData();
                }else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    public void getData(){
        if (isMine){
            presenter.getAttention(page);
        }else{
            presenter.getMediaAttention(uzid,page);
        }
    }

    @Override
    public AttentionPresenter initPresenter() {
        return new AttentionPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attention;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick(R.id.rl_finish)
    public void onViewClicked() {
        finish();
    }

    public static void startAttentionActivity(Context context,String uzid,boolean isMine) {
        Intent intent = new Intent(context, AttentionActivity.class);
        intent.putExtra("uzid",uzid);
        intent.putExtra("isMine",isMine);
        context.startActivity(intent);
    }

    @Override
    public void getAttentionFinish(List<AttentionBean> list) {
        if (isRefresh){
            swipeRefresh.finishRefreshing();
            attentionBeans.clear();
        }else {
            swipeRefresh.finishLoadmore();
        }
        attentionBeans.addAll(list);
        attentionAdapter.notifyDataSetChanged();
    }

    @Override
    public void attention() {
        if (attentionBeans.get(mPosition).getIs_fol()==0){
            attentionBeans.get(mPosition).setIs_fol(1);
        }else  {
            attentionBeans.get(mPosition).setIs_fol(0);
        }
        attentionAdapter.notifyDataSetChanged();
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
