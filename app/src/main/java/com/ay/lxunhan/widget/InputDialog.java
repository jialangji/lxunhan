package com.ay.lxunhan.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.utils.Utils;
import com.blankj.utilcode.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputDialog extends Dialog {
    public static final int INPUTDIALOG_BTNTYPE_EDITUSERNAME = 0;
    public static final int INPUTDIALOG_BTNTYPE_PASSWORD = 1;

    private InputDialogBuilder builder;

    public InputDialog(Context context) {
        super(context);
    }


    public void setBuilder(InputDialogBuilder builder) {
        this.builder = builder;
    }

    public InputDialogBuilder getBuilder() {
        return builder;
    }

    public InputDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public InputDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void show() {
        if (!isShowing()) {
//            if (builder != null && builder.viewHolder != null) {
//                builder.viewHolder.etDialogContent.setText("");
//                builder.viewHolder.etDialogContent.requestFocus();
//            }
            super.show();
        }
    }

    public static class InputDialogBuilder {

        //标题，左边按钮，右边按钮，输入框提示语
        String title, rightText, leftText, hinit1;
        boolean cancel;
        int btnType;
        boolean isShowSingle;

        private boolean isShowEt1;
        Context context;

        //确定
        private View.OnClickListener positiveButtonListener;
        //取消
        private View.OnClickListener negativeButtonListener;

        public ViewHolder viewHolder;

        public InputDialogBuilder(Context context) {
            this.context = context;
        }

        public InputDialogBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public InputDialogBuilder setRightText(String rightText) {
            this.rightText = rightText;
            return this;
        }

        public InputDialogBuilder setLeftText(String leftText) {
            this.leftText = leftText;
            return this;
        }

        public InputDialogBuilder setHinit1(String hinit1) {
            this.hinit1 = hinit1;
            return this;
        }

        public InputDialogBuilder setCancel(boolean cancel) {
            this.cancel = cancel;
            return this;
        }

        public InputDialogBuilder setBtnType(int btnType) {
            this.btnType = btnType;
            return this;
        }

        public InputDialogBuilder setShowSingle(boolean showSingle) {
            isShowSingle = showSingle;
            return this;
        }

        public InputDialogBuilder setShowEt1(boolean showEt1) {
            isShowEt1 = showEt1;
            return this;
        }

        public InputDialogBuilder setPositiveButtonListener(View.OnClickListener positiveButtonListener) {
            this.positiveButtonListener = positiveButtonListener;
            return this;
        }

        public InputDialogBuilder setNegativeButtonListener(View.OnClickListener negativeButtonListener) {
            this.negativeButtonListener = negativeButtonListener;
            return this;
        }

        public InputDialog create() {
            InputDialog inputDialog = new InputDialog(context, R.style.PublicDialog);
            View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_input_public, null);
            viewHolder = new ViewHolder(inflate);

            if (!StringUtils.isEmpty(title)) {
                viewHolder.tvDialogTitle.setText(title);
            }
            if (!StringUtils.isEmpty(hinit1)) {
                viewHolder.etDialogContent.setHint(hinit1);
            }
            if (!StringUtils.isEmpty(leftText)) {
                viewHolder.btnStart.setText(leftText);
            }
            if (!StringUtils.isEmpty(rightText)) {
                viewHolder.btnEnd.setText(rightText);
            }
            if (isShowSingle) {
                viewHolder.btnEnd.setVisibility(View.GONE);
            }
            if (isShowEt1) {
                viewHolder.etDialogContent.setVisibility(View.VISIBLE);
            } else {
                viewHolder.etDialogContent.setVisibility(View.GONE);
            }
            inputDialog.setCancelable(cancel);
            inputDialog.setCanceledOnTouchOutside(false);
            viewHolder.btnStart.setOnClickListener(negativeButtonListener);
            viewHolder.btnEnd.setOnClickListener(positiveButtonListener);
            switch (btnType) {
                case INPUTDIALOG_BTNTYPE_EDITUSERNAME://修改用户名
                    InputFilter[] filters = {new SketchLengthFilter(25), Utils.notEnterEmoji(), new InputFilter.LengthFilter(25)};
                    viewHolder.etDialogContent.setFilters(filters);
                    viewHolder.etDialogContent.addTextChangedListener(new SketchTextWatcher(viewHolder.etDialogContent));
                    break;
                case INPUTDIALOG_BTNTYPE_PASSWORD:
                    viewHolder.etDialogContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
            }
            inputDialog.setContentView(inflate);
            inputDialog.setBuilder(this);
            setFullDialog(inputDialog, true, false);
            return inputDialog;
        }

        public static class ViewHolder {
            @BindView(R.id.tv_dialog_title)
            public TextView tvDialogTitle;
            @BindView(R.id.et_dialog_content)
            public EditText etDialogContent;
            @BindView(R.id.btn_start)
            public TextView btnStart;
            @BindView(R.id.btn_end)
            public TextView btnEnd;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    public static void setFullDialog(Dialog dialog, boolean isMatchWidth, boolean isMathcHeight) {
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            if (isMatchWidth) {
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
            if (isMathcHeight) {
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            }
            // window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
//            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}
