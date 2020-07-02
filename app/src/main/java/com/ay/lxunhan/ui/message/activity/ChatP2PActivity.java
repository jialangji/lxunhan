package com.ay.lxunhan.ui.message.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.MultipleItemQuickAdapter;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.ChatImBean;
import com.ay.lxunhan.contract.ChatImContract;
import com.ay.lxunhan.presenter.ChatImPresenter;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.MoonUtil;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.Utils;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.wyyim.emoji.AitManager;
import com.ay.lxunhan.wyyim.emoji.EmoticonPickerView;
import com.ay.lxunhan.wyyim.emoji.IEmoticonSelectedListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.NIMAntiSpamOption;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatP2PActivity extends BaseActivity<ChatImContract.ChatImView, ChatImPresenter> implements IEmoticonSelectedListener, ChatImContract.ChatImView {
    private static final int SHOW_LAYOUT_DELAY = 500;
    /**
     * 文本框最大输入字符数目
     */
    public int maxInputTextLength = 5000;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.rl_chat)
    RecyclerView rvChat;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    @BindView(R.id.editTextMessage)
    EditText messageEditText;
    @BindView(R.id.textMessageLayout)
    RelativeLayout messageInputBar;
    @BindView(R.id.emoticon_picker_view)
    EmoticonPickerView emoticonPickerView;
    Handler uiHandler;
    private List<ChatImBean.MessageListBean> chatListBeans = new ArrayList<>();
    private TextWatcher aitTextWatcher;
    AitManager aitManager = new AitManager(this);
    private String sessionId;
    private boolean isKeyboardShowed = true; // 是否显示键盘
    private BaseQuickAdapter adapter;
    private int page = 1;
    private boolean isNewMessage = false;


    @Override
    protected void initView() {
        super.initView();
        swipeRefresh.setEnableLoadmore(false);
        swipeRefresh.setAutoLoadMore(false);
        // 监听消息状态变化
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
        sessionId = getIntent().getStringExtra("userId");
        uiHandler = new Handler();
        initTextEdit();
        aitTextWatcher = aitManager;
        adapter = new MultipleItemQuickAdapter<ChatImBean.MessageListBean>(chatListBeans) {
            @Override
            protected void addItemTypes() {
                super.addItemTypes();
                addItemType(0, R.layout.item_im_heself);
                addItemType(1, R.layout.item_im_myself);
            }

            @Override
            protected void convert(BaseViewHolder helper, ChatImBean.MessageListBean item) {
                super.convert(helper, item);
                TextView bodyTextView=helper.getView(R.id.tv_message);
                MoonUtil.identifyFaceExpression(ChatP2PActivity.this, bodyTextView, item.getBody(), ImageSpan.ALIGN_BOTTOM);
                bodyTextView.setMovementMethod(LinkMovementMethod.getInstance());
            }
        };
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(adapter);


    }


    private Observer<List<IMMessage>> incomingMessageObserver = (Observer<List<IMMessage>>) messages -> {

        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                isNewMessage = true;
                presenter.getChatImShow(sessionId, page);
                NIMClient.getService(MsgService.class).sendMessageReceipt(sessionId, messages.get(messages.size()-1));
            }
        },SHOW_LAYOUT_DELAY);
    };



    @Override
    protected void initData() {
        super.initData();
        presenter.getChatImShow(sessionId, page);
    }

    @Override
    protected void initListener() {
        super.initListener();
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                if (page* Contacts.LIMIT==chatListBeans.size()){
                    page = page + 1;
                    presenter.getChatImShow(sessionId, page);
                }else {
                    ToastUtil.makeShortText(ChatP2PActivity.this,"暂无更多记录");
                }
            }
        });

    }

    @Override
    public ChatImPresenter initPresenter() {
        return new ChatImPresenter(this);
    }

    @Override
    public boolean isKeyboardEnable() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_p2_p;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }


    public static void startChat(Context context, String id) {
        Intent intent = new Intent(context, ChatP2PActivity.class);
        intent.putExtra("userId", id);
        context.startActivity(intent);
    }

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

    @Override
    public void onStickerSelected(String categoryName, String stickerName) {

    }

    // 点击edittext，切换键盘和更多布局
    private void switchToTextLayout(boolean needShowInput) {
        hideEmojiLayout();
        messageEditText.setVisibility(View.VISIBLE);
        messageInputBar.setVisibility(View.VISIBLE);

        if (needShowInput) {
            uiHandler.postDelayed(showTextRunnable, SHOW_LAYOUT_DELAY);
        } else {
            hideInputMethod();
        }
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

    // 显示表情布局
    private void showEmojiLayout() {
        hideInputMethod();
        messageEditText.requestFocus();
        uiHandler.postDelayed(showEmojiRunnable, 200);
        emoticonPickerView.setVisibility(View.VISIBLE);
        emoticonPickerView.show(this);
        uiHandler.postDelayed(() -> rvChat.scrollToPosition(chatListBeans.size() - 1), SHOW_LAYOUT_DELAY);
    }

    private Runnable showEmojiRunnable = new Runnable() {
        @Override
        public void run() {
            emoticonPickerView.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
    }

    // 显示键盘布局
    private void showInputMethod(EditText editTextMessage) {
        editTextMessage.requestFocus();
        //如果已经显示,则继续操作时不需要把光标定位到最后
        if (!isKeyboardShowed) {
            editTextMessage.setSelection(editTextMessage.getText().length());
            isKeyboardShowed = true;
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextMessage, 0);

        uiHandler.postDelayed(() -> rvChat.scrollToPosition(chatListBeans.size() - 1), SHOW_LAYOUT_DELAY);
    }

    // 隐藏键盘布局
    private void hideInputMethod() {
        isKeyboardShowed = false;
        uiHandler.removeCallbacks(showTextRunnable);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(messageEditText.getWindowToken(), 0);
        messageEditText.clearFocus();
    }

    private Runnable showTextRunnable = new Runnable() {
        @Override
        public void run() {
            showInputMethod(messageEditText);
        }
    };


    private void initTextEdit() {
        messageEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        messageEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                switchToTextLayout(true);
            }
            return false;
        });


        messageEditText.addTextChangedListener(new TextWatcher() {
            private int start;
            private int count;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.start = start;
                this.count = count;
                if (aitTextWatcher != null) {
                    aitTextWatcher.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (aitTextWatcher != null) {
                    aitTextWatcher.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Utils.replaceEmoticons(ChatP2PActivity.this, s, start, count);

                int editEnd = messageEditText.getSelectionEnd();
                messageEditText.removeTextChangedListener(this);
                while (StringUtil.counterChars(s.toString()) > maxInputTextLength && editEnd > 0) {
                    s.delete(editEnd - 1, editEnd);
                    editEnd--;
                }
                messageEditText.setSelection(editEnd);
                messageEditText.addTextChangedListener(this);

                if (aitTextWatcher != null) {
                    aitTextWatcher.afterTextChanged(s);
                }
            }
        });
    }

    @OnClick({R.id.rl_finish, R.id.rl_more, R.id.emoji_button, R.id.buttonSendMessage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.rl_more:
                break;
            case R.id.emoji_button:
                toggleEmojiLayout();
                break;
            case R.id.buttonSendMessage:
                onTextMessageSendButtonPressed();
                break;
        }
    }

    // 发送文本消息
    private void onTextMessageSendButtonPressed() {
        if (TextUtils.isEmpty(StringUtil.getFromEdit(messageEditText))){
            ToastUtil.makeShortText(this, StringUtil.getString(R.string.null_text));
            return;
        }
        String text = messageEditText.getText().toString();
        IMMessage textMessage = createTextMessage(text);
        NIMAntiSpamOption antiSpamOption = new NIMAntiSpamOption();
        antiSpamOption.enable = false;
        textMessage.setNIMAntiSpamOption(antiSpamOption);

        // send message to server and save to db
        NIMClient.getService(MsgService.class).sendMessage(textMessage, false).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                restoreText(true);
            }

            @Override
            public void onFailed(int code) {
                Log.e("IM:", "CODE:" + code);
                sendFailWithBlackList(code, textMessage);
            }

            @Override
            public void onException(Throwable exception) {
                Log.e("IM:", "CODE:" + exception.getMessage());
            }
        });
    }

    protected IMMessage createTextMessage(String text) {
        return MessageBuilder.createTextMessage(sessionId.toLowerCase(), SessionTypeEnum.P2P, text);
    }

    private void restoreText(boolean clearText) {
        if (clearText) {
            messageEditText.setText("");
        }
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                isNewMessage = true;
                presenter.getChatImShow(sessionId, page);
            }
        },SHOW_LAYOUT_DELAY);
    }


    // 被对方拉入黑名单后，发消息失败的交互处理
    private void sendFailWithBlackList(int code, IMMessage msg) {
        if (code == ResponseCode.RES_IN_BLACK_LIST) {
            // 如果被对方拉入黑名单，发送的消息前不显示重发红点
            msg.setStatus(MsgStatusEnum.success);
            NIMClient.getService(MsgService.class).updateIMMessageStatus(msg);
            // 同时，本地插入被对方拒收的tip消息
            IMMessage tip = MessageBuilder.createTipMessage(msg.getSessionId(), msg.getSessionType());
            tip.setContent(StringUtil.getString(R.string.black_list_send_tip));
            tip.setStatus(MsgStatusEnum.success);
            CustomMessageConfig config = new CustomMessageConfig();
            config.enableUnreadCount = false;
            tip.setConfig(config);
            NIMClient.getService(MsgService.class).saveMessageToLocal(tip, true);
        }
    }

    @Override
    public void getChatImShowFinish(ChatImBean imBean) {
        GlideUtil.loadCircleImgForHead(this, ivHeader, imBean.getUser_avatar());
        tvName.setText(imBean.getUser_nickname());
        tvSignature.setText(imBean.getUser_autograph());
        ivSex.setImageDrawable(imBean.getUser_sex() ? getResources().getDrawable(R.drawable.ic_man) : getResources().getDrawable(R.drawable.ic_woman));
        swipeRefresh.finishRefreshing();
        if (isNewMessage) {
            chatListBeans.clear();
            chatListBeans.addAll(imBean.getMessage_list());
        } else {
            chatListBeans.addAll(imBean.getMessage_list());
        }

        adapter.notifyDataSetChanged();
        uiHandler.postDelayed(() -> rvChat.scrollToPosition(chatListBeans.size() - 1), SHOW_LAYOUT_DELAY);
    }
}
