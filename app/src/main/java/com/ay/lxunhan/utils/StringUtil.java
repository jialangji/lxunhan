package com.ay.lxunhan.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ay.lxunhan.base.AppContext;
import com.blankj.utilcode.util.ActivityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * author: yu.jl
 * e-mail: bbfx89625@gmail.com
 * time  : 2019/2/21
 * desc  :
 */
public class StringUtil {

    /**
     * counter ASCII character as one, otherwise two
     *
     * @param str
     * @return count
     */
    public static int counterChars(String str) {
        // return
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            int tmp = (int) str.charAt(i);
            if (tmp > 0 && tmp < 127) {
                count += 1;
            } else {
                count += 2;
            }
        }
        return count;
    }

    public static String getFromEdit(EditText editText) {
        if (editText != null && editText.getText() != null)
            return editText.getText().toString();
        else
            return "";
    }

    public static String getErrorMsgForAddress(String address) {
        if (TextUtils.isEmpty(address)) {
            return "请输入地址";
        }
        //判断地址是否合法

        return "";
    }

    /**
     * 删除字符串中的空白符
     *
     * @param content
     * @return String
     */
    public static String removeBlanks(String content) {
        if (content == null) {
            return null;
        }
        StringBuilder buff = new StringBuilder();
        buff.append(content);
        for (int i = buff.length() - 1; i >= 0; i--) {
            if (' ' == buff.charAt(i) || ('\n' == buff.charAt(i)) || ('\t' == buff.charAt(i))
                    || ('\r' == buff.charAt(i))) {
                buff.deleteCharAt(i);
            }
        }
        return buff.toString();
    }

    public static String getPhone(String phone) {
        return phone.replaceAll("^(\\d{3})\\d+(\\d{4})$", "$1****$2");
    }

    public static String getEmail(String email) {
        return email.replaceAll("^([A-z,\\d]{2}).*([A-z,\\d]{2})(@.*)$", "$1****$2$3");
    }

    public static String getString(int rsd) {
        try {
            return ActivityUtils.getTopActivity().getString(rsd);
        } catch (Exception e) {
            return "";
        }
    }

    public static int getInteger(int rsd) {
        return ActivityUtils.getTopActivity().getResources().getInteger(rsd);
    }

    public static Activity getCurrentAc() {
        return ActivityUtils.getTopActivity();
    }


    @SuppressLint("NewApi")
    public static void copyString(String text) {
        if (android.os.Build.VERSION.SDK_INT > 10) {
            android.content.ClipboardManager clip = (android.content.ClipboardManager)
                    AppContext.context().getSystemService(Context.CLIPBOARD_SERVICE);
            clip.setPrimaryClip(ClipData.newPlainText(text, text));
        } else {
            android.text.ClipboardManager clipM = (android.text.ClipboardManager)
                    AppContext.context().getSystemService(Context.CLIPBOARD_SERVICE);
            clipM.setText(text);
        }
    }

    //6-12位
    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        //String regex = "^[a-zA-Z0-9]{6,8}$";
        String regex = "^[a-zA-Z0-9]{8,64}$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }


    //Setting content disappears after 3s
    public static void setTextDelay(TextView textView, String data) {
        AppContext.mHandler.post(() -> setTextDelay(null, textView, data));

    }

    public static void setTextDelay(View view, TextView textView, String data) {
        if (textView == null)
            return;
        if (textView.getVisibility() != View.VISIBLE) {
            textView.setVisibility(View.VISIBLE);
        }
        textView.setText(data);
        if (view != null)
            view.setClickable(false);
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("");
                if (view != null)
                    view.setClickable(true);
            }
        }, 3000);

    }

    public static String getTermsString(Context context, String name) {
        StringBuilder termsString = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(name)));

            String str;
            while ((str = reader.readLine()) != null) {
                termsString.append(str);
            }

            reader.close();
            return termsString.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
