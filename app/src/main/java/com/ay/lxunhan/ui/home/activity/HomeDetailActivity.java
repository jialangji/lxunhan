package com.ay.lxunhan.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.FanyiBean;
import com.ay.lxunhan.bean.HomeDetailBean;
import com.ay.lxunhan.bean.HomeQuizDetailBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.HomeDetailContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.HomeDetailPresenter;
import com.ay.lxunhan.ui.public_ac.activity.ComplaintActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.ui.public_ac.activity.TwoCommentActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.ShareUtils;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.Utils;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.ShareDialog;
import com.ay.lxunhan.widget.ShareImgDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.shuyu.action.web.CustomActionWebView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

public class HomeDetailActivity extends BaseActivity<HomeDetailContract.HomeDetailView, HomeDetailPresenter> implements HomeDetailContract.HomeDetailView {

    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.web)
    CustomActionWebView webview;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.tv_like_count)
    TextView tvLikeCount;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    @BindView(R.id.title_header)
    ImageView titleHeader;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.rl_user)
    LinearLayout rlUser;
    @BindView(R.id.nestedScroll)
    NestedScrollView nestedScroll;
    private List<CommentBean> commentBeans = new ArrayList<>();
    private BaseQuickAdapter commentAdapter;
    private HomeDetailBean homeDetailBean;
    private int page = 1;
    private int type;
    private int id;
    private boolean isRefresh = true;
    private int commentPostion;
    private ShareDialog shareDialog;
    private ShareImgDialog shareImgDialog;
    private String ydStr = "翻译内容无效";


    @Override
    public HomeDetailPresenter initPresenter() {
        return new HomeDetailPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        type = getIntent().getIntExtra("type", 0);
        id = getIntent().getIntExtra("id", 0);
        commentAdapter = new BaseQuickAdapter<CommentBean, BaseViewHolder>(R.layout.item_comment, commentBeans) {
            @Override
            protected void convert(BaseViewHolder helper, CommentBean item) {
                GlideUtil.loadCircleImgForHead(HomeDetailActivity.this, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
                helper.setText(R.id.tv_comment, item.getContent());
                helper.setGone(R.id.iv_v, item.getIs_media());
                helper.setText(R.id.tv_time, item.getTimeText());
                helper.setText(R.id.tv_like_count, item.getLike_count() + "");
                helper.setText(R.id.tv_comment_count, item.getTwo_arr().getCount() + "");
                helper.setImageResource(R.id.iv_like, item.getIs_like() ? R.drawable.ic_like_hand : R.drawable.ic_unlike_hand);
                helper.setGone(R.id.tv_reply, item.getIs_two());
                TextView tvReplay = helper.getView(R.id.tv_reply);
                String str = item.getTwo_arr().getName() + "等人共" + item.getTwo_arr().getCount() + "条回复>";
                SpannableString span = new SpannableString(str);
                span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_2A6CFF)), 0, item.getTwo_arr().getName().length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_2A6CFF)), item.getTwo_arr().getName().length() + 2, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvReplay.setText(span);
                helper.addOnClickListener(R.id.ll_like);
                helper.addOnClickListener(R.id.ll_comment);
                helper.addOnClickListener(R.id.iv_header);
            }
        };
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(commentAdapter);
        List<String> list = new ArrayList<>();
        list.add("翻译");
        webview.setActionList(list);
        webview.linkJSInterface();
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);


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
    protected void initData() {
        super.initData();
        presenter.getHomeDetail(type, id);

    }

    @Override
    protected void initListener() {
        super.initListener();
        nestedScroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            if (scrollY > oldScrollY) {
                if (scrollY-oldScrollY>50){
                    rlUser.setVisibility(View.VISIBLE);
                }
            }
            if (scrollY < oldScrollY) {

            }

            if (scrollY == 0) {
               rlUser.setVisibility(View.GONE);
            }

            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {

            }
        });
        webview.setActionSelectListener((s, s1) -> {
            HttpMethods.getInstance().fanyi(s1).subscribeWith(new BaseSubscriber<FanyiBean>() {
                @Override
                public void onNext(FanyiBean o) {
                    super.onNext(o);
                    if (o.getErrorCode().equals("0")) {
                        runOnUiThread(() -> {
                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeDetailActivity.this);
                            builder.setTitle("翻译结果");
                            builder.setMessage("原文：" + o.getQuery() + "\n 译文；" + o.getTranslation().get(0));
                            builder.show();
                        });
                    } else {
                        ToastUtil.makeShortText(HomeDetailActivity.this, "翻译失败");
                    }


                }
            });
        });
        commentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(this, commentBeans.get(position).getUid());
                    break;
                case R.id.ll_like:
                    if (isLogin()) {
                        commentPostion = position;
                        SendCommentModel sendCommentModel = new SendCommentModel(String.valueOf(commentBeans.get(commentPostion).getId()));
                        presenter.commentLike(sendCommentModel);
                    }
                    break;
                case R.id.ll_comment:
                    TwoCommentActivity.startTwoCommentActivity(this, String.valueOf(commentBeans.get(position).getId()));
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
    protected int getLayoutId() {
        return R.layout.activity_home_detail;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    public boolean isKeyboardEnable() {
        return true;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.rl_finish, R.id.rl_more, R.id.ll_moreLike, R.id.tv_wechat, R.id.tv_attention})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_attention:
                if (homeDetailBean.getIs_fow() != 2) {
                    presenter.attention(homeDetailBean.getUid());
                }

                break;
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
                ShareUtils.shareToWx(this, homeDetailBean.getShare_url());
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
                presenter.share(1, String.valueOf(homeDetailBean.getId()));
                ShareUtils.shareToQQ(HomeDetailActivity.this, homeDetailBean.getShare_url());

            }

            @Override
            public void shareQQRoom() {
                presenter.share(1, String.valueOf(homeDetailBean.getId()));
                ShareUtils.shareToQQRoom(HomeDetailActivity.this, homeDetailBean.getShare_url());

            }

            @Override
            public void shareWx() {
                presenter.share(1, String.valueOf(homeDetailBean.getId()));
                ShareUtils.shareToWx(HomeDetailActivity.this, homeDetailBean.getShare_url());

            }

            @Override
            public void shareWxPyq() {
                presenter.share(1, String.valueOf(homeDetailBean.getId()));
                ShareUtils.shareToWxPyq(HomeDetailActivity.this, homeDetailBean.getShare_url());

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
                ComplaintActivity.startComplaintActivity(HomeDetailActivity.this, String.valueOf(homeDetailBean.getId()), type);

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
                presenter.share(1, String.valueOf(homeDetailBean.getId()));
                ShareUtils.shareToQQImg(HomeDetailActivity.this, bitmap);

            }

            @Override
            public void shareWx() {
                presenter.share(1, String.valueOf(homeDetailBean.getId()));
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
        GlideUtil.loadCircleImgForHead(this, ivHeader, homeDetailBean.getAvatar());
        tvName.setText(homeDetailBean.getNickname());
        GlideUtil.loadCircleImgForHead(this, titleHeader, homeDetailBean.getAvatar());
        titleName.setText(homeDetailBean.getNickname());
        tvSignature.setText(homeDetailBean.getInto());
        tvTitle.setText(homeDetailBean.getTitle());
        ivSex.setImageDrawable(homeDetailBean.getSex() ? getResources().getDrawable(R.drawable.ic_man) : getResources().getDrawable(R.drawable.ic_woman));
        if (homeDetailBean.getIs_fow() == 2) {
            tvAttention.setVisibility(View.GONE);
        } else {
            tvAttention.setText(homeDetailBean.getIs_fow() == 1 ? StringUtil.getString(R.string.attention_to) : StringUtil.getString(R.string.add_attention));
        }
        tvType.setText(homeDetailBean.getPlate_name());
        tvTime.setText(homeDetailBean.getTimeText());
        webview.loadDataWithBaseURL(null, Utils.getHtmlData(homeDetailBean.getContent()), "text/html", "uft-8", null);
        tvLikeCount.setText(homeDetailBean.getLike_count() + "");
        ivLike.setImageDrawable(homeDetailBean.getIs_like() ? getResources().getDrawable(R.drawable.ic_like_hand) : getResources().getDrawable(R.drawable.ic_unlike_black));

    }


    @Override
    public void getHomeDetailQuizFinish(HomeQuizDetailBean homeQuizDetailBean) {
        page = 1;
        presenter.getOneComment(String.valueOf(id), type, page);
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

    @Override
    protected void onDestroy() {
        if (webview != null) {
            webview.clearHistory();
            webview.clearSslPreferences();
            webview.clearFormData();
            webview.clearCache(true);
            webview.clearView();
            ((ViewGroup) webview.getParent()).removeView(webview);
            webview.destroy();
            webview = null;
        }
        super.onDestroy();
    }

    public static void startHomeDetailActivity(Context context, int type, int id) {
        Intent intent = new Intent(context, HomeDetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

}
