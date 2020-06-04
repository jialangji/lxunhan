package com.ay.lxunhan.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.AppContext;

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
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
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
