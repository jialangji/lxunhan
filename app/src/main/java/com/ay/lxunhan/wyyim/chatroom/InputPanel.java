package com.ay.lxunhan.wyyim.chatroom;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.ay.lxunhan.R;
import com.ay.lxunhan.utils.MoonUtil;
import com.ay.lxunhan.wyyim.emoji.EmoticonPickerView;
import com.ay.lxunhan.wyyim.emoji.IEmoticonSelectedListener;
import com.netease.LSMediaCapture.util.NimUIKit;
import com.netease.LSMediaCapture.util.string.StringUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.CustomNotificationConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

public class InputPanel implements IEmoticonSelectedListener {

    private static final String TAG = "MsgSendLayout";

    private static final int SHOW_LAYOUT_DELAY = 100;

    protected Container container;
    protected View view;
    protected Handler uiHandler;

    protected View actionPanelBottomLayout; // 更多布局
    protected LinearLayout messageActivityBottomLayout;
    protected EditText messageEditText;// 文本消息编辑框
    protected View sendMessageButtonInInputBar;// 发送消息按钮
    protected View emojiButtonInInputBar;// 发送消息按钮
    protected View messageInputBar;

    private SessionCustomization customization;

    // 表情
    protected EmoticonPickerView emoticonPickerView;  // 贴图表情控件

    private boolean isKeyboardShowed = true; // 是否显示键盘

    private boolean isTextAudioSwitchShow = true;

    // adapter
    private List<BaseAction> actions;

    // data
    private long typingTime = 0;

    // message edit watcher

    private MessageEditWatcher watcher;

    public InputPanel(Container container, View view, List<BaseAction> actions, boolean isTextAudioSwitchShow) {
        this.container = container;
        this.view = view;
        this.actions = actions;
        this.uiHandler = new Handler();
        this.isTextAudioSwitchShow = isTextAudioSwitchShow;
        init();
    }

    public InputPanel(Container container, View view, List<BaseAction> actions) {
        this(container, view, actions, true);
    }

    public void onPause() {
        // 停止录音
    }

    public boolean collapse(boolean immediately) {
        boolean respond = (emoticonPickerView != null && emoticonPickerView.getVisibility() == View.VISIBLE
                || actionPanelBottomLayout != null && actionPanelBottomLayout.getVisibility() == View.VISIBLE);

        hideAllInputLayout(immediately);

        return respond;
    }

    public void setWatcher(MessageEditWatcher watcher) {
        this.watcher = watcher;
    }

    private void init() {
        initViews();
        initInputBarListener();
        initTextEdit();
        restoreText(false);

        for (int i = 0; i < actions.size(); ++i) {
            actions.get(i).setIndex(i);
            actions.get(i).setContainer(container);
        }
    }

    public void setCustomization(SessionCustomization customization) {
        this.customization = customization;
        if (customization != null) {
            emoticonPickerView.setWithSticker(customization.withSticker);
        }
    }

    public void reload(Container container, SessionCustomization customization) {
        this.container = container;
        setCustomization(customization);
    }

    private void initViews() {
        // input bar
        messageActivityBottomLayout = (LinearLayout) view.findViewById(R.id.messageActivityBottomLayout);
        messageInputBar = view.findViewById(R.id.textMessageLayout);
        emojiButtonInInputBar = view.findViewById(R.id.emoji_button);
        sendMessageButtonInInputBar = view.findViewById(R.id.buttonSendMessage);
        messageEditText = (EditText) view.findViewById(R.id.editTextMessage);

        // 表情
        emoticonPickerView = (EmoticonPickerView) view.findViewById(R.id.emoticon_picker_view);


    }

    private void initInputBarListener() {
        emojiButtonInInputBar.setOnClickListener(clickListener);
        sendMessageButtonInInputBar.setOnClickListener(clickListener);
    }

    private void initTextEdit() {
        messageEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        messageEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                switchToTextLayout(true);
            }
            return false;
        });

        messageEditText.setOnFocusChangeListener((v, hasFocus) -> {
            //TODO 取消注释改行,配合更多按钮功能
            //messageEditText.setHint("");
            //checkSendButtonEnable(messageEditText);
        });

        messageEditText.addTextChangedListener(new TextWatcher() {
            private int start;
            private int count;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.start = start;
                this.count = count;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //todo 未输入时,显示更多按钮,则取消注释该行
                //checkSendButtonEnable(messageEditText);
                MoonUtil.replaceEmoticons(container.activity, s.toString(), start, count);

                if (watcher != null) {
                    watcher.afterTextChanged(s, start, count);
                }

                int editEnd = messageEditText.getSelectionEnd();
                messageEditText.removeTextChangedListener(this);
                while (StringUtil.counterChars(s.toString()) > 5000 && editEnd > 0) {
                    s.delete(editEnd - 1, editEnd);
                    editEnd--;
                }
                messageEditText.setSelection(editEnd);
                messageEditText.addTextChangedListener(this);

                sendTypingCommand();
            }
        });
    }


    /**
     * 发送“正在输入”通知
     */
    private void sendTypingCommand() {
        if (container.account.equals(NimUIKit.getAccount())) {
            return;
        }

        if (container.sessionType == SessionTypeEnum.Team || container.sessionType == SessionTypeEnum.ChatRoom) {
            return;
        }

        if (System.currentTimeMillis() - typingTime > 5000L) {
            typingTime = System.currentTimeMillis();
            CustomNotification command = new CustomNotification();
            command.setSessionId(container.account);
            command.setSessionType(container.sessionType);
            CustomNotificationConfig config = new CustomNotificationConfig();
            config.enablePush = false;
            config.enableUnreadCount = false;
            command.setConfig(config);

            JSONObject json = new JSONObject();
            json.put("id", "1");
            command.setContent(json.toString());

            NIMClient.getService(MsgService.class).sendCustomNotification(command);
        }
    }

    /**
     * ************************* 键盘布局切换 *******************************
     */

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
           if (v == sendMessageButtonInInputBar) {
                onTextMessageSendButtonPressed();
            } else  if (v == emojiButtonInInputBar) {
                toggleEmojiLayout();
            }
        }
    };

    // 点击edittext，切换键盘和更多布局
    private void switchToTextLayout(boolean needShowInput) {
        hideEmojiLayout();
        hideActionPanelLayout();


        messageEditText.setVisibility(View.VISIBLE);
        messageInputBar.setVisibility(View.VISIBLE);

        if (needShowInput) {
            uiHandler.postDelayed(showTextRunnable, SHOW_LAYOUT_DELAY);
        } else {
            hideInputMethod();
        }
    }

    // 发送文本消息
    private void onTextMessageSendButtonPressed() {
        String text = messageEditText.getText().toString();
        if(text.isEmpty()){
            Toast.makeText(container.activity, "请输入后再发送", Toast.LENGTH_SHORT).show();
            return;
        }
        IMMessage textMessage = createTextMessage(text);

        if (container.proxy.sendMessage(textMessage)) {
            restoreText(true);
        }
    }

    // 发送文本消息
    public void onTextMessageSendButtonPressed(String text) {
        if(text.isEmpty()){
            Toast.makeText(container.activity, "请输入后再发送", Toast.LENGTH_SHORT).show();
            return;
        }
        IMMessage textMessage = createTextMessage(text);

        if (container.proxy.sendMessage(textMessage)) {
            restoreText(true);
        }
    }

    protected IMMessage createTextMessage(String text) {
        return MessageBuilder.createTextMessage(container.account, container.sessionType, text);
    }


    // 点击表情，切换到表情布局
    private void toggleEmojiLayout() {
        if (emoticonPickerView == null || emoticonPickerView.getVisibility() == View.GONE) {
            showEmojiLayout();
        } else {
            hideEmojiLayout();
        }
    }

    // 隐藏表情布局
    private void hideEmojiLayout() {
        uiHandler.removeCallbacks(showEmojiRunnable);
        if (emoticonPickerView != null) {
            emoticonPickerView.setVisibility(View.GONE);
        }
    }

    // 隐藏更多布局
    private void hideActionPanelLayout() {
        uiHandler.removeCallbacks(showMoreFuncRunnable);
        if (actionPanelBottomLayout != null) {
            actionPanelBottomLayout.setVisibility(View.GONE);
        }
    }

    // 隐藏键盘布局
    private void hideInputMethod() {
        isKeyboardShowed = false;
        uiHandler.removeCallbacks(showTextRunnable);
        InputMethodManager imm = (InputMethodManager) container.activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(messageEditText.getWindowToken(), 0);
        messageEditText.clearFocus();
    }

    // 隐藏语音布局
    private void hideAudioLayout() {

        messageEditText.setVisibility(View.VISIBLE);
    }

    // 显示表情布局
    private void showEmojiLayout() {
        hideInputMethod();
        hideActionPanelLayout();
        hideAudioLayout();

        messageEditText.requestFocus();
        uiHandler.postDelayed(showEmojiRunnable, 200);
        emoticonPickerView.setVisibility(View.VISIBLE);
        emoticonPickerView.show(this);
        container.proxy.onInputPanelExpand();
    }

    // 显示键盘布局
    private void showInputMethod(EditText editTextMessage) {
        editTextMessage.requestFocus();
        //如果已经显示,则继续操作时不需要把光标定位到最后
        if (!isKeyboardShowed) {
            editTextMessage.setSelection(editTextMessage.getText().length());
            isKeyboardShowed = true;
        }

        InputMethodManager imm = (InputMethodManager) container.activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextMessage, 0);

        container.proxy.onInputPanelExpand();
    }



    private Runnable showEmojiRunnable = new Runnable() {
        @Override
        public void run() {
            emoticonPickerView.setVisibility(View.VISIBLE);
        }
    };

    private Runnable showMoreFuncRunnable = new Runnable() {
        @Override
        public void run() {
            actionPanelBottomLayout.setVisibility(View.VISIBLE);
        }
    };

    private Runnable showTextRunnable = new Runnable() {
        @Override
        public void run() {
            showInputMethod(messageEditText);
        }
    };

    private void restoreText(boolean clearText) {
        if (clearText) {
            messageEditText.setText("");
        }
        //todo  打开更多按钮,取消注释该行
        //checkSendButtonEnable(messageEditText);
    }

    /**
     * *************** IEmojiSelectedListener ***************
     */
    @Override
    public void onEmojiSelected(String key) {
        Editable mEditable = messageEditText.getText();
        if (key.equals("/DEL")) {
            messageEditText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        } else {
            int start = messageEditText.getSelectionStart();
            int end = messageEditText.getSelectionEnd();
            start = (start < 0 ? 0 : start);
            end = (start < 0 ? 0 : end);
            mEditable.replace(start, end, key);
        }
    }

    private Runnable hideAllInputLayoutRunnable;

    @Override
    public void onStickerSelected(String category, String item) {
        Log.i("InputPanel", "onStickerSelected, category =" + category + ", sticker =" + item);

        if (customization != null) {
            MsgAttachment attachment = customization.createStickerAttachment(category, item);
            IMMessage stickerMessage = MessageBuilder.createCustomMessage(container.account, container.sessionType, "贴图消息", attachment);
            container.proxy.sendMessage(stickerMessage);
        }
    }

    /**
     * 隐藏所有输入布局
     */
    private void hideAllInputLayout(boolean immediately) {
        if (hideAllInputLayoutRunnable == null) {
            hideAllInputLayoutRunnable = new Runnable() {

                @Override
                public void run() {
                    hideInputMethod();
                    hideActionPanelLayout();
                    hideEmojiLayout();
                }
            };
        }
        long delay = immediately ? 0 : ViewConfiguration.getDoubleTapTimeout();
        uiHandler.postDelayed(hideAllInputLayoutRunnable, delay);
    }

    public void setVisible(boolean visible){
        if(visible){
            messageActivityBottomLayout.setVisibility(View.VISIBLE);
        }else{
            messageActivityBottomLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void expandInputPanel(){
        switchToTextLayout(true);
    }
}
