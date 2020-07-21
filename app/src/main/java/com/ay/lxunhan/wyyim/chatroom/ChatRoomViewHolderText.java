package com.ay.lxunhan.wyyim.chatroom;

import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.utils.MoonUtil;
import com.netease.LSMediaCapture.util.NimUIKit;
import com.netease.LSMediaCapture.util.sys.ScreenUtil;

public class ChatRoomViewHolderText extends MsgViewHolderText {

    @Override
    protected boolean isShowBubble() {
        return false;
    }

    @Override
    protected boolean isShowHeadImage() {
        return false;
    }


    @Override
    protected void bindContentView() {
        TextView bodyTextView = findViewById(R.id.nim_message_item_text_body);
        bodyTextView.setTextColor(bodyTextView.getContext().getResources().getColor(R.color.color_chat_text));
        layoutDirection();
        MoonUtil.identifyFaceExpression(NimUIKit.getContext(), bodyTextView, getDisplayText(), ImageSpan.ALIGN_BOTTOM);
        bodyTextView.setMovementMethod(LinkMovementMethod.getInstance());
        bodyTextView.setOnLongClickListener(longClickListener);
        bodyTextView.setOnClickListener(onClickListener);
    }

    private void layoutDirection() {
        TextView bodyTextView = findViewById(R.id.nim_message_item_text_body);
        bodyTextView.setPadding(ScreenUtil.dip2px(6), 0, 0, 0);
    }
}
