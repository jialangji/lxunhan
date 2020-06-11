package com.ay.lxunhan.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.ChannelBean;
import com.ay.lxunhan.contract.ChannelContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.ChannelPresenter;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.GridDividerDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChannelManageActivity extends BaseActivity<ChannelContract.ChannelView, ChannelPresenter> implements ChannelContract.ChannelView {

    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.rv_mychannel)
    RecyclerView rvMychannel;
    @BindView(R.id.rv_addchannel)
    RecyclerView rvAddchannel;
    private List<ChannelBean.MyChanneBean> myChannelList = new ArrayList<>();
    private List<ChannelBean.MyChanneBean> noChannelList = new ArrayList<>();
    private BaseQuickAdapter myAdapter;
    private BaseQuickAdapter normalAdapter;
    private int mPosition;
    private boolean isEdit = true;
    private boolean isVideo;

    @Override
    public ChannelPresenter initPresenter() {
        return new ChannelPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        isVideo = getIntent().getBooleanExtra("isVideo", false);
        myAdapter = new BaseQuickAdapter<ChannelBean.MyChanneBean, BaseViewHolder>(R.layout.item_channel_normal, myChannelList) {
            @Override
            protected void convert(BaseViewHolder helper, ChannelBean.MyChanneBean item) {

                helper.setGone(R.id.iv_icon,!isVideo);
                GlideUtil.loadImg(ChannelManageActivity.this, helper.getView(R.id.iv_icon), item.getIcon());
                helper.setText(R.id.tv_name, item.getName());
                helper.setGone(R.id.iv_del, item.isShowIcon());
                helper.addOnClickListener(R.id.iv_del);
            }
        };
        rvMychannel.setLayoutManager(new GridLayoutManager(this, 3));
        rvMychannel.addItemDecoration(new GridDividerDecoration(ChannelManageActivity.this, 12, GridLayoutManager.VERTICAL));
        rvMychannel.setAdapter(myAdapter);
        normalAdapter = new BaseQuickAdapter<ChannelBean.MyChanneBean, BaseViewHolder>(R.layout.item_channel_my, noChannelList) {
            @Override
            protected void convert(BaseViewHolder helper, ChannelBean.MyChanneBean item) {
                helper.setGone(R.id.iv_icon,!isVideo);
                GlideUtil.loadImg(ChannelManageActivity.this, helper.getView(R.id.iv_icon), item.getIcon());
                helper.setText(R.id.tv_name, item.getName());
                helper.setGone(R.id.iv_add, item.isShowIcon());
                helper.addOnClickListener(R.id.iv_add);
            }
        };
        rvAddchannel.setLayoutManager(new GridLayoutManager(this, 3));
        rvAddchannel.addItemDecoration(new GridDividerDecoration(ChannelManageActivity.this, 12, GridLayoutManager.VERTICAL));
        rvAddchannel.setAdapter(normalAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        myAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.iv_del:
                    mPosition = position;
                    if (isVideo) {
                        presenter.deleteVideoChannel(String.valueOf(myChannelList.get(position).getId()));

                    } else {
                        presenter.deleteChannel(String.valueOf(myChannelList.get(position).getId()));
                    }

                    break;
            }
        });
        normalAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.iv_add:
                    mPosition = position;
                    if (isVideo) {
                        presenter.addVideoChannel(String.valueOf(noChannelList.get(position).getId()));

                    } else {
                        presenter.addChannel(String.valueOf(noChannelList.get(position).getId()));
                    }
                    break;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        if (isVideo) {
            presenter.channelVideoManagement();
        } else {
            presenter.channelManagement();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_channel_manage;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @OnClick({R.id.rl_finish, R.id.tv_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_edit:
                if (isEdit) {
                    isEdit = false;
                    tvEdit.setText("完成");
                    for (ChannelBean.MyChanneBean myChanneBean : myChannelList) {
                        if (myChanneBean.getId() == 1 || myChanneBean.getId() == 2
                                || myChanneBean.getId() == 3 || myChanneBean.getId() == 4) {
                            myChanneBean.setShowIcon(false);
                        } else {
                            myChanneBean.setShowIcon(true);
                        }
                    }
                    for (ChannelBean.MyChanneBean myChanneBean : noChannelList) {
                        myChanneBean.setShowIcon(true);
                    }
                    myAdapter.notifyDataSetChanged();
                    normalAdapter.notifyDataSetChanged();
                } else {
                    isEdit = true;
                    tvEdit.setText(StringUtil.getString(R.string.edit));
                    if (isVideo) {
                        presenter.channelVideoManagement();
                    } else {
                        presenter.channelManagement();
                    }
                    EventBus.getDefault().postSticky(new EventModel<>(EventModel.CHANGECHANNEL));
                }
                break;
        }
    }

    @Override
    public void channelManageFinish(ChannelBean channelBean) {
        myChannelList.clear();
        noChannelList.clear();
        for (ChannelBean.MyChanneBean myChanneBean : channelBean.getMy_channe()) {
            if (myChanneBean.getId() != 5) {
                myChannelList.add(myChanneBean);
            }
        }
        noChannelList.addAll(channelBean.getNo_channe());
        myAdapter.setNewData(myChannelList);
        normalAdapter.setNewData(noChannelList);
    }

    @Override
    public void addChannelFinish() {
        myChannelList.add(noChannelList.get(mPosition));
        noChannelList.remove(mPosition);
        myAdapter.notifyDataSetChanged();
        normalAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteChannelFinish() {
        noChannelList.add(myChannelList.get(mPosition));
        myChannelList.remove(mPosition);
        myAdapter.notifyDataSetChanged();
        normalAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }

    public static void stratChannelManageActivity(Context context, boolean isVideo) {
        Intent intent = new Intent(context, ChannelManageActivity.class);
        intent.putExtra("isVideo", isVideo);
        context.startActivity(intent);
    }
}
