package com.ay.lxunhan.ui.video.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
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
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.VideoDetailBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.VideoDetailContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.VideoDetailPresenter;
import com.ay.lxunhan.ui.public_ac.activity.ComplaintActivity;
import com.ay.lxunhan.ui.public_ac.activity.TwoCommentActivity;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.Utils;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.ShareDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

public class VideoDetailActivity extends BaseActivity<VideoDetailContract.VideoDetailView, VideoDetailPresenter> implements VideoDetailContract.VideoDetailView {

    @BindView(R.id.jzvdStd)
    JzvdStd jzvdStd;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.iv_v)
    ImageView ivV;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_time)
    TextView tvTime;
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
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    private SensorManager sensorManager;
    private Jzvd.JZAutoFullscreenListener jzAutoFullscreenListener;
    private ShareDialog shareDialog;
    private String id;
    private VideoDetailBean videoDetailBean;
    private List<CommentBean> commentBeans = new ArrayList<>();
    private BaseQuickAdapter commentAdapter;
    private int page = 1;
    private int type=2;
    private boolean isRefresh = true;
    private int commentPostion;
    @Override
    public VideoDetailPresenter initPresenter() {
        return new VideoDetailPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        id=getIntent().getStringExtra("id");
        jzvdStd.backButton.setVisibility(View.GONE);
        jzvdStd.batteryLevel.setVisibility(View.GONE);
        jzvdStd.backButton.setVisibility(View.GONE);//返回按钮
        jzvdStd.batteryTimeLayout.setVisibility(View.GONE);//时间和电量
        //用于实现重力感应下切换横竖屏
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        jzAutoFullscreenListener = new Jzvd.JZAutoFullscreenListener();
        JzvdStd.SAVE_PROGRESS=false;
        JzvdStd.setVideoImageDisplayType(JzvdStd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);
        //设置全屏播放
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向\
        commentAdapter = new BaseQuickAdapter<CommentBean, BaseViewHolder>(R.layout.item_comment, commentBeans) {
            @Override
            protected void convert(BaseViewHolder helper, CommentBean item) {
                GlideUtil.loadCircleImgForHead(VideoDetailActivity.this, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
                helper.setText(R.id.tv_comment, item.getContent());
                helper.setGone(R.id.iv_v, item.getIs_media());
                helper.setText(R.id.tv_time, item.getTimeText());
                helper.setText(R.id.tv_like_count, item.getLike_count() + "");
                helper.setText(R.id.tv_comment_count, item.getTwo_arr().getCount() + "");
                helper.setImageResource(R.id.iv_like, item.getIs_like() ? R.drawable.ic_like_hand : R.drawable.ic_unlike_hand);
                helper.setGone(R.id.tv_reply, item.getIs_two());
                TextView tvReplay=helper.getView(R.id.tv_reply);
                String str=item.getTwo_arr().getName()+"等人共"+item.getTwo_arr().getCount()+"条回复>";
                SpannableString span=new SpannableString(str);
                span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_2A6CFF)), 0, item.getTwo_arr().getName().length() , SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_2A6CFF)),item.getTwo_arr().getName().length()+2,str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvReplay.setText(span);
                helper.addOnClickListener(R.id.ll_like);
                helper.addOnClickListener(R.id.ll_comment);
            }
        };
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(commentAdapter);
    }

    public String setTwoCommentColor(String name,int count){
        String str=name+"等人共"+count+"条回复>";
        SpannableString span=new SpannableString(str);
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_ff8b02)), 1, name.length() + 1, SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_2A6CFF)),name.length()+4,str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_detail;
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
                presenter.getOneComment(id, type, page);
                break;

        }
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getVideoDetail( id);
        presenter.getOneComment(id, type, page);
    }

    @Override
    protected void initListener() {
        super.initListener();
        commentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_like:
                    commentPostion = position;
                    SendCommentModel sendCommentModel = new SendCommentModel(String.valueOf(commentBeans.get(commentPostion).getId()));
                    presenter.commentLike(sendCommentModel);
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
                isRefresh = true;
                page = page + 1;
                presenter.getOneComment(String.valueOf(id), type, page);
            }
        });
        etComment.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (!TextUtils.isEmpty(StringUtil.getFromEdit(etComment))) {//评论
                    SendCommentModel sendCommentModel = new SendCommentModel(UserInfo.getInstance().getUserId(), String.valueOf(videoDetailBean.getId()), videoDetailBean.getUid(), type, StringUtil.getFromEdit(etComment));
                    presenter.sendOneComment(sendCommentModel);
                }
            }
            return true;
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

    @OnClick({R.id.rl_finish, R.id.rl_more, R.id.ll_moreLike,R.id.tv_attention})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.rl_more:
                showDialog();
                break;
            case R.id.ll_moreLike:
                SendCommentModel sendCommentModel = new SendCommentModel(videoDetailBean.getId() + "", type);
                presenter.addLike(sendCommentModel);
                break;
            case R.id.tv_attention:
                if (videoDetailBean.getIs_fol() != 2) {
                    presenter.attention(videoDetailBean.getUid());
                }
                break;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(jzAutoFullscreenListener);
        Jzvd.releaseAllVideos();
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
                ComplaintActivity.startComplaintActivity(VideoDetailActivity.this, id, type);

            }

            @Override
            public void collect() {

            }

            @Override
            public void cancel() {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //播放器重力感应
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(jzAutoFullscreenListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        JzvdStd.goOnPlayOnResume();
    }

    public static void startVideoDetailActivity(Context context,String id){
        Intent intent=new Intent(context,VideoDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    public void getVideoDetailFinish(VideoDetailBean videoDetailBean) {
        this.videoDetailBean=videoDetailBean;
        jzvdStd.setUp(videoDetailBean.getVideo(),"", JzvdStd.SCREEN_WINDOW_NORMAL);
        GlideUtil.loadCircleImgForHead(this, ivHeader, videoDetailBean.getAvatar());
        GlideUtil.loadImg(this,jzvdStd.thumbImageView,videoDetailBean.getCover());
        tvName.setText(videoDetailBean.getNickname());
        tvSignature.setText(videoDetailBean.getInto());
        ivSex.setImageDrawable(videoDetailBean.getSex() ? getResources().getDrawable(R.drawable.ic_man) : getResources().getDrawable(R.drawable.ic_woman));
        if (videoDetailBean.getIs_fol() == 2) {
            tvAttention.setVisibility(View.GONE);
        } else {
            tvAttention.setText(videoDetailBean.getIs_fol() == 1 ? StringUtil.getString(R.string.attention_to) : StringUtil.getString(R.string.add_attention));
        }
        tvType.setText(videoDetailBean.getPlate_name());
        tvTime.setText(videoDetailBean.getTimeText());
        tvContent.setText(videoDetailBean.getTitle());
        tvLikeCount.setText(videoDetailBean.getLike_count() + "");
        ivLike.setImageDrawable(videoDetailBean.getIs_like() ? getResources().getDrawable(R.drawable.ic_like_hand) : getResources().getDrawable(R.drawable.ic_unlike_black));

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
    public void sendCommentFinish() {
        EventBus.getDefault().postSticky(new EventModel<>(EventModel.SENDCOMMENT));
        etComment.setText("");
        page = 1;
        presenter.getOneComment(String.valueOf(id), type, page);
    }

    @Override
    public void addLikeFinish() {
        if (videoDetailBean.getIs_like()) {
            videoDetailBean.setIs_like(0);
            videoDetailBean.setLike_count(videoDetailBean.getLike_count() - 1);
        } else {
            videoDetailBean.setIs_like(1);
            videoDetailBean.setLike_count(videoDetailBean.getLike_count() + 1);
        }
        EventBus.getDefault().postSticky(new EventModel<>(EventModel.ARTICLELIKE));
        tvLikeCount.setText(videoDetailBean.getLike_count() + "");
        ivLike.setImageDrawable(videoDetailBean.getIs_like() ? getResources().getDrawable(R.drawable.ic_like_hand) : getResources().getDrawable(R.drawable.ic_unlike_black));
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
    public void attentionFinish() {
        if (videoDetailBean.getIs_fol()==1){
            videoDetailBean.setIs_fol(0);
        }else{
            videoDetailBean.setIs_fol(1);
        }
        tvAttention.setText(videoDetailBean.getIs_fol() == 1 ? StringUtil.getString(R.string.attention_to) : StringUtil.getString(R.string.add_attention));
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