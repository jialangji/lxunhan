package com.ay.lxunhan.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveBitmap {
    //保存图片至本地
    public static void bitmapFileSaveLocal(Context context,View view){
        saveImageToGallery(context,viewShot(view));
    }

    private static Bitmap viewShot(final View view){
        if (view == null)
            return null;
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(measureSpec, measureSpec);

        if (view.getMeasuredWidth()<=0 || view.getMeasuredHeight()<=0) {
            Logger.e("ImageUtils.viewShot size error");
            return null;
        }
        Bitmap bm;
        try {
            bm = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        }catch (OutOfMemoryError e){
            System.gc();
            try {
                bm = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            }catch (OutOfMemoryError ee){
                Logger.e("ImageUtils.viewShot error", ee);
                return null;
            }
        }
        Canvas bigCanvas = new Canvas(bm);
        Paint paint = new Paint();
        int iHeight = bm.getHeight();
        bigCanvas.drawBitmap(bm, 0, iHeight, paint);
        view.draw(bigCanvas);
        return bm;
    }

    /**
     * 保存图片到手机相册，并通知图库更新
     * @param context
     * @param bmp 图片bitmap
     * @return  返回图片保存的路径，开发人员可以根据返回的路径在手机里面查看，部分手机发送通知图库并不会更新
     */

    public static String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "saveImage");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "hlx"+ System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        String path = Environment.getExternalStorageDirectory() + "/ saveImage /" + fileName; context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        return Environment.getExternalStorageDirectory() + "/saveImage/" + fileName;
    }
}
