package com.ay.lxunhan.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.AppContext;
import com.ay.lxunhan.observer.MyIUiListener;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.utils
 * @date 2020/7/6
 */
public class ShareUtils {

    private static final int THUMB_SIZE = 150;

    /**
     * 分享文字到微信好友
     */
    public static void shareToWx(Context context, String url) {
        //初始化一个WXWebpageObject，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;

        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "韩乐讯";
        msg.description = "";
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        req.userOpenId = Contacts.WX_APP_ID;

        //调用api接口，发送数据到微信
        AppContext.mWxApi.sendReq(req);
    }

    /**
     * 分享文字到朋友圈
     */
    public static void shareToWxPyq(Context context, String url) {
        //初始化一个WXWebpageObject，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "韩乐讯";
        msg.description = "";
        Bitmap thumbBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo);
        msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        req.userOpenId = Contacts.WX_APP_ID;
        //调用api接口，发送数据到微信
        AppContext.mWxApi.sendReq(req);
    }

    /**
     * 分享图片
     */
    public static void shareImgToWx(Bitmap bitmap) {
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        req.userOpenId = Contacts.WX_APP_ID;
        //调用api接口，发送数据到微信
        AppContext.mWxApi.sendReq(req);
    }


    public static void shareToQQImg(Activity context,String url) {
        final Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,url);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "韩乐讯");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare. SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare. SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        AppContext.mTencent.shareToQQ(context, params, new MyIUiListener());
    }


    public static void shareToQQ(Activity context,String url) {
        final Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,url);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "韩乐讯");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "韩乐讯");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "韩乐讯");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare. SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        AppContext.mTencent.shareToQQ(context, params, new MyIUiListener());
    }


    public static void shareToQQRoom(Activity context,String url) {
        final Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,url);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "韩乐讯");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "韩乐讯");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "韩乐讯");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        AppContext.mTencent.shareToQQ(context, params, new MyIUiListener());
    }
    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
