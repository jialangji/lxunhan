package com.ay.lxunhan.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.utils.DisplayUtil;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class GlideUtil {
    private final static int DEFAULT_PIC = R.mipmap.ic_logo;
    private final static int DEFAULT_AVATAR_PIC = R.mipmap.ic_logo_round;
    private final static RequestListener requestListener = new RequestListener() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
            assert e != null;
            Log.e("GlideUtil", "onException: " + e.toString() + "  model:" + model + " isFirstResource: " + isFirstResource);
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
            return false;
        }
    };

    private static String getImgFullPath(String relativePath) {
        if (relativePath == null) {
            return null;
        }
        if (relativePath.equals("") || relativePath.startsWith("http://") || relativePath.startsWith("https://")) {
            return relativePath;
        }
        return relativePath;
    }


    public static void loadImg(Context context, ImageView v, String url) {
        GlideApp.with(context)
                .load(getImgFullPath(url)) // 图片地址
                .placeholder(DEFAULT_PIC)// 正在加载中的图片
                .dontAnimate()
                .error(DEFAULT_PIC) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)// 磁盘缓存策略
                .centerCrop()
                .listener(requestListener)
                .into(v); // 需要显示的ImageView控件
    }
    public static void loadImgLocal(Context context, ImageView v, int id){
        GlideApp.with(context)
                .load(id)
                .placeholder(DEFAULT_PIC)// 正在加载中的图片
                .dontAnimate()
                .error(DEFAULT_PIC) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)// 磁盘缓存策略
                .centerCrop()
                .listener(requestListener)
                .into(v);
    }
    public static void loadImgLocal2(Context context, ImageView v, int id){
        GlideApp.with(context)
                .load(id)
                .placeholder(DEFAULT_PIC)// 正在加载中的图片
                .dontAnimate()
                .error(DEFAULT_PIC) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)// 磁盘缓存策略
                .listener(requestListener)
                .into(v);
    }
    public static void loadImgUrl(Context context, ImageView v, String url) {
        GlideApp.with(context)
                .load(url) // 图片地址
                .placeholder(DEFAULT_PIC)// 正在加载中的图片
                .dontAnimate()
                .error(DEFAULT_PIC) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)// 磁盘缓存策略
                .centerCrop()
                .listener(requestListener)
                .into(v); // 需要显示的ImageView控件
    }

    public static void loadRoundImg(Context context, ImageView v, String url) {
        GlideApp.with(context)
                .asBitmap()
                .load(getImgFullPath(url))
                .transform(new MultiTransformation<>(new CenterCrop(),new RoundCustomTransformation(context,10)))
               // 图片地址
                .placeholder(DEFAULT_PIC)// 正在加载中的图片
                .error(DEFAULT_PIC) // 加载失败的图片
                // .listener(requestListener)
                .into(v); // 需要显示的ImageView控件
    }

    public static void loadRoundImg(Context context, ImageView v, String url, int radius) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundCustomTransformation(context, radius));
        GlideApp.with(context)
                .asBitmap()
                .skipMemoryCache(true)
                .load(getImgFullPath(url)) // 图片地址
                .placeholder(DEFAULT_PIC)// 正在加载中的图片
                .dontAnimate()
                .error(DEFAULT_PIC) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)// 磁盘缓存策略
                .apply(options)
                .listener(requestListener)
                .into(v); // 需要显示的ImageView控件
    }
    public static void loadRoundTop(Context context, ImageView v, String url) {
        RoundedCornersTransform transform = new RoundedCornersTransform(context, DisplayUtil.dip2px(context,10));
        transform.setNeedCorner(true, true, false, false);
        RequestOptions options = RequestOptions
                .bitmapTransform(transform);
        GlideApp.with(context)
                .load(getImgFullPath(url)) // 图片地址
                .placeholder(DEFAULT_PIC)// 正在加载中的图片
                .dontAnimate()
                .error(DEFAULT_PIC) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)// 磁盘缓存策略
                .apply(options)
                .listener(requestListener)
                .into(v); // 需要显示的ImageView控件
    }

    public static void loadRoundZd(Context context, ImageView v, String url, int radius) {
        RoundedCornersTransform transform = new RoundedCornersTransform(context, DisplayUtil.dip2px(context,10));
        transform.setNeedCorner(true, false, true, false);
        RequestOptions options = RequestOptions
                .bitmapTransform(transform);
        GlideApp.with(context)
                .load(getImgFullPath(url)) // 图片地址
                .placeholder(DEFAULT_PIC)// 正在加载中的图片
                .dontAnimate()
                .error(DEFAULT_PIC) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)// 磁盘缓存策略
                .apply(options)
                .listener(requestListener)
                .into(v); // 需要显示的ImageView控件
    }
    public static void loadGifImg(Context context,ImageView v,String url){
        GlideApp.with(context).asGif()
                .load(getImgFullPath(url)) // 图片地址
                .placeholder(DEFAULT_PIC)// 正在加载中的图片
                .error(DEFAULT_PIC) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)// 磁盘缓存策略
                .into(v); // 需要显示的ImageView控件
    }
    public static void loadCircleImg(Context context, ImageView v, String url) {
        GlideApp.with(context)
                .load(url) // 图片地址
                .placeholder(DEFAULT_AVATAR_PIC)// 正在加载中的图片
                .dontAnimate()
                .error(DEFAULT_AVATAR_PIC) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)// 磁盘缓存策略
                .circleCrop()
                .listener(requestListener)
                .into(v); // 需要显示的ImageView控件
    }
    public static void loadCircleImgForHead(Context context, ImageView v, Bitmap bitmap) {
        GlideApp.with(context)
                .load(bitmap) // 图片地址
                .placeholder(DEFAULT_AVATAR_PIC)// 正在加载中的图片
                .dontAnimate()
                .error(DEFAULT_AVATAR_PIC) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)// 磁盘缓存策略
                .circleCrop()
                .listener(requestListener)
                .into(v); // 需要显示的ImageView控件
    }

    public static void loadRoundImg(Context context, ImageView v, Bitmap url) {
        GlideApp.with(context)
                .load(url)
                .transform(new MultiTransformation<>(new CenterCrop(),new RoundCustomTransformation(context,10)))
                // 图片地址
                .placeholder(DEFAULT_PIC)// 正在加载中的图片
                .error(DEFAULT_PIC) // 加载失败的图片
                // .listener(requestListener)
                .into(v); // 需要显示的ImageView控件
    }

    public static void loadCircleImgForHead(Context context, ImageView v, String url) {
        GlideApp.with(context)
                .load(url) // 图片地址
                .placeholder(DEFAULT_AVATAR_PIC)// 正在加载中的图片
                .dontAnimate()
                .error(DEFAULT_AVATAR_PIC) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)// 磁盘缓存策略
                .circleCrop()
                .listener(requestListener)
                .into(v); // 需要显示的ImageView控件
    }

    public static void loadCircleImgForHeadNoCache(Context context, ImageView v, String url) {//不进行缓存
        GlideApp.with(context)
                .load(getImgFullPath(url)) // 图片地址
                .placeholder(DEFAULT_AVATAR_PIC)// 正在加载中的图片
                .dontAnimate()
                .error(DEFAULT_AVATAR_PIC) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.NONE)// 磁盘缓存策略
                .circleCrop()
                .listener(requestListener)
                .into(v); // 需要显示的ImageView控件
    }
}
