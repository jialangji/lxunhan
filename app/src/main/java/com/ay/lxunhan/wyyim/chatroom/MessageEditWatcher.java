package com.ay.lxunhan.wyyim.chatroom;

import android.text.Editable;

public interface MessageEditWatcher {

    void afterTextChanged(Editable editable, int start, int count);

}