package com.ay.lxunhan.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.ui.public_ac.activity.TwoCommentActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

public class HomeCommentPopWindow extends PopupWindow {

    private Activity mContext;
    private List<CommentBean> showCommentBean=new ArrayList<>();
    private BaseQuickAdapter commentAdapter;
    private LinearLayout linearLayout;
    private EditText mCommentEdit;
    private TextView tvCount;
    private RecyclerView rvComment;
    private int commentPosition;
    private int page;

    public HomeCommentPopWindow(Activity context,int page) {
        this.mContext = context;
        this.page = page;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View conentView = inflater.inflate(R.layout.popwindow_comment, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.Popupwindow);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        View view=conentView.findViewById(R.id.view_cancel);
        linearLayout = conentView.findViewById(R.id.ll_layout);
        linearLayout.setOnClickListener(v -> dismiss());
        view.setOnClickListener(v -> dismiss());
        mCommentEdit = conentView.findViewById(R.id.et_comment);
        tvCount = conentView.findViewById(R.id.count);
        rvComment = conentView.findViewById(R.id.comment_ex_list);
        initData();
        initListener();
    }

    public void initData() {
        commentAdapter = new BaseQuickAdapter<CommentBean, BaseViewHolder>(R.layout.item_comment, showCommentBean) {
            @Override
            protected void convert(BaseViewHolder helper, CommentBean item) {
                GlideUtil.loadCircleImgForHead(mContext, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
                helper.setText(R.id.tv_comment, item.getContent());
                helper.setGone(R.id.iv_v, item.getIs_media());
                helper.setText(R.id.tv_time, item.getTimeText());
                helper.setText(R.id.tv_like_count, item.getLike_count() + "");
                helper.setImageResource(R.id.iv_like, item.getIs_like() ? R.drawable.ic_like_hand : R.drawable.ic_unlike_hand);
                helper.setGone(R.id.tv_reply, item.getIs_two());
                TextView tvReplay=helper.getView(R.id.tv_reply);
                helper.setGone(R.id.my_comment,false);
                String str=item.getTwo_arr().getName()+"等人共"+item.getTwo_arr().getCount()+"条回复>";
                SpannableString span=new SpannableString(str);
                span.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_2A6CFF)), 0, item.getTwo_arr().getName().length() , SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_2A6CFF)),item.getTwo_arr().getName().length()+2,str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvReplay.setText(span);
                helper.addOnClickListener(R.id.ll_like);
                helper.addOnClickListener(R.id.ll_comment);
                helper.addOnClickListener(R.id.iv_header);
            }
        };
        rvComment.setLayoutManager(new LinearLayoutManager(mContext));
        rvComment.setAdapter(commentAdapter);
    }

    private onSendCommentListener onSendCommentListener;

    public void setOnSendCommentListener(HomeCommentPopWindow.onSendCommentListener onSendCommentListener) {
        this.onSendCommentListener = onSendCommentListener;
    }

    public void setCount(String count){
        tvCount.setText(String.format("%s条评论", count));
    }

    private void initListener() {
        commentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(mContext,showCommentBean.get(position).getUid());
                    break;
                case R.id.ll_like:
                    commentPosition=position;
                    if (onSendCommentListener!=null){
                        onSendCommentListener.commentLike(String.valueOf(showCommentBean.get(position).getId()));
                    }
                    break;
                case R.id.ll_comment:
                    TwoCommentActivity.startTwoCommentActivity(mContext, String.valueOf(showCommentBean.get(position).getId()));
                    break;
            }
        });
        mCommentEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (!TextUtils.isEmpty(StringUtil.getFromEdit(mCommentEdit))) {//评论
                    if (onSendCommentListener != null) {
                        onSendCommentListener.onSend(StringUtil.getFromEdit(mCommentEdit));
                    }
                }
            }
            return true;
        });
        rvComment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {
                    if (page * Contacts.LIMIT == showCommentBean.size()) {
                        if (onSendCommentListener != null) {
                            onSendCommentListener.loadMore();
                        }
                    }
                    //加载更多
                }
            }
        });
        linearLayout.setOnClickListener(v -> hidesoft());
    }

    public void updateData(List<CommentBean> commentBean,int page,boolean isRefrsh) {
        this.page=page;
        if (isRefrsh){
            showCommentBean.clear();
        }
        showCommentBean.addAll(commentBean);
        commentAdapter.notifyDataSetChanged();
    }


    public void updateLike(){
        if (showCommentBean.get(commentPosition).getIs_like()) {
            showCommentBean.get(commentPosition).setIs_like(0);
            showCommentBean.get(commentPosition).setLike_count(showCommentBean.get(commentPosition).getLike_count() - 1);
        } else {
            showCommentBean.get(commentPosition).setIs_like(1);
            showCommentBean.get(commentPosition).setLike_count(showCommentBean.get(commentPosition).getLike_count() + 1);
        }
        commentAdapter.notifyDataSetChanged();
    }
    public void sendSuccess(){
        mCommentEdit.setText("");
    }

    private void hidesoft() {
        InputMethodManager manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (manager.isActive() && mCommentEdit != null) {
            //拿到view的token 不为空
            if (mCommentEdit.getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                manager.hideSoftInputFromWindow(mCommentEdit.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void dismiss() {
        hidesoft();
        super.dismiss();

    }

    public HomeCommentPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeCommentPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HomeCommentPopWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public interface onSendCommentListener {
        void onSend(String comment);

        void loadMore();

        void commentLike(String commentID);
    }
}
