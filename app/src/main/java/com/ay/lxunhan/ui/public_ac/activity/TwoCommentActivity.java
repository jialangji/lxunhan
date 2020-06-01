package com.ay.lxunhan.ui.public_ac.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.TwoCommentBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.TwoCommentConytract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.TwoCommentPresenter;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.Utils;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TwoCommentActivity extends BaseActivity<TwoCommentConytract.TwoCommentView, TwoCommentPresenter> implements TwoCommentConytract.TwoCommentView {

    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.iv_v)
    ImageView ivV;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_like_ac)
    ImageView ivLikeAc;
    @BindView(R.id.tv_like_count_ac)
    TextView tvLikeCountAc;
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R.id.tv_comment_reply)
    TextView tvCommentReply;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private String id;
    private int page = 1;
    private boolean isRefresh = true;
    private TwoCommentBean.FirstShowBean bean;
    private List<TwoCommentBean.CommentListBean> commentListBeans = new ArrayList<>();
    private BaseQuickAdapter twoCommentAdapter;
    private int mPostion;


    @Override
    public boolean isKeyboardEnable() {
        return true;
    }

    @Override
    protected void initView() {
        super.initView();
        id = getIntent().getStringExtra("id");
        twoCommentAdapter = new BaseQuickAdapter<TwoCommentBean.CommentListBean, BaseViewHolder>(R.layout.item_two_comment,commentListBeans) {
            @Override
            protected void convert(BaseViewHolder helper, TwoCommentBean.CommentListBean item) {
                GlideUtil.loadCircleImgForHead(TwoCommentActivity.this,helper.getView(R.id.iv_header),item.getAvatar());
                helper.setImageResource(R.id.iv_like, item.getIs_like() ? R.drawable.ic_like_hand : R.drawable.ic_unlike_hand);
                helper.setText(R.id.tv_name,item.getNickname());
                helper.setText(R.id.tv_comment,item.getContent());
                helper.setText(R.id.tv_like_count,item.getLike_count()+"");
                helper.setText(R.id.tv_time,item.getTimeText());
                helper.setGone(R.id.iv_v,item.getIs_media());
                helper.addOnClickListener(R.id.ll_like);
            }
        };
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(twoCommentAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getTwoComment(id, page);
    }

    @Override
    public TwoCommentPresenter initPresenter() {
        return new TwoCommentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_two_comment;
    }

    @Override
    protected void initListener() {
        super.initListener();
        twoCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_like:

                    mPostion=position;
                    SendCommentModel sendCommentModel = new SendCommentModel(commentListBeans.get(mPostion).getId()+"");
                    presenter.commentLike(sendCommentModel, true);
                    break;
            }
        });
        etComment.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (!TextUtils.isEmpty(StringUtil.getFromEdit(etComment))) {//评论
                    SendCommentModel sendCommentModel = new SendCommentModel(UserInfo.getInstance().getUserId(), id, StringUtil.getFromEdit(etComment));
                    presenter.sendTwoComment(sendCommentModel);
                }
            }
            return true;
        });
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                isRefresh = true;
                page = 1;
                presenter.getTwoComment(id, page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                isRefresh = false;
                page = page + 1;
                presenter.getTwoComment(id, page);
            }
        });
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @OnClick({R.id.rl_finish, R.id.ll_like})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.ll_like:
                if (!Utils.isFastClick()){
                    return;
                }
                SendCommentModel sendCommentModel = new SendCommentModel(id);
                presenter.commentLike(sendCommentModel, false);
                break;
        }
    }

    @Override
    public void getTwoCommentFinish(TwoCommentBean twoCommentBean) {
        bean = twoCommentBean.getFirst_show();
        GlideUtil.loadCircleImgForHead(this, ivHeader, bean.getAvatar());
        ivV.setVisibility(bean.getIs_media() ? View.VISIBLE : View.GONE);
        tvTime.setText(bean.getTimeText());
        tvName.setText(bean.getNickname());
        tvComment.setText(bean.getContent());
        ivLikeAc.setImageDrawable(getResources().getDrawable(bean.getIs_like() ? R.drawable.ic_like_hand : R.drawable.ic_unlike_hand));
        tvLikeCountAc.setText(bean.getLike_count() + "");
        tvCommentCount.setText(bean.getCount() + "");
        tvCommentReply.setText(String.format(StringUtil.getString(R.string.reply_count), bean.getCount()));
        if (isRefresh) {
            commentListBeans.clear();
            commentListBeans.addAll(twoCommentBean.getComment_list());
            swipeRefresh.finishRefreshing();
        } else {
            commentListBeans.addAll(twoCommentBean.getComment_list());
            swipeRefresh.finishLoadmore();
        }
        twoCommentAdapter.setNewData(commentListBeans);
    }

    @Override
    public void addLikeFinish(boolean isTwo) {
        if (isTwo) {
            if (commentListBeans.get(mPostion).getIs_like()) {
                commentListBeans.get(mPostion).setIs_like(0);
                commentListBeans.get(mPostion).setLike_count(commentListBeans.get(mPostion).getLike_count() - 1);
            } else {
                commentListBeans.get(mPostion).setIs_like(1);
                commentListBeans.get(mPostion).setLike_count(commentListBeans.get(mPostion).getLike_count() + 1);
            }
            twoCommentAdapter.notifyDataSetChanged();
        } else {
            if (bean.getIs_like()) {
                bean.setLike_count(bean.getLike_count() - 1);
                bean.setIs_like(0);
            } else {
                bean.setLike_count(bean.getLike_count() + 1);
                bean.setIs_like(1);
            }
            EventBus.getDefault().postSticky(new EventModel<>(EventModel.SENDCOMMENT));

            ivLikeAc.setImageDrawable(getResources().getDrawable(bean.getIs_like() ? R.drawable.ic_like_hand : R.drawable.ic_unlike_hand));
            tvLikeCountAc.setText(bean.getLike_count() + "");
        }
    }

    @Override
    public void sendTwoCommentFinish() {
        EventBus.getDefault().postSticky(new EventModel<>(EventModel.TWOCOMMENTLIKE));
        page = 1;
        etComment.setText("");
        presenter.getTwoComment(id, page);
    }

    public static void startTwoCommentActivity(Context context, String id) {
        Intent intent = new Intent(context, TwoCommentActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
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
