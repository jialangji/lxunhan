package com.ay.lxunhan.adapter;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.bean.MultiItemBaseBean;
import com.ay.lxunhan.bean.PyqBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.ui.public_ac.imageviewbig.ImagePreviewActivity;
import com.ay.lxunhan.utils.DisplayUtil;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.Utils;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.GridDividerDecoration;
import com.ay.lxunhan.widget.MyImageSpan;
import com.ay.lxunhan.widget.TagTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.jzvd.JzvdStd;

import static android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;

public class PublicAdapterUtil {

    public static BaseQuickAdapter getAdapter(List<MultiItemBaseBean> datas, Context context) {
        return new MultipleItemQuickAdapter<MultiItemBaseBean>(datas) {
            private BaseQuickAdapter imgAdapter;
            private BaseQuickAdapter baseQuickAdapter;
            @Override
            protected void addItemTypes() {
                super.addItemTypes();
                addItemType(0, R.layout.item_home_list);
                addItemType(1, R.layout.item_home_list2);
                addItemType(2, R.layout.item_home_list3);
                addItemType(3, R.layout.item_home_list_ask);
                addItemType(4, R.layout.item_home_list_quiz);
            }

            @Override
            protected void convert(BaseViewHolder helper, MultiItemBaseBean item) {
                super.convert(helper, item);
                GlideUtil.loadCircleImgForHead(context, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
                if (item.getIs_fol() == 2) {
                    helper.setGone(R.id.tv_attention, false);
                } else {
                    helper.setGone(R.id.tv_attention, true);
                    helper.setText(R.id.tv_attention, item.getIs_fol() != 2 && item.getIs_fol() == 1 ? StringUtil.getString(R.string.attention_to) : StringUtil.getString(R.string.add_attention));
                }

                helper.setText(R.id.tv_signature, item.getInto());
                helper.setImageResource(R.id.iv_sex, item.getSex() ? R.drawable.ic_man : R.drawable.ic_woman);
                helper.setImageResource(R.id.iv_like, item.getIs_like() ? R.drawable.ic_like_hand : R.drawable.ic_unlike_hand);
                helper.setImageResource(R.id.iv_comment, R.drawable.ic_comment_normal);
                helper.setText(R.id.tv_comment_count, item.getComment_count() + "");
                helper.setText(R.id.tv_like_count, item.getLike_count() + "");
                helper.setText(R.id.tv_time, item.getTimeText());
                helper.addOnClickListener(R.id.tv_attention);
                helper.addOnClickListener(R.id.ll_linear);
                helper.addOnClickListener(R.id.ll_like);
                switch (item.getItemType()) {
                    case 0:
                        helper.setText(R.id.tv_content, item.getTitle());
                        helper.setText(R.id.tv_type, item.getPlate_name());
                        break;
                    case 1:
                        helper.setText(R.id.tv_content, item.getTitle());
                        GlideUtil.loadRoundImg(context, helper.getView(R.id.iv_comment_img), item.getCover().get(0));
                        helper.setText(R.id.tv_type, item.getPlate_name());
                        break;
                    case 2:
                        helper.setText(R.id.tv_content, item.getTitle());
                        helper.setText(R.id.tv_type, item.getPlate_name());
                        RecyclerView rvImg = helper.getView(R.id.rv_img);
                        imgAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_img, item.getCover()) {
                            @Override
                            protected void convert(BaseViewHolder helper, String itemchild) {
                                GlideUtil.loadRoundImg(context, helper.getView(R.id.iv_img), itemchild);
                            }
                        };
                        rvImg.addItemDecoration(new GridDividerDecoration(context, 10, GridLayoutManager.VERTICAL));

                        rvImg.setLayoutManager(new GridLayoutManager(context, 3));
                        rvImg.setAdapter(imgAdapter);
                        break;
                    case 3:
                        TextView tvTagAsk = helper.getView(R.id.tv_content);
                        tvTagAsk.setText(setSpan(context, item.getBounty_gold(), item.getTitle()));
                        break;
                    case 4:
                        TagTextView tvTagquiz = helper.getView(R.id.tv_content);
                        helper.setText(R.id.tv_quiz_title, item.getDesc());
                        tvTagquiz.setContentAndTag(R.layout.item_lable_quiz, item.getTitle(), "投票", R.color.color_49b114);
                        RecyclerView rvQuiz = helper.getView(R.id.rv_quiz);
                        helper.setText(R.id.tv_quiz, item.getIs_pate() ? StringUtil.getString(R.string.quiz_to) : StringUtil.getString(R.string.quiz));
                        baseQuickAdapter = new BaseQuickAdapter<MultiItemBaseBean.OptionListBean, BaseViewHolder>(R.layout.item_quiz, item.getOption_list()) {
                            @Override
                            protected void convert(BaseViewHolder helperChild, MultiItemBaseBean.OptionListBean itemChild) {
                                TextView tvSelect = helperChild.getView(R.id.tv_select);
                                tvSelect.setText(String.format("%d.%s", (helperChild.getAdapterPosition() + 1), itemChild.getContent()));
                                ProgressBar progressBar = helperChild.getView(R.id.progressBarSmall);
                                RelativeLayout relativeLayout = helperChild.getView(R.id.rl_select);
                                if (item.getIs_pate()) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    helperChild.setText(R.id.tv_quiz_num, Utils.num(Long.parseLong(itemChild.getCount())));
                                    if (itemChild.getIs_selected()) {
                                        tvSelect.setTextColor(context.getResources().getColor(R.color.color_fc5a8e));
                                        helperChild.setTextColor(R.id.tv_select, context.getResources().getColor(R.color.color_fc5a8e));
                                        relativeLayout.setBackground(context.getResources().getDrawable(R.drawable.shape_radius_pink_line10));
                                        if (itemChild.getBfb() == 100) {
                                            progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_bg_pink));
                                        } else {
                                            progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_bg_pink_half));
                                        }
                                    } else {
                                        tvSelect.setTextColor(context.getResources().getColor(R.color.color_303133));
                                        helperChild.setTextColor(R.id.tv_select, context.getResources().getColor(R.color.color_303133));
                                        relativeLayout.setBackground(context.getResources().getDrawable(R.drawable.shape_grayb2_line));
                                        if (itemChild.getBfb() == 100) {
                                            progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_bg_gray));
                                        } else {
                                            progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_bg_gray_half));
                                        }
                                    }
                                    int progress = itemChild.getBfb();
                                    progressBar.setMax(100);
                                    progressBar.setProgress(progress);
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    if (itemChild.isUserIsSelect()) {
                                        tvSelect.setTextColor(context.getResources().getColor(R.color.white));
                                        relativeLayout.setBackground(context.getResources().getDrawable(R.drawable.shape_radius_pink_10));
                                    } else {
                                        tvSelect.setTextColor(context.getResources().getColor(R.color.color_303133));
                                        relativeLayout.setBackground(context.getResources().getDrawable(R.drawable.shape_grayb2_line));
                                    }
                                }
                                helperChild.addOnClickListener(R.id.rl_select);
                            }
                        };
                        baseQuickAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                            switch (view.getId()) {
                                case R.id.rl_select:
                                    if (!item.getIs_pate()) {
                                        for (int i = 0; i < item.getOption_list().size(); i++) {
                                            if (i == position) {
                                                item.getOption_list().get(position).setUserIsSelect(true);
                                            } else {
                                                item.getOption_list().get(position).setUserIsSelect(false);
                                            }
                                        }
                                    }
                                    baseQuickAdapter.notifyDataSetChanged();
                                    break;
                            }

                        });
                        rvQuiz.setLayoutManager(new LinearLayoutManager(context));
                        rvQuiz.setAdapter(baseQuickAdapter);
                        helper.addOnClickListener(R.id.tv_quiz);
                        break;
                }
            }
        };
    }

    public static BaseQuickAdapter getVideoAdapter(List<VideoBean> videoBeans, Context context) {
        return new MultipleItemQuickAdapter<VideoBean>(videoBeans) {
            @Override
            protected void addItemTypes() {
                super.addItemTypes();
                addItemType(2, R.layout.item_video_list);
                addItemType(5, R.layout.item_video_small_list);

            }

            @Override
            protected void convert(BaseViewHolder helper, VideoBean item) {
                super.convert(helper, item);
                GlideUtil.loadCircleImgForHead(context, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
                helper.addOnClickListener(R.id.ll_line);
                helper.addOnClickListener(R.id.rl_more);
                switch (item.getItemType()) {
                    case 2:
                        JzvdStd jzvdStd = helper.getView(R.id.jzvdStd);
                        jzvdStd.titleTextView.setTextColor(context.getResources().getColor(R.color.white));
                        jzvdStd.titleTextView.setTextSize(DisplayUtil.px2dip(context,46));
                        jzvdStd.backButton.setVisibility(View.GONE);//返回按钮
                        jzvdStd.batteryTimeLayout.setVisibility(View.GONE);//时间和电量
                        JzvdStd.SAVE_PROGRESS=false;
                        JzvdStd.setVideoImageDisplayType(JzvdStd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);
                        //设置全屏播放
                        JzvdStd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
                        JzvdStd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向
                        GlideUtil.loadRoundTop(context,jzvdStd.thumbImageView,item.getCover());
                        jzvdStd.setUp(item.getVideo(), item.getTitle(), JzvdStd.SCREEN_WINDOW_NORMAL);
                        helper.setImageResource(R.id.iv_like, item.getIs_like() ? R.drawable.ic_like_hand : R.drawable.ic_unlike_hand);
                        helper.setText(R.id.tv_comment_count, item.getComment_count() + "");
                        helper.setText(R.id.tv_like_count, item.getLike_count() + "");
                        helper.addOnClickListener(R.id.tv_attention);
                        if (item.getIs_fol() == 2) {
                            helper.setGone(R.id.tv_attention,false );
                        } else {
                            helper.setGone(R.id.tv_attention, true);
                            helper.setTextColor(R.id.tv_attention, context.getResources().getColor(item.getIs_fol() != 2 && item.getIs_fol() == 1 ? R.color.color_b2 : R.color.color_fc5a8e));
                            helper.setText(R.id.tv_attention, item.getIs_fol() != 2 && item.getIs_fol() == 1 ? StringUtil.getString(R.string.attention_to) : StringUtil.getString(R.string.add_attention));
                        }
                        break;
                    case 5:
                        GlideUtil.loadRoundTop(context, helper.getView(R.id.iv_video_cover), item.getCover());
                        break;
                }
            }
        };
    }

    public static BaseQuickAdapter getPyqAdpater(List<PyqBean> pyqBeans,Context context){
        return new MultipleItemQuickAdapter<PyqBean>(pyqBeans){

            private BaseQuickAdapter imgAdapter;

            @Override
            protected void addItemTypes() {
                super.addItemTypes();
                addItemType(0, R.layout.item_pyq_no_img);
                addItemType(1, R.layout.item_pyq_one_img);
                addItemType(2, R.layout.item_pyq_more_img);
            }

            @Override
            protected void convert(BaseViewHolder helper, PyqBean item) {
                super.convert(helper, item);
                GlideUtil.loadCircleImgForHead(context,helper.getView(R.id.iv_header),item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
                helper.setText(R.id.tv_signature, item.getAutograph());
                helper.setImageResource(R.id.iv_sex, item.getSex() ? R.drawable.ic_man : R.drawable.ic_woman);
                helper.setImageResource(R.id.iv_like, item.getIs_like() ? R.drawable.ic_like_hand : R.drawable.ic_unlike_hand);
                helper.setImageResource(R.id.iv_comment, R.drawable.ic_comment_normal);
                helper.setText(R.id.tv_comment_count, item.getComment_count() + "");
                helper.setText(R.id.tv_like_count, item.getLike_count() + "");
                helper.setText(R.id.tv_time, item.getTimeText());
                helper.addOnClickListener(R.id.ll_line);
                switch (helper.getItemViewType()) {
                    case 1:
                        GlideUtil.loadRoundImg(context,helper.getView(R.id.iv_cover),item.getImage_arr().get(0),10);
                        helper.getView(R.id.iv_cover).setOnClickListener(v -> ImagePreviewActivity.startImageviewnActivity(context,item.getImage_arr(),0));
                        break;
                    case 2:
                        RecyclerView rvImg = helper.getView(R.id.rv_img);
                        imgAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_img, item.getImage_arr()) {
                            @Override
                            protected void convert(BaseViewHolder helper, String itemchild) {
                                GlideUtil.loadRoundImg(context, helper.getView(R.id.iv_img), itemchild);
                            }
                        };
                        rvImg.addItemDecoration(new GridDividerDecoration(context, 10, GridLayoutManager.VERTICAL));
                        rvImg.setLayoutManager(new GridLayoutManager(context, 3));
                        rvImg.setAdapter(imgAdapter);
                        imgAdapter.setOnItemClickListener((adapter, view, position) -> ImagePreviewActivity.startImageviewnActivity(context,item.getImage_arr(),position));
                        break;
                }
            }
        };
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
}
