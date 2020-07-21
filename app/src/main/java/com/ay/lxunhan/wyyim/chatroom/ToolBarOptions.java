package com.ay.lxunhan.wyyim.chatroom;

import com.ay.lxunhan.R;

public class ToolBarOptions {
    /**
     * toolbar的title资源id
     */
    public int titleId = 0;
    /**
     * toolbar的title
     */
    public String titleString;
    /**
     * toolbar的logo资源id
     */
    public int logoId = R.mipmap.ic_logo_round;
    /**
     * toolbar的返回按钮资源id，默认开启的资源nim_actionbar_dark_back_icon
     */
    public int navigateId = R.drawable.ic_write_finish;
    /**
     * toolbar的返回按钮，默认开启
     */
    public boolean isNeedNavigate = true;
}
