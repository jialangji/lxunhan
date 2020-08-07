package com.ay.lxunhan.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.HomeDetailBean;
import com.ay.lxunhan.bean.HomeQuizDetailBean;
import com.ay.lxunhan.bean.model.AcceptModel;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.HomeDetailContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.HomeDetailPresenter;
import com.ay.lxunhan.ui.public_ac.activity.ComplaintActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.DisplayUtil;
import com.ay.lxunhan.utils.ShareUtils;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.MyImageSpan;
import com.ay.lxunhan.widget.ShareDialog;
import com.ay.lxunhan.widget.ShareImgDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

public class HomeAskDetailActivity extends BaseActivity<HomeDetailContract.HomeDetailView, HomeDetailPresenter> implements HomeDetailContract.HomeDetailView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.webview)
    TextView webview;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.tv_like_count)
    TextView tvLikeCount;
    private BaseQuickAdapter commentAdapter;
    private int page = 1;
    private int type;
    private int id;
    private boolean isRefresh = true;
    private List<CommentBean> commentBeans = new ArrayList<>();
    private HomeDetailBean homeDetailBean;
    private int commentPostion;
    private ShareDialog shareDialog;
    private ShareImgDialog shareImgDialog;

    @Override
    public HomeDetailPresenter initPresenter() {
        return new HomeDetailPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        type = getIntent().getIntExtra("type", 0);
        id = getIntent().getIntExtra("id", 0);
        etComment.setHint(getResources().getString(R.string.iam_answer));
        commentAdapter = new BaseQuickAdapter<CommentBean, BaseViewHolder>(R.layout.item_comment_ask, commentBeans) {
            @Override
            protected void convert(BaseViewHolder helper, CommentBean item) {
                GlideUtil.loadCircleImgForHead(HomeAskDetailActivity.this, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
                helper.setText(R.id.tv_signature, item.getContent());
                helper.setGone(R.id.iv_v, item.getIs_media());
                helper.setText(R.id.tv_time, item.getTimeText());
                helper.setText(R.id.tv_like_count, item.getLike_count() + "");
                helper.setImageResource(R.id.iv_like, item.getIs_like() ? R.drawable.ic_like_hand : R.drawable.ic_unlike_hand);
                TextView tvAccept = helper.getView(R.id.tv_accept);
                if (!homeDetailBean.getIs_solve()) {
                    if (homeDetailBean.getIs_fow() == 2) {
                        tvAccept.setVisibility(View.VISIBLE);
                    } else {
                        tvAccept.setVisibility(View.GONE);
                    }
                } else {
                    if (item.getIs_adoption()) {
                        tvAccept.setVisibility(View.VISIBLE);
                        tvAccept.setText("已采纳");
                    } else {
                        tvAccept.setVisibility(View.GONE);
                    }

                }
                helper.addOnClickListener(R.id.iv_header);
                helper.addOnClickListener(R.id.ll_like);
                helper.addOnClickListener(R.id.tv_accept);
            }
        };
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(commentAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_ask_detail;
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.getHomeDetail(type, id);
    }

    @Override
    public boolean isUserEvent() {
        return true;
    }

    @Override
    protected void getStickyEvent(Object eventModel) {
        super.getStickyEvent(eventModel);
        EventModel model = (EventModel) eventModel;
        switch (model.getMessageType()) {
            case EventModel.TWOCOMMENTLIKE:
                page = 1;
                presenter.getOneComment(String.valueOf(id), type, page);
                break;

        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        commentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_like:
                    if (isLogin()) {
                        commentPostion = position;
                        SendCommentModel sendCommentModel = new SendCommentModel(String.valueOf(commentBeans.get(commentPostion).getId()));
                        presenter.commentLike(sendCommentModel);
                    }

                    break;
                case R.id.tv_accept:
                    if (isLogin()) {
                        if (!homeDetailBean.getIs_solve()) {
                            AcceptModel acceptModel = new AcceptModel(homeDetailBean.getId(), commentBeans.get(position).getId());
                            presenter.accept(acceptModel);
                        }
                    }

                    break;
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(this, commentBeans.get(position).getUid());
                    break;
            }
        });
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                isRefresh = true;
                page = 1;
                presenter.getOneComment(String.valueOf(id), type, page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == commentBeans.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.getOneComment(String.valueOf(id), type, page);
                } else {
                    swipeRefresh.finishLoadmore();
                }

            }
        });

        etComment.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (isLogin()) {
                    if (!TextUtils.isEmpty(StringUtil.getFromEdit(etComment))) {//评论
                        SendCommentModel sendCommentModel = new SendCommentModel(UserInfo.getInstance().getUserId(), String.valueOf(homeDetailBean.getId()), homeDetailBean.getUid(), type, StringUtil.getFromEdit(etComment));
                        presenter.sendOneComment(sendCommentModel);
                    }
                }
            }
            return true;
        });
    }

    @Override
    public boolean isKeyboardEnable() {
        return true;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @OnClick({R.id.rl_finish, R.id.ll_moreLike, R.id.tv_wechat, R.id.rl_more, R.id.tv_attention})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.rl_more:
                if (isLogin()) {
                    showDialog();
                }
                break;
            case R.id.ll_moreLike:
                if (isLogin()) {
                    SendCommentModel sendCommentModel = new SendCommentModel(homeDetailBean.getId() + "", type);
                    presenter.addLike(sendCommentModel);
                }
                break;
            case R.id.tv_wechat:
                ShareUtils.shareToWx(HomeAskDetailActivity.this, homeDetailBean.getShare_url());
                break;
            case R.id.tv_attention:
                if (isLogin()) {
                    if (homeDetailBean.getIs_fow() != 2) {
                        presenter.attention(homeDetailBean.getUid());
                    }
                }
                break;
        }
    }

    public void showDialog() {
        if (shareDialog == null) {
            shareDialog = new ShareDialog(this, R.style.selectPicDialogstyle);
        }
        shareDialog.show();
        shareDialog.setItemClickListener(new ShareDialog.ItemClickListener() {
            @Override
            public void shareFriends() {

            }

            @Override
            public void shareQQ() {
                presenter.share(3, String.valueOf(homeDetailBean.getId()));
                ShareUtils.shareToQQ(HomeAskDetailActivity.this,homeDetailBean.getShare_url());
            }

            @Override
            public void shareQQRoom() {
                presenter.share(3, String.valueOf(homeDetailBean.getId()));
                ShareUtils.shareToQQRoom(HomeAskDetailActivity.this,homeDetailBean.getShare_url());

            }

            @Override
            public void shareWx() {
                presenter.share(3, String.valueOf(homeDetailBean.getId()));
                ShareUtils.shareToWx(HomeAskDetailActivity.this, homeDetailBean.getShare_url());
            }

            @Override
            public void shareWxPyq() {
                presenter.share(3, String.valueOf(homeDetailBean.getId()));
                ShareUtils.shareToWxPyq(HomeAskDetailActivity.this, homeDetailBean.getShare_url());
            }

            @Override
            public void shareWb() {

            }

            @Override
            public void shareImg() {
                showImg(homeDetailBean.getPiiic_url());
            }

            @Override
            public void copyUrl() {
                StringUtil.copyString(homeDetailBean.getShare_url());
            }

            @Override
            public void complaint() {
                ComplaintActivity.startComplaintActivity(HomeAskDetailActivity.this, String.valueOf(homeDetailBean.getId()), type);

            }

            @Override
            public void collect() {
                presenter.addCollect(String.valueOf(homeDetailBean.getId()), type);
            }

            @Override
            public void cancel() {

            }
        });
    }


    public void showImg(String url) {
        if (shareImgDialog == null) {
            shareImgDialog = new ShareImgDialog(this, R.style.selectPicDialogstyle, url);
        }
        shareImgDialog.show();
        shareImgDialog.setItemClickListener(new ShareImgDialog.ItemClickListener() {
            @Override
            public void shareQQ(String bitmap) {
                presenter.share(3, String.valueOf(homeDetailBean.getId()));
                ShareUtils.shareToQQImg(HomeAskDetailActivity.this,bitmap);

            }

            @Override
            public void shareWx() {
                presenter.share(3, String.valueOf(homeDetailBean.getId()));
            }

            @Override
            public void shareWb(Bitmap bitmap) {

            }

        });
    }

    @Override
    public void getHomeDetailFinish(HomeDetailBean homeDetailBean) {
        this.homeDetailBean = homeDetailBean;
        presenter.getOneComment(String.valueOf(id), type, page);
        tvTitle.setText(setSpan(this, String.valueOf(homeDetailBean.getBounty_gold()),homeDetailBean.getTitle()));
        GlideUtil.loadCircleImgForHead(this, ivHeader, homeDetailBean.getAvatar());
        tvName.setText(homeDetailBean.getNickname());
        tvSignature.setText(homeDetailBean.getInto());
        ivSex.setImageDrawable(homeDetailBean.getSex() ? getResources().getDrawable(R.drawable.ic_man) : getResources().getDrawable(R.drawable.ic_woman));
        if (homeDetailBean.getIs_fow() == 2) {
            tvAttention.setVisibility(View.GONE);
        } else {
            tvAttention.setText(homeDetailBean.getIs_fow() == 1 ? StringUtil.getString(R.string.attention_to) : StringUtil.getString(R.string.add_attention));
        }
        tvTime.setText(homeDetailBean.getTimeText());
        webview.setText(homeDetailBean.getContent());
        tvLikeCount.setText(homeDetailBean.getLike_count() + "");
        ivLike.setImageDrawable(homeDetailBean.getIs_like() ? getResources().getDrawable(R.drawable.ic_like_hand) : getResources().getDrawable(R.drawable.ic_unlike_black));
    }
    private static SpannableString setSpan(Context context, String price, String title) {
        String str = "0" + price + "  " + title;
        SpannableString spstr = new SpannableString(str);
        spstr.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_ff8b02)), 1, price.length() + 1, SPAN_EXCLUSIVE_EXCLUSIVE);
        spstr.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, 10)), 1, price.length() + 1, SPAN_EXCLUSIVE_EXCLUSIVE);
        spstr.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_161824)), price.length() + 2, str.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        spstr.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, 14)), price.length() + 2, str.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        Drawable rightDrawable = context.getResources().getDrawable(R.drawable.ic_small_coin);
        rightDrawable.setBounds(0, 0, rightDrawable.getIntrinsicWidth(), rightDrawable.getIntrinsicHeight());
        spstr.setSpan(new MyImageSpan(rightDrawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spstr;
    }

    @Override
    public void getHomeDetailQuizFinish(HomeQuizDetailBean homeQuizDetailBean) {

    }

    @Override
    public void getOneCommentFinsh(List<CommentBean> list) {
        if (isRefresh) {
            commentBeans.clear();
            commentBeans.addAll(list);
            commentAdapter.setNewData(commentBeans);
            swipeRefresh.finishRefreshing();
        } else {
            commentBeans.addAll(list);
            commentAdapter.setNewData(commentBeans);
            swipeRefresh.finishLoadmore();
        }
    }

    @Override
    public void addCollectFinish() {
        ToastUtil.makeShortText(this, "收藏成功");
    }

    @Override
    public void sendCommentFinish() {
        EventBus.getDefault().postSticky(new EventModel<>(EventModel.SENDCOMMENT));
        etComment.setText("");
        page = 1;
        presenter.getOneComment(String.valueOf(id), type, page);
    }

    @Override
    public void addLikeFinish() {
        if (homeDetailBean.getIs_like()) {
            homeDetailBean.setIs_like(0);
            homeDetailBean.setLike_count(homeDetailBean.getLike_count() - 1);
        } else {
            homeDetailBean.setIs_like(1);
            homeDetailBean.setLike_count(homeDetailBean.getLike_count() + 1);
        }
        EventBus.getDefault().postSticky(new EventModel<>(EventModel.ARTICLELIKE));
        tvLikeCount.setText(homeDetailBean.getLike_count() + "");
        ivLike.setImageDrawable(homeDetailBean.getIs_like() ? getResources().getDrawable(R.drawable.ic_like_hand) : getResources().getDrawable(R.drawable.ic_unlike_black));
    }

    @Override
    public void commentLikeFinish() {
        if (commentBeans.get(commentPostion).getIs_like()) {
            commentBeans.get(commentPostion).setIs_like(0);
            commentBeans.get(commentPostion).setLike_count(commentBeans.get(commentPostion).getLike_count() - 1);
        } else {
            commentBeans.get(commentPostion).setIs_like(1);
            commentBeans.get(commentPostion).setLike_count(commentBeans.get(commentPostion).getLike_count() + 1);
        }
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void quziFinish() {

    }

    @Override
    public void acceptFinish() {
        presenter.getHomeDetail(type, id);
        page = 1;
        presenter.getOneComment(String.valueOf(id), type, page);
    }

    @Override
    public void attentionFinish() {
        if (homeDetailBean.getIs_fow() == 1) {
            homeDetailBean.setIs_fow(0);
        } else {
            homeDetailBean.setIs_fow(1);
        }
        tvAttention.setText(homeDetailBean.getIs_fow() == 1 ? StringUtil.getString(R.string.attention_to) : StringUtil.getString(R.string.add_attention));

    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }


    public static void startHomeAskDetailActivity(Context context, int type, int id) {
        Intent intent = new Intent(context, HomeAskDetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }
}
