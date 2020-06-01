package com.ay.lxunhan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;


import com.ay.lxunhan.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import static com.ay.lxunhan.utils.Contacts.DIR_NAME;


public class FileUtils {

    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值
    public static File saveBitmap(Context context, Bitmap bitmap) {
        return saveBitmap(context, bitmap, context.getString(R.string.app_name));
    }

    public static File saveBitmap(Context context, Bitmap bitmap, String dirName) {
        File fileDir = getSavedFile(context, dirName);
        if (!fileDir.exists()) fileDir.mkdirs();
        String imageFileName = "hlx" + System.currentTimeMillis() + ".jpg";
        try {
            File file = new File(fileDir, imageFileName);
            FileOutputStream fout = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File getSavedFile(Context context, String dirName) {
        File fileDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            fileDir = new File(Environment.getExternalStorageDirectory() + File.separator + DIR_NAME + File.separator + dirName);
        } else {
            fileDir = new File(context.getExternalFilesDir(null) + File.separator + dirName);
        }
        return fileDir;
    }

    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("File","获取文件大小不存在!");
        }
        return size;
    }

    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

}
