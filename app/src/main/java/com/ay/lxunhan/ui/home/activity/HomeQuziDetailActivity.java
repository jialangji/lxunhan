package com.ay.lxunhan.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.HomeDetailBean;
import com.ay.lxunhan.bean.HomeQuizDetailBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.HomeDetailContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.HomeDetailPresenter;
import com.ay.lxunhan.ui.public_ac.activity.ComplaintActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.ui.public_ac.activity.TwoCommentActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.Utils;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.ShareDialog;
import com.ay.lxunhan.widget.TagTextView;
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

public class HomeQuziDetailActivity extends BaseActivity<HomeDetailContract.HomeDetailView, HomeDetailPresenter> implements HomeDetailContract.HomeDetailView {

    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.tv_tag_text)
    TagTextView tvTagText;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.rv_quiz)
    RecyclerView rvQuiz;
    @BindView(R.id.tv_quiz)
    TextView tvQuiz;
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

    private List<CommentBean> commentBeans = new ArrayList<>();
    private BaseQuickAdapter commentAdapter;
    private int page = 1;
    private int id;
    private boolean isRefresh = true;
    private int type = 4;
    private BaseQuickAdapter baseQuickAdapter;
    private HomeQuizDetailBean homeQuizDetailBean;
    private int commentPostion;
    private ShareDialog shareDialog;

    @Override
    public HomeDetailPresenter initPresenter() {
        return new HomeDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_quzi_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        id = getIntent().getIntExtra("id", 0);
        commentAdapter = new BaseQuickAdapter<CommentBean, BaseViewHolder>(R.layout.item_comment, commentBeans) {
            @Override
            protected void convert(BaseViewHolder helper, CommentBean item) {
                GlideUtil.loadCircleImgForHead(HomeQuziDetailActivity.this, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
                helper.setText(R.id.tv_comment, item.getContent());
                helper.setGone(R.id.iv_v, item.getIs_media());
                helper.setText(R.id.tv_time, item.getTimeText());
                helper.setText(R.id.tv_like_count, item.getLike_count() + "");
                helper.setImageResource(R.id.iv_like, item.getIs_like() ? R.drawable.ic_like_hand : R.drawable.ic_unlike_hand);
                helper.setGone(R.id.tv_reply, item.getIs_two());
                TextView tvReplay=helper.getView(R.id.tv_reply);
                String str=item.getTwo_arr().getName()+"等人共"+item.getTwo_arr().getCount()+"条回复>";
                SpannableString span=new SpannableString(str);
                span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_2A6CFF)), 0, item.getTwo_arr().getName().length() , SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_2A6CFF)),item.getTwo_arr().getName().length()+2,str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvReplay.setText(span);
                helper.setText(R.id.tv_comment_count,  item.getTwo_arr().getCount() + "");
                helper.addOnClickListener(R.id.ll_like);
                helper.addOnClickListener(R.id.ll_comment);
                helper.addOnClickListener(R.id.iv_header);
            }
        };
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(commentAdapter);
    }


    @Override
    protected void initData() {
        super.initData();
        presenter.getHomeQuizDetail(id);
        presenter.getOneComment(String.valueOf(id), type, page);
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @Override
    protected void initListener() {
        super.initListener();
        commentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(this,commentBeans.get(position).getUid());
                    break;
                case R.id.ll_like:
                    if (isLogin()){
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
                }else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
        etComment.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (isLogin()){
                    if (!TextUtils.isEmpty(StringUtil.getFromEdit(etComment))) {//评论
                        SendCommentModel sendCommentModel = new SendCommentModel(UserInfo.getInstance().getUserId(), String.valueOf(homeQuizDetailBean.getId()), homeQuizDetailBean.getUid(), type, StringUtil.getFromEdit(etComment));
                        presenter.sendOneComment(sendCommentModel);
                    }
                }

            }
            return true;
        });
    }

    public static void startHomeQuizDetailActivity(Context context, int id) {
        Intent intent = new Intent(context, HomeQuziDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_finish, R.id.tv_quiz, R.id.ll_moreLike, R.id.tv_wechat, R.id.rl_more,R.id.tv_attention})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_attention:
                if (homeQuizDetailBean.getIs_fow() != 2) {
                    presenter.attention(homeQuizDetailBean.getUid());
                }
                break;
            case R.id.rl_finish:
                finish();
                break;
            case R.id.rl_more:
                if (isLogin()){
                    showDialog();
                }

                break;
            case R.id.tv_quiz:
                if (isLogin()){
                    if (!homeQuizDetailBean.getIs_pate()) {
                        int oid = -1;
                        for (HomeQuizDetailBean.OptionListBean optionListBean : homeQuizDetailBean.getOption_list()) {
                            if (optionListBean.isUserIsSelect()){
                                oid=optionListBean.getId();
                            }
                        }
                        SendCommentModel quizModel = new SendCommentModel(homeQuizDetailBean.getId(),oid );
                        presenter.quiz(quizModel);
                    }
                }

                break;
            case R.id.ll_moreLike:
                if (isLogin()){
                    SendCommentModel sendCommentModel=new SendCommentModel(homeQuizDetailBean.getId()+"",type);
                    presenter.addLike(sendCommentModel);
                }
                break;
            case R.id.tv_wechat:
                break;
        }
    }
    public void showDialog(){
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

            }

            @Override
            public void shareQQRoom() {

            }

            @Override
            public void shareWx() {

            }

            @Override
            public void shareWxPyq() {

            }

            @Override
            public void shareWb() {

            }

            @Override
            public void shareImg() {

            }

            @Override
            public void copyUrl() {

            }

            @Override
            public void complaint() {
                ComplaintActivity.startComplaintActivity(HomeQuziDetailActivity.this, String.valueOf(homeQuizDetailBean.getId()),type);

            }

            @Override
            public void collect() {
                presenter.addCollect(String.valueOf(homeQuizDetailBean.getId()),type);
            }

            @Override
            public void cancel() {

            }
        });
    }

    @Override
    public void getHomeDetailFinish(HomeDetailBean homeDetailBean) {

    }

    @Override
    public boolean isUserEvent() {
        return true;
    }

    @Override
    protected void getStickyEvent(Object eventModel) {
        super.getStickyEvent(eventModel);
        EventModel model= (EventModel) eventModel;
        switch (model.getMessageType()) {
            case EventModel.TWOCOMMENTLIKE:
                page=1;
                presenter.getOneComment(String.valueOf(id),type,page);
                break;

        }
    }

    @Override
    public void getHomeDetailQuizFinish(HomeQuizDetailBean homeQuizDetailBean) {
        this.homeQuizDetailBean = homeQuizDetailBean;
        GlideUtil.loadCircleImgForHead(this, ivHeader, homeQuizDetailBean.getAvatar());
        tvName.setText(homeQuizDetailBean.getNickname());
        tvSignature.setText(homeQuizDetailBean.getInto());
        if (homeQuizDetailBean.getIs_fow() == 2) {
            tvAttention.setVisibility(View.GONE);
        } else {
            tvAttention.setText(homeQuizDetailBean.getIs_fow() == 1 ? StringUtil.getString(R.string.attention_to) : StringUtil.getString(R.string.add_attention));
        }
        tvQuiz.setText(homeQuizDetailBean.getIs_pate() ? StringUtil.getString(R.string.quiz_to) : StringUtil.getString(R.string.quiz));
        tvTagText.setContentAndTag(R.layout.item_lable_quiz, homeQuizDetailBean.getTitle(), "投票", R.color.color_fc5a8e);
        tvTime.setText(homeQuizDetailBean.getTimeText());
        tvDesc.setText(homeQuizDetailBean.getDesc());
        tvLikeCount.setText(String.format("%d", homeQuizDetailBean.getLike_count()));
        ivLike.setImageDrawable(homeQuizDetailBean.getIs_like() ? getResources().getDrawable(R.drawable.ic_like_hand) : getResources().getDrawable(R.drawable.ic_unlike_black));
        baseQuickAdapter = new BaseQuickAdapter<HomeQuizDetailBean.OptionListBean, BaseViewHolder>(R.layout.item_quiz, homeQuizDetailBean.getOption_list()) {
            @Override
            protected void convert(BaseViewHolder helperChild, HomeQuizDetailBean.OptionListBean itemChild) {
                TextView tvSelect = helperChild.getView(R.id.tv_select);
                tvSelect.setText(String.format("%d.%s", (helperChild.getAdapterPosition() + 1), itemChild.getContent()));
                ProgressBar progressBar = helperChild.getView(R.id.progressBarSmall);
                RelativeLayout relativeLayout = helperChild.getView(R.id.rl_select);
                if (homeQuizDetailBean.getIs_pate()) {
                    progressBar.setVisibility(View.VISIBLE);
                    helperChild.setText(R.id.tv_quiz_num, Utils.num(itemChild.getCount()));
                    if (itemChild.getIs_selected()) {
                        tvSelect.setTextColor(getResources().getColor(R.color.color_fc5a8e));
                        helperChild.setTextColor(R.id.tv_select, getResources().getColor(R.color.color_fc5a8e));
                        relativeLayout.setBackground(getResources().getDrawable(R.drawable.shape_radius_pink_line10));
                        if (itemChild.getBfb() == 100) {
                            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bg_pink));
                        } else {
                            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bg_pink_half));
                        }
                    } else {
                        tvSelect.setTextColor(getResources().getColor(R.color.color_303133));
                        helperChild.setTextColor(R.id.tv_select, getResources().getColor(R.color.color_303133));
                        relativeLayout.setBackground(getResources().getDrawable(R.drawable.shape_grayb2_line));
                        if (itemChild.getBfb() == 100) {
                            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bg_gray));
                        } else {
                            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bg_gray_half));
                        }
                    }
                    int progress = itemChild.getBfb();
                    progressBar.setMax(100);
                    progressBar.setProgress(progress);
                } else {
                    progressBar.setVisibility(View.GONE);
                    if (itemChild.isUserIsSelect()) {
                        tvSelect.setTextColor(getResources().getColor(R.color.white));
                        relativeLayout.setBackground(getResources().getDrawable(R.drawable.shape_radius_pink_10));
                    } else {
                        tvSelect.setTextColor(getResources().getColor(R.color.color_303133));
                        relativeLayout.setBackground(getResources().getDrawable(R.drawable.shape_grayb2_line));
                    }
                }
                helperChild.addOnClickListener(R.id.rl_select);
            }
        };
        baseQuickAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.rl_select:
                    if (!homeQuizDetailBean.getIs_pate()) {
                        for (int i = 0; i < homeQuizDetailBean.getOption_list().size(); i++) {
                            if (i == position) {
                                homeQuizDetailBean.getOption_list().get(position).setUserIsSelect(true);
                            } else {
                                homeQuizDetailBean.getOption_list().get(position).setUserIsSelect(false);
                            }
                        }
                    }
                    baseQuickAdapter.notifyDataSetChanged();
                    break;
            }

        });
        rvQuiz.setLayoutManager(new LinearLayoutManager(this));
        rvQuiz.setAdapter(baseQuickAdapter);
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
        ToastUtil.makeShortText(this,"收藏成功");
    }

    @Override
    public void sendCommentFinish() {
        etComment.setText("");
        EventBus.getDefault().postSticky(new EventModel<>(EventModel.SENDCOMMENT));
        page = 1;
        presenter.getOneComment(String.valueOf(id), type, page);
    }

    @Override
    public void addLikeFinish() {
        if (homeQuizDetailBean.getIs_like()) {
            homeQuizDetailBean.setIs_like(0);
            homeQuizDetailBean.setLike_count(homeQuizDetailBean.getLike_count() - 1);
        } else {
            homeQuizDetailBean.setIs_like(1);
            homeQuizDetailBean.setLike_count(homeQuizDetailBean.getLike_count() + 1);
        }
        EventBus.getDefault().postSticky(new EventModel<>(EventModel.ARTICLELIKE));
        tvLikeCount.setText(homeQuizDetailBean.getLike_count() + "");
        ivLike.setImageDrawable(homeQuizDetailBean.getIs_like() ? getResources().getDrawable(R.drawable.ic_like_hand) : getResources().getDrawable(R.drawable.ic_unlike_black));
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
        presenter.getHomeQuizDetail(id);
    }

    @Override
    public void acceptFinish() {

    }

    @Override
    public void attentionFinish() {
        if (homeQuizDetailBean.getIs_fow()==1){
            homeQuizDetailBean.setIs_fow(0);
        }else{
            homeQuizDetailBean.setIs_fow(1);
        }
        tvAttention.setText(homeQuizDetailBean.getIs_fow() == 1 ? StringUtil.getString(R.string.attention_to) : StringUtil.getString(R.string.add_attention));

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
