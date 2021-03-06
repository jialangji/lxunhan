package com.ay.lxunhan.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.EditText;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.AppContext;
import com.ay.lxunhan.wyyim.emoji.EmojiManager;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Utils {

    public static String matcherSearchContent(String text, String[] keyword1) {
        String[] keyword = new String[keyword1.length];
        System.arraycopy(keyword1, 0, keyword, 0, keyword1.length);
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        CharacterStyle span;
        String wordReg;
        for (int i = 0; i < keyword.length; i++) {
            String key = "";
            //  处理通配符问题
            if (keyword[i].contains("*") || keyword[i].contains("(") || keyword[i].contains(")")) {
                char[] chars = keyword[i].toCharArray();
                for (int k = 0; k < chars.length; k++) {
                    if (chars[k] == '*' || chars[k] == '(' || chars[k] == ')') {
                        key = key + "\\" + String.valueOf(chars[k]);
                    } else {
                        key = key + String.valueOf(chars[k]);
                    }
                }
                keyword[i] = key;
            }

            wordReg = "(?i)" + keyword[i];   //忽略字母大小写
            Pattern pattern = Pattern.compile(wordReg);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                span = new ForegroundColorSpan(AppContext.instance.getResources().getColor(R.color.color_fc5a8e));
                spannable.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_MARK_MARK);
            }
        }

        return spannable.toString();
    }

    private static final float SMALL_SCALE = 0.45F;
    public static void replaceEmoticons(Context context, Editable editable, int start, int count) {
        if (count <= 0 || editable.length() < start + count)
            return;

        CharSequence s = editable.subSequence(start, start + count);
        Matcher matcher = EmojiManager.getPattern().matcher(s);
        while (matcher.find()) {
            int from = start + matcher.start();
            int to = start + matcher.end();
            String emot = editable.subSequence(from, to).toString();
            Drawable d = getEmotDrawable(context, emot, SMALL_SCALE);
            if (d != null) {
                ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
                editable.setSpan(span, from, to, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
    private static Drawable getEmotDrawable(Context context, String text, float scale) {
        Drawable drawable = EmojiManager.getDrawable(context, text);

        // scale
        if (drawable != null) {
            int width = (int) (drawable.getIntrinsicWidth() * scale);
            int height = (int) (drawable.getIntrinsicHeight() * scale);
            drawable.setBounds(0, 0, width, height);
        }

        return drawable;
    }

    /**
     * 是否是网页
     */
    public static boolean isUrl(String str){
        return str.startsWith("http://") || str.startsWith("https://");
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static long lastClickTime;

    // 根据年月日计算年龄,birthTimeString:"1994-11-14"
    public static int getAgeFromBirthTime(Date date) {
        // 得到当前时间的年、月、日
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH) + 1;
            int dayNow = cal.get(Calendar.DATE);
            //得到输入时间的年，月，日
            cal.setTime(date);
            int selectYear = cal.get(Calendar.YEAR);
            int selectMonth = cal.get(Calendar.MONTH) + 1;
            int selectDay = cal.get(Calendar.DATE);
            // 用当前年月日减去生日年月日
            int yearMinus = yearNow - selectYear;
            int monthMinus = monthNow - selectMonth;
            int dayMinus = dayNow - selectDay;
            int age = yearMinus;// 先大致赋值
            if (yearMinus <= 0) {
                age = 0;
            }
            if (monthMinus < 0) {
                age = age - 1;
            } else if (monthMinus == 0) {
                if (dayMinus < 0) {
                    age = age - 1;
                }
            }
            return age;
        }
        return 0;
    }

    public static Date getDate(String str) {
        Date date;
        try {
            java.text.SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.parse(str);
            return date;
        } catch (Exception e) {
            return null;
        }

    }

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    public static String num(long number) {
        String num;
        if (number < 10000) {
            num = String.valueOf(number);
            return num;
        } else {
            if (number % 10000 > 1000) {
                num = number / 10000 + "." + number % 10000 / 1000 + "万";
            } else {
                num = number / 10000 + "万";
            }
            return num;
        }
    }

    public static String getHtmlData(String bodyHTML) {
        String head = "<head >" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/content-styles.css\" type=\"text/css\">"+
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">"+
                "<style>* img{max-width: 100%; width:auto; height:auto!important;}</style>"+
                "</head>";
        return "<html>" + head + "<body class=\"ck-content\">" + bodyHTML + "</body></html>";
    }


    public static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static List<String> stringToList(String strs, String segmentation) {
        String str[] = strs.split(segmentation);
        return Arrays.asList(str);
    }

    static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[-+]?[0-9]+(\\.[0-9]+)?$");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static TextWatcher setNullTextWatcher(EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = StringUtil.getFromEdit(editText);
                String str = editable.replaceAll("", "").trim();    //删掉空字符
                if (!editable.equals(str)) {
                    editText.setText(str);  //设置EditText的字符
                    editText.setSelection(str.length()); //因为删除了字符，要重写设置新的光标所在位置
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    public static TextWatcher setPswTextWatcher(EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = StringUtil.getFromEdit(editText);
                String regEx = "[^a-zA-Z0-9]";  //只能输入字母或数字
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(editable);
                String str = m.replaceAll("").trim();
                if (str.length() > 64) {
                    str = str.substring(0, 64);
                }
                //删掉不是字母或数字的字符
                if (!editable.equals(str)) {
                    editText.setText(str);  //设置EditText的字符
                    editText.setSelection(str.length()); //因为删除了字符，要重写设置新的光标所在位置
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    public static void limitEmailInput(final EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int index = s.toString().indexOf("@");
                if (index == -1 && s.toString().length() > 30) {//包含@
                    et.setText(s.toString().substring(0, 30));
                    et.setSelection(30);
                } else if (index > 30) {
                    et.setText(s.toString().substring(0, 30));
                    et.setSelection(30);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static void limitCountInput(EditText et, CharSequence textChange, int pointSize) {
        try {
            String text = textChange.toString();
            int pointIndex = text.indexOf(".");
            int length = textChange.length();

            if (pointIndex < 0) {
                if (length > 8) {
                    et.setText(textChange.toString().substring(0, 8));
                    et.setSelection(8);
                }
            } else {
                String pre = text.substring(0, pointIndex);
                String last = text.substring(pointIndex + 1, length);
                boolean isChange = false;
                if (pre.length() > 8) {
                    pre = pre.substring(0, 8);
                    isChange = true;
                }
                if (last.length() > pointSize) {
                    last = last.substring(0, pointSize);
                    isChange = true;
                }
                if (isChange) {
                    pre = pre + "." + last;
                    et.setText(pre);
                    et.setSelection(pre.length());
                }
            }
        } catch (Exception e) {
        }
    }

    //判断通知栏是否开启
    public static boolean isNotificationEnabled() {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            mAppOps = (AppOpsManager) AppContext.context().getSystemService(Context.APP_OPS_SERVICE);
        }
        ApplicationInfo appInfo = AppContext.context().getApplicationInfo();
        String pkg = AppContext.context().getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    //展示长度限制
    public static String handleText(String str, int maxLen) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int count = 0;
        int endIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            char item = str.charAt(i);
            if (item < 128) {
                count = count + 1;
            } else {
                count = count + 2;
            }
            if (maxLen == count) {
                endIndex = i + 1;
            } else if (item >= 128 && (maxLen + 1) == count) {
                endIndex = i;
            }
        }
        if (count <= maxLen) {
            return str;
        } else {
            return str.substring(0, endIndex);
        }
    }

    public static void limitInputLength(EditText et, int maxLength) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = Utils.handleText(s.toString(), 40);
                if (!content.equals(s.toString())) {
                    et.setText(content);
                    et.setSelection(content.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static InputFilter notEnterEmoji() {
        return new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };
    }



}
