package com.ay.lxunhan.wyyim.chatroom;

import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.ay.lxunhan.R;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.ChatRoomServiceObserver;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.AttachmentProgress;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChatRoomMsgListPanel implements TAdapterDelegate {
    private static final int MESSAGE_CAPACITY = 500;

    // container
    private Container container;
    private View rootView;
    private Handler uiHandler;

    // message list view
    private MessageListView messageListView;
    private LinkedList<IMMessage> items;
    private MsgAdapter adapter;

    ExtraDelegate extraDelegate;

    public interface ExtraDelegate {
        void onReceivedCustomAttachment(ChatRoomMessage msg);

        void onMessageClick(IMMessage imMessage);
    }

    public void setExtraDelegate(ExtraDelegate extraDelegate) {
        this.extraDelegate = extraDelegate;
    }

    public ChatRoomMsgListPanel(Container container, View rootView) {
        this.container = container;
        this.rootView = rootView;

        init();
    }

    public void onResume() {
        setEarPhoneMode(UserPreferences.isEarPhoneModeEnable());
    }

    public void onPause() {
    }

    public void onDestroy() {
        registerObservers(false);
    }

    public boolean onBackPressed() {
        uiHandler.removeCallbacks(null);
        return false;
    }

    private void init() {
        initListView();
        this.uiHandler = new Handler();
        registerObservers(true);
    }

    private void initListView() {
        items = new LinkedList<>();
        adapter = new MsgAdapter(container.activity, items, this);
        adapter.setEventListener(new MsgItemEventListener());

        messageListView = (MessageListView) rootView.findViewById(R.id.messageListView);
        messageListView.requestDisallowInterceptTouchEvent(true);

        messageListView.setMode(AutoRefreshListView.Mode.START);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            messageListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        // adapter
        messageListView.setAdapter(adapter);

        messageListView.setListViewEventListener(() -> container.proxy.shouldCollapseInputPanel());
        //fixme 取消注释后可收取历史消息
        //messageListView.setOnRefreshListener(new MessageLoader());
    }

    // 刷新消息列表
    public void refreshMessageList() {
        container.activity.runOnUiThread(() -> adapter.notifyDataSetChanged());
    }

    public void scrollToBottom() {
        uiHandler.postDelayed(() -> ListViewUtil.scrollToBottom(messageListView), 200);
    }

    public void onIncomingMessage(List<ChatRoomMessage> messages) {
        boolean needScrollToBottom = ListViewUtil.isLastMessageVisible(messageListView);
        boolean needRefresh = false;
        List<ChatRoomMessage> addedListItems = new ArrayList<>(messages.size());
        for (ChatRoomMessage message : messages) {
            //点赞 与 礼物 信息不显示在聊天列表
            if (message != null && message.getMsgType()== MsgTypeEnum.custom) {
                if (extraDelegate != null) {
                    extraDelegate.onReceivedCustomAttachment(message);
                }
            } else if (isMyMessage(message)) {
                saveMessage(message, false);
                addedListItems.add(message);
                needRefresh = true;
            }


            //对通知类信息再进行一次人员列表变更处理
            if (message != null && message.getAttachment() instanceof ChatRoomNotificationAttachment) {
                if (extraDelegate != null) {
                    extraDelegate.onReceivedCustomAttachment(message);
                }
            }
        }
        if (needRefresh) {
            adapter.notifyDataSetChanged();
        }

        // incoming messages tip
        ChatRoomMessage lastMsg = messages.get(messages.size() - 1);
        if (isMyMessage(lastMsg) && needScrollToBottom) {
            ListViewUtil.scrollToBottom(messageListView);
        }
    }

    // 发送消息后，更新本地消息列表
    public void onMsgSend(IMMessage message) {
        // add to listView and refresh
        saveMessage(message, false);
        List<IMMessage> addedListItems = new ArrayList<>(1);
        addedListItems.add(message);

        adapter.notifyDataSetChanged();
        ListViewUtil.scrollToBottom(messageListView);
    }

    private void saveMessage(final List<IMMessage> messageList, boolean addFirst) {
        if (messageList == null || messageList.isEmpty()) {
            return;
        }

        for (IMMessage msg : messageList) {
            saveMessage(msg, addFirst);
        }
    }

    public void saveMessage(final IMMessage message, boolean addFirst) {
        if (message == null) {
            return;
        }

        if (items.size() >= MESSAGE_CAPACITY) {
            items.poll();
        }

        if (addFirst) {
            items.add(0, message);
        } else {
            items.add(message);
        }
    }

    /**
     * *************** implements TAdapterDelegate ***************
     */
    @Override
    public int getViewTypeCount() {
        return ChatRoomMsgViewHolderFactory.getViewTypeCount();
    }

    @Override
    public Class<? extends TViewHolder> viewHolderAtPosition(int position) {
        return ChatRoomMsgViewHolderFactory.getViewHolderByType(items.get(position));
    }

    @Override
    public boolean enabled(int position) {
        return false;
    }


    /**
     * *************** MessageLoader ***************
     */
    private class MessageLoader implements AutoRefreshListView.OnRefreshListener {

        private static final int LOAD_MESSAGE_COUNT = 10;

        private IMMessage anchor;

        private boolean firstLoad = true;

        public MessageLoader() {
            anchor = null;
            loadFromLocal();
        }

        private RequestCallback<List<ChatRoomMessage>> callback = new RequestCallbackWrapper<List<ChatRoomMessage>>() {
            @Override
            public void onResult(int code, List<ChatRoomMessage> messages, Throwable exception) {
                if (messages != null) {
                    onMessageLoaded(messages);
                } else {
                    messageListView.onRefreshComplete(LOAD_MESSAGE_COUNT, LOAD_MESSAGE_COUNT, false);
                }
            }
        };

        private void loadFromLocal() {
            messageListView.onRefreshStart(AutoRefreshListView.Mode.START);
            NIMClient.getService(ChatRoomService.class).pullMessageHistory(container.account, anchor().getTime(), LOAD_MESSAGE_COUNT)
                    .setCallback(callback);
        }

        private IMMessage anchor() {
            if (items.size() == 0) {
                return (anchor == null ? ChatRoomMessageBuilder.createEmptyChatRoomMessage(container.account, 0) : anchor);
            } else {
                return items.get(0);
            }
        }

        /**
         * 历史消息加载处理
         */
        private void onMessageLoaded(List<ChatRoomMessage> messages) {
            int count = messages.size();

            if (items.size() > 0) {
                // 在第一次加载的过程中又收到了新消息，做一下去重
                for (IMMessage message : messages) {
                    for (IMMessage item : items) {
                        if (item.isTheSame(message)) {
                            items.remove(item);
                            break;
                        }
                    }
                }
            }

            List<IMMessage> result = new ArrayList<>();
            for (IMMessage message : messages) {
                result.add(message);
            }
            saveMessage(result, true);

            // 如果是第一次加载，updateShowTimeItem返回的就是lastShowTimeItem
            if (firstLoad) {
                ListViewUtil.scrollToBottom(messageListView);
            }

            refreshMessageList();
            messageListView.onRefreshComplete(count, LOAD_MESSAGE_COUNT, true);

            firstLoad = false;
        }

        /**
         * *************** OnRefreshListener ***************
         */
        @Override
        public void onRefreshFromStart() {
            loadFromLocal();
        }

        @Override
        public void onRefreshFromEnd() {
        }
    }


    /**
     * ************************* 观察者 ********************************
     */
    private void registerObservers(boolean register) {
        ChatRoomServiceObserver service = NIMClient.getService(ChatRoomServiceObserver.class);
        service.observeMsgStatus(messageStatusObserver, register);
        service.observeAttachmentProgress(attachmentProgressObserver, register);
    }

    /**
     * 消息状态变化观察者
     */
    Observer<ChatRoomMessage> messageStatusObserver = (Observer<ChatRoomMessage>) message -> {
        if (isMyMessage(message)) {
            onMessageStatusChange(message);
        }
    };

    /**
     * 消息附件上传/下载进度观察者
     */
    Observer<AttachmentProgress> attachmentProgressObserver = (Observer<AttachmentProgress>) progress -> onAttachmentProgressChange(progress);

    private void onMessageStatusChange(IMMessage message) {
        int index = getItemIndex(message.getUuid());
        if (index >= 0 && index < items.size()) {
            IMMessage item = items.get(index);
            item.setStatus(message.getStatus());
            item.setAttachStatus(message.getAttachStatus());
            refreshViewHolderByIndex(index);
        }
    }

    private void onAttachmentProgressChange(AttachmentProgress progress) {
        int index = getItemIndex(progress.getUuid());
        if (index >= 0 && index < items.size()) {
            IMMessage item = items.get(index);
            float value = (float) progress.getTransferred() / (float) progress.getTotal();
            adapter.putProgress(item, value);
            refreshViewHolderByIndex(index);
        }
    }

    public boolean isMyMessage(ChatRoomMessage message) {
        return message.getSessionType() == container.sessionType
                && message.getSessionId() != null
                && message.getSessionId().equals(container.account);
    }

    /**
     * 刷新单条消息
     *
     * @param index
     */
    private void refreshViewHolderByIndex(final int index) {
        container.activity.runOnUiThread(() -> {
            if (index < 0) {
                return;
            }

            Object tag = ListViewUtil.getViewHolderByIndex(messageListView, index);
            if (tag instanceof MsgViewHolderBase) {
                MsgViewHolderBase viewHolder = (MsgViewHolderBase) tag;
                viewHolder.refreshCurrentItem();
            }
        });
    }

    private int getItemIndex(String uuid) {
        for (int i = 0; i < items.size(); i++) {
            IMMessage message = items.get(i);
            if (TextUtils.equals(message.getUuid(), uuid)) {
                return i;
            }
        }

        return -1;
    }

    private class MsgItemEventListener implements MsgAdapter.ViewHolderEventListener {

        @Override
        public void onFailedBtnClick(IMMessage message) {
            if (message.getDirect() == MsgDirectionEnum.Out) {
                // 发出的消息，如果是发送失败，直接重发，否则有可能是漫游到的多媒体消息，但文件下载
                if (message.getStatus() == MsgStatusEnum.fail) {
                    resendMessage(message); // 重发
                } else {
                    if (message.getAttachment() instanceof FileAttachment) {
                        FileAttachment attachment = (FileAttachment) message.getAttachment();
                        if (TextUtils.isEmpty(attachment.getPath())
                                && TextUtils.isEmpty(attachment.getThumbPath())) {
                            showReDownloadConfirmDlg(message);
                        }
                    } else {
                        resendMessage(message);
                    }
                }
            } else {
                showReDownloadConfirmDlg(message);
            }
        }

        @Override
        public void onMessageClick(IMMessage message) {
            extraDelegate.onMessageClick(message);
        }

        @Override
        public boolean onViewHolderLongClick(View clickView, View viewHolderView, IMMessage item) {
            return true;
        }

        // 重新下载(对话框提示)
        private void showReDownloadConfirmDlg(final IMMessage message) {
        }

        // 重发消息到服务器
        private void resendMessage(IMMessage message) {
            // 重置状态为unsent
            int index = getItemIndex(message.getUuid());
            if (index >= 0 && index < items.size()) {
                IMMessage item = items.get(index);
                item.setStatus(MsgStatusEnum.sending);
                refreshViewHolderByIndex(index);
            }

            NIMClient.getService(ChatRoomService.class).sendMessage((ChatRoomMessage) message, true);
        }

    }

    private void setEarPhoneMode(boolean earPhoneMode) {
        UserPreferences.setEarPhoneModeEnable(earPhoneMode);
    }
}
