package com.ay.lxunhan.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ay.lxunhan.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplyDialog extends Dialog {
    private EditText etContent;
    private Context context;
    private TextView tvReply;
    OnReplyClickListenr onReplyClickListenr;

    public ReplyDialog(Context context) {
        super(context, R.style.ReplyDialog);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply_dialog);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }

    public void setOnReplyClickListenr(OnReplyClickListenr onReplyClickListenr) {
        this.onReplyClickListenr = onReplyClickListenr;
    }

    private void initView() {
        etContent = (EditText) findViewById(R.id.et_content);
        tvReply = (TextView) findViewById(R.id.tv_reply);
        tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReplyClickListenr != null) {
                    onReplyClickListenr.onReplyClick();
                }

            }
        });
    }

    public void setContext() {
        etContent.setText("");
        etContent.setHint(context.getResources().getString(R.string.write_comment));
    }

    public String getContent() {
        return etContent.getText().toString();
    }

    public void showKeyboard() {
        if (etContent != null) {
            //设置可获得焦点
            etContent.setFocusable(true);
            etContent.setFocusableInTouchMode(true);
            //请求获得焦点
            etContent.requestFocus();
            etContent.setFilters(new InputFilter[]{emojiFilter});
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) etContent
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(etContent, 0);
        }
    }

    InputFilter emojiFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                return "";
            }
            return null;
        }
    };


    public interface OnReplyClickListenr {
        void onReplyClick();
    }
}
