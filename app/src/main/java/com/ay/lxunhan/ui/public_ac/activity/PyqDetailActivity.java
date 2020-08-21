package com.ay.lxunhan.ui.public_ac.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.PyqCommentBean;
import com.ay.lxunhan.bean.PyqDetailBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.PyqDetailContract;
import com.ay.lxunhan.presenter.PyqDetailPresenter;
import com.ay.lxunhan.ui.public_ac.imageviewbig.ImagePreviewActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.GridDividerDecoration;
import com.ay.lxunhan.widget.ReplyDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

public class PyqDetailActivity extends BaseActivity<PyqDetailContract.PyqDetailView, PyqDetailPresenter> implements PyqDetailContract.PyqDetailView {

    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rv_header)
    RecyclerView rvHeader;
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
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.rv_img)
    RecyclerView rvImg;
    @BindView(R.id.rl_img)
    RelativeLayout rlImg;
    @BindView(R.id.rl_like)
    RelativeLayout rlLike;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    private PyqDetailBean pyqDetailBean;
    private List<PyqDetailBean.LikeListBean> likeBeans = new ArrayList<>();
    private List<PyqCommentBean.CommentListBean> pyqCommentBeans = new ArrayList<>();
    private BaseQuickAdapter imgAdapter;
    private List<String> imgList = new ArrayList<>();
    private String Id;
    private int page = 1;
    private boolean isRefresh = true;
    private BaseQuickAdapter headerAdapter;
    private BaseQuickAdapter commentAdapter;
    private ReplyDialog replyDialog;

    @Override
    public PyqDetailPresenter initPresenter() {
        return new PyqDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pyq_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        tvWechat.setVisibility(View.GONE);
        Id = getIntent().getStringExtra("id");
        imgAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_img, imgList) {
            @Override
            protected void convert(BaseViewHolder helper, String itemchild) {
                GlideUtil.loadRoundImg(PyqDetailActivity.this, helper.getView(R.id.iv_img), itemchild, 10);
            }
        };
        rvImg.addItemDecoration(new GridDividerDecoration(this, 10, GridLayoutManager.VERTICAL));
        rvImg.setLayoutManager(new GridLayoutManager(this, 3));
        rvImg.setAdapter(imgAdapter);
        headerAdapter = new BaseQuickAdapter<PyqDetailBean.LikeListBean, BaseViewHolder>(R.layout.item_like_header, likeBeans) {
            @Override
            protected void convert(BaseViewHolder helper, PyqDetailBean.LikeListBean item) {
                GlideUtil.loadCircleImgForHead(PyqDetailActivity.this, helper.getView(R.id.iv_header), item.getAvatar());
            }
        };
        rvHeader.addItemDecoration(new GridDividerDecoration(this, 4, GridLayoutManager.VERTICAL));
        rvHeader.setLayoutManager(new GridLayoutManager(this, 7));
        rvHeader.setAdapter(headerAdapter);

        commentAdapter = new BaseQuickAdapter<PyqCommentBean.CommentListBean, BaseViewHolder>(R.layout.item_pyq_comment, pyqCommentBeans) {
            @Override
            protected void convert(BaseViewHolder helper, PyqCommentBean.CommentListBean item) {
                GlideUtil.loadCircleImgForHead(PyqDetailActivity.this, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getC_nickname());
                helper.setText(R.id.tv_time, item.getTimeText());
                if (item.getFid() == 0) {
                    helper.setText(R.id.tv_comment, item.getContent());
                } else {
                    String str = "回复" + item.getB_nickname() + "：" + item.getContent();
                    SpannableString span = new SpannableString(str);
                    span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_303133)), 2, item.getB_nickname().length() + 2, SPAN_EXCLUSIVE_EXCLUSIVE);
                    helper.setText(R.id.tv_comment, span);
                }
                helper.addOnClickListener(R.id.tv_reply);
                helper.addOnClickListener(R.id.iv_header);
            }
        };
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(commentAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getPyqDetailShow(Id);

    }

    @Override
    protected void initListener() {
        super.initListener();
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                isRefresh = true;
                page = 1;
                presenter.getComment(Id, page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == pyqCommentBeans.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.getComment(Id, page);
                } else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
        headerAdapter.setOnItemClickListener((adapter, view, position) -> FriendDetailActivity.startUserDetailActivity(PyqDetailActivity.this, String.valueOf(likeBeans.get(position).getUid())));
        commentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(PyqDetailActivity.this, String.valueOf(pyqCommentBeans.get(position).getCid()));
                    break;
                case R.id.tv_reply:
                    showReplyDialog2(position);
                    break;
            }
        });
        imgAdapter.setOnItemClickListener((adapter, view, position) -> ImagePreviewActivity.startImageviewnActivity(this, imgList, position));
        etComment.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (isLogin()) {
                    if (!TextUtils.isEmpty(StringUtil.getFromEdit(etComment))) {//评论
                        SendCommentModel sendCommentModel = new SendCommentModel(UserInfo.getInstance().getUserId(), String.valueOf(pyqDetailBean.getCircle_show().getId()), pyqDetailBean.getCircle_show().getUid(), 6, StringUtil.getFromEdit(etComment));
                        presenter.sendOneComment(sendCommentModel);
                    }
                }
            }
            return true;
        });
    }

    public void showReplyDialog2(int position) {
        if (replyDialog == null) {
            replyDialog = new ReplyDialog(this);
            replyDialog.setOnShowListener(dialog -> replyDialog.showKeyboard());
        }
        if (!replyDialog.isShowing()) {
            replyDialog.show();
        }
        replyDialog.setOnReplyClickListenr(() -> {
            if (!TextUtils.isEmpty(replyDialog.getContent())) {
                SendCommentModel sendCommentModel;
                if (pyqCommentBeans.get(position).getFid() == 0) {
                    sendCommentModel = new SendCommentModel(UserInfo.getInstance().getUserId(), String.valueOf(pyqCommentBeans.get(position).getId()), replyDialog.getContent(), String.valueOf(pyqCommentBeans.get(position).getCid()));
                } else {
                    sendCommentModel = new SendCommentModel(UserInfo.getInstance().getUserId(), String.valueOf(pyqCommentBeans.get(position).getFid()), replyDialog.getContent(), String.valueOf(pyqCommentBeans.get(position).getCid()));
                }
                presenter.sendTwoComment(sendCommentModel);
            }

        });


    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.rl_finish, R.id.ll_moreLike, R.id.iv_cover})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.ll_moreLike:
                SendCommentModel sendCommentModel = new SendCommentModel(pyqDetailBean.getCircle_show().getId() + "", 6);
                presenter.addLike(sendCommentModel);
                break;
            case R.id.iv_cover:
                ImagePreviewActivity.startImageviewnActivity(this, imgList, 0);
                break;
        }
    }

    @Override
    public boolean isKeyboardEnable() {
        return true;
    }

    @Override
    public void getPyqDetailFinish(PyqDetailBean detailBean) {
        pyqDetailBean = detailBean;
        GlideUtil.loadCircleImgForHead(this, ivHeader, detailBean.getCircle_show().getAvatar());
        tvName.setText(detailBean.getCircle_show().getNickname());
        tvContent.setText(detailBean.getCircle_show().getTitle());
        tvLikeCount.setText(detailBean.getCircle_show().getLike_count() + "");
        tvSignature.setText(detailBean.getCircle_show().getAutograph());
        ivSex.setImageDrawable(detailBean.getCircle_show().getSex() ? getResources().getDrawable(R.drawable.ic_man) : getResources().getDrawable(R.drawable.ic_woman));
        ivLike.setImageDrawable(detailBean.getCircle_show().getIs_like() ? getResources().getDrawable(R.drawable.ic_like_hand) : getResources().getDrawable(R.drawable.ic_unlike_black));
        tvTime.setText(detailBean.getCircle_show().getTimeText());
        if (detailBean.getCircle_show().getImage_arr() != null || detailBean.getCircle_show().getImage_arr().size() > 0) {
            rlImg.setVisibility(View.VISIBLE);
            imgList.clear();
            imgList.addAll(detailBean.getCircle_show().getImage_arr());
            if (detailBean.getCircle_show().getImage_arr().size() == 1) {
                GlideUtil.loadRoundImg(PyqDetailActivity.this, ivCover, imgList.get(0), 10);
                ivCover.setVisibility(View.VISIBLE);
                rvImg.setVisibility(View.GONE);
            } else {
                ivCover.setVisibility(View.GONE);
                rvImg.setVisibility(View.VISIBLE);
                imgAdapter.notifyDataSetChanged();
            }
        } else {
            rlImg.setVisibility(View.GONE);
        }
        likeBeans.clear();
        likeBeans.addAll(detailBean.getLike_list());
        headerAdapter.notifyDataSetChanged();
        presenter.getComment(Id, page);
    }

    @Override
    public void sendCommentFinish() {
        isRefresh = true;
        etComment.setText("");
        page = 1;
        presenter.getComment(Id, page);
    }

    @Override
    public void addLikeFinish() {
        presenter.getPyqDetailShow(Id);
    }

    @Override
    public void sendTwoCommentFinish() {
        if (replyDialog != null && replyDialog.isShowing()) {
            replyDialog.setContext();
            replyDialog.dismiss();
        }
        isRefresh = true;
        page = 1;
        presenter.getComment(Id, page);
    }

    @Override
    public void getCommentFinish(PyqCommentBean beans) {
        if (pyqDetailBean.getCircle_show().getLike_count() == 0 && (beans == null || beans.getComment_list().size() == 0)) {
            llMore.setVisibility(View.GONE);
        } else {
            llMore.setVisibility(View.VISIBLE);
        }
        if (pyqDetailBean.getCircle_show().getLike_count() == 0) {
            rlLike.setVisibility(View.GONE);
        } else {
            rlLike.setVisibility(View.VISIBLE);
        }
        if (beans.getComment_list().size() == 0) {
            rvComment.setVisibility(View.GONE);
        } else {
            tvCommentCount.setText("("+beans.getCom_list_count()+")");
            rvComment.setVisibility(View.VISIBLE);
        }
        if (isRefresh) {
            pyqCommentBeans.clear();
            swipeRefresh.finishRefreshing();
        } else {
            swipeRefresh.finishLoadmore();
        }
        pyqCommentBeans.addAll(beans.getComment_list());
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }

    public static void startPyqDetailActivity(Context context, String Id) {
        Intent intent = new Intent(context, PyqDetailActivity.class);
        intent.putExtra("id", Id);
        context.startActivity(intent);
    }
}
