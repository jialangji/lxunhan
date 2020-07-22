package com.ay.lxunhan.wyyim.liveuser;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.AppContext;
import com.ay.lxunhan.bean.GiftBean;
import com.ay.lxunhan.bean.WyGiftBean;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;

import java.util.LinkedList;
import java.util.Queue;

public class GiftAnimation {
    private final int SHOW_HIDE_ANIMATOR_DURATION = 500;
    private final int ANIMATION_STAY_DURATION = 1000;

    private boolean upFree = true;
    private boolean downFree = true;

    private ViewGroup upView;
    private ViewGroup downView;
    private AnimatorSet upAnimatorSet;
    private AnimatorSet downAnimatorSet;

    private Queue<ChatRoomMessage> cache = new LinkedList<>();

    public GiftAnimation(ViewGroup downView, ViewGroup upView) {
        this.upView = upView;
        this.downView = downView;
        this.upAnimatorSet = buildAnimationSet(upView);
        this.downAnimatorSet = buildAnimationSet(downView);
    }

    // 收到礼物，等待显示动画
    public void showGiftAnimation(final ChatRoomMessage message) {
        cache.add(message);
        checkAndStart();
    }

    private void checkAndStart() {
        if (!upFree && !downFree) {
            return;
        }

        if (downFree) {
            startAnimation(downView, downAnimatorSet);
        } else {
            startAnimation(upView, upAnimatorSet);
        }
    }

    // 开始礼物动画
    private void startAnimation(ViewGroup target, AnimatorSet set) {
        ChatRoomMessage message = cache.poll();
        if (message == null) {
            return;
        }

        // 更新状态
        onAnimationStart(target);

        // 更新礼物视图
        updateView(message, target);

        // 执行动画组
        target.setAlpha(1f);
        target.setVisibility(View.VISIBLE);
        set.start();
    }

    private void onAnimationStart(final ViewGroup target) {
        if (target == upView) {
            upFree = false;
        } else if (target == downView) {
            downFree = false;
        }
    }

    private void onAnimationCompleted(final ViewGroup target) {
        if (target == upView) {
            upFree = true;
        } else if (target == downView) {
            downFree = true;
        }

        checkAndStart();
    }

    /**
     * ********************* 属性动画 *********************
     */

    private AnimatorSet buildAnimationSet(final ViewGroup target) {
        ObjectAnimator show = buildShowAnimator(target, SHOW_HIDE_ANIMATOR_DURATION);
        ObjectAnimator hide = buildHideAnimator(target, SHOW_HIDE_ANIMATOR_DURATION);
        hide.setStartDelay(ANIMATION_STAY_DURATION);

        AnimatorSet set = new AnimatorSet();
        set.setTarget(target);
        set.playSequentially(show, hide);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onAnimationCompleted(target);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        return set;
    }

    private ObjectAnimator buildShowAnimator(final View target, long duration) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", -300.0F, 0.0F)
                .setDuration(duration);
        translationX.setInterpolator(new OvershootInterpolator());

        return translationX;
    }

    private ObjectAnimator buildHideAnimator(final View target, long duration) {
        return ObjectAnimator.ofFloat(target, View.ALPHA, 1f, 0.0f)
                .setDuration(duration);
    }

    /**
     * ********************* 更新礼物信息 *********************
     */

    private void updateView(final ChatRoomMessage message, ViewGroup root) {
        Log.e("GIFT",message.getAttachStr());

        Gson gson=new Gson();
        WyGiftBean wyGiftBean= gson.fromJson(message.getAttachStr(), WyGiftBean.class);

        TextView audienceNameText = root.findViewById(R.id.audience_name);
        ImageView ivheadee = root.findViewById(R.id.iv_header);
        TextView giftText = root.findViewById(R.id.gift_name);
        TextView tvCount = root.findViewById(R.id.tv_count);
        ImageView giftImage = root.findViewById(R.id.gift_image);
        GiftBean bean =wyGiftBean.getData().getPresent();
        // gift name & image

        audienceNameText.setText(bean.getUserName());
        GlideUtil.loadCircleImgForHead(AppContext.instance,ivheadee,bean.getHeader());
        giftText.setText(bean.getName());
        tvCount.setText("x"+bean.getCount());
        GlideUtil.loadRoundImg(AppContext.instance, giftImage, bean.getCover());
    }
}
