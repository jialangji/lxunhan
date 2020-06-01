package com.ay.lxunhan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {
	public static boolean isValidFileName(String fileName) {
		if (fileName == null || fileName.length() > 255) return false;
		else
			return fileName.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
	}

	/**
	 * 保存图片到指定目录
	 *
	 * @param
	 */
	public static File saveBitmap(Context context, Bitmap mBitmap, String fileName) throws IOException {
		if (!isValidFileName(fileName)) {
			Log.e("", "文件名不能包含\\/:*?\"<>|");
			return null;
		}
		// 首先保存图片
		File externalFilesDir = context.getExternalFilesDir(null);
		File dir = (externalFilesDir == null) ? null : new File(externalFilesDir, Contacts.DIR_NAME);
		if (dir == null) {
			Log.e("", "未找到存储位置");
			return null;
		}
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
		fos.flush();
		fos.close();
		return file;
	}

	/**
	 * Drawable to bitmap.
	 *
	 * @param drawable The drawable.
	 * @return bitmap
	 */
	public static Bitmap drawable2Bitmap(final Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			if (bitmapDrawable.getBitmap() != null) {
				return bitmapDrawable.getBitmap();
			}
		}
		Bitmap bitmap;
		if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
			bitmap = Bitmap.createBitmap(1, 1,
					drawable.getOpacity() != PixelFormat.OPAQUE
							? Bitmap.Config.ARGB_8888
							: Bitmap.Config.RGB_565);
		} else {
			bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight(),
					drawable.getOpacity() != PixelFormat.OPAQUE
							? Bitmap.Config.ARGB_8888
							: Bitmap.Config.RGB_565);
		}
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}
}
