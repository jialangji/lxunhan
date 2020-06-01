package com.ay.lxunhan.utils.datepicker;

import android.content.Context;

import com.ay.lxunhan.R;
import com.ay.lxunhan.utils.StringUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jlj on 2019/3/19
 */

public class DateTimePickerUtil {
   private static String[] mMonthList1 = {StringUtil.getString(R.string.january), StringUtil.getString(R.string.february),
            StringUtil.getString(R.string.march), StringUtil.getString(R.string.april), StringUtil.getString(R.string.may), StringUtil.getString(R.string.june),
            StringUtil.getString(R.string.july), StringUtil.getString(R.string.august), StringUtil.getString(R.string.september), StringUtil.getString(R.string.october), StringUtil.getString(R.string.november),
            StringUtil.getString(R.string.december)};
    public static OptionsPickerView getSelectDatePicker(Context context, final int type, final DateTimePickerListener listener, int time) {

        List<String> yearList = new ArrayList<>();
        List<List<String>> monthList = new ArrayList<>();
        List<List<List<String>>> dayList = new ArrayList<>();
        List<String> yearMonth, monthDay;
        List<List<String>> yearMonthDay;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) - time;
        int mouth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.HOUR_OF_DAY);
        for (int i = time; i < time + 200; i++) {
            yearList.add(String.valueOf(i));
            yearMonth = new ArrayList<>();
            monthList.add(yearMonth);
            yearMonthDay = new ArrayList<>();
            dayList.add(yearMonthDay);
            for (int j = 0; j < 12; j++) {
                yearMonth.add(mMonthList1[j]);
                monthDay = new ArrayList<>();
                yearMonthDay.add(monthDay);
                int monthName = j + 1, daySize = 0;
                if (monthName == 1 || monthName == 3 || monthName == 5 || monthName == 7 || monthName == 8 || monthName == 10 || monthName == 12) {
                    daySize = 31;
                } else if (monthName == 4 || monthName == 6 || monthName == 9 || monthName == 11) {
                    daySize = 30;
                } else {//2月
                    if (monthName == 2) {
                        if ((i % 4 == 0 && i % 100 != 0) || i % 400 == 0) {//闰年29天
                            daySize = 29;
                        } else {
                            daySize = 28;
                        }
                    }
                }
                for (int k = 1; k <= daySize; k++) {
                    monthDay.add(String.valueOf(k));
                }
            }
        }

        OptionsPickerView pvOptionsTime = new OptionsPickerBuilder(context, (options1, option2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            if (listener != null) {
                String dateSelect = dayList.get(options1).get(option2).get(options3) + " " + monthList.get(options1).get(option2) + " " + yearList.get(options1);
                int monthName = option2 + 1;
                int dayName = options3 + 1;
                String mMonth, mDay;
                if (monthName < 10) {
                    mMonth = "0" + monthName;
                } else {
                    mMonth = monthName + "";
                }
                if (dayName < 10) {
                    mDay = "0" + dayName;
                } else {
                    mDay = dayName + "";
                }
                String dateForm = yearList.get(options1) + "-" + mMonth + "-" + mDay;
                listener.dateTimeResult(true, type, dateSelect, dateForm);
            }
        }, 2).setCancelText("").setSubCalSize(14).setLabels(StringUtil.getString(R.string.year), StringUtil.getString(R.string.mouth), StringUtil.getString(R.string.day))
                .setSelectOptions(year - 1, mouth, day)
                .build();
        pvOptionsTime.setPicker(yearList, monthList, dayList);
        return pvOptionsTime;
    }


    public static OptionsPickerView getAllDatePicker(Context context, final int type, final DateTimePickerListener listener) {
        final List<String> yearList = new ArrayList<>();
        final List<List<String>> monthList = new ArrayList<>();
        final List<List<List<String>>> dayList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        List<String> yearMonth, monthDay;
        List<List<String>> yearMonthDay;
        for (int i = year - 100; i <= year; i++) {
            yearList.add(String.valueOf(i));
            yearMonth = new ArrayList<>();
            monthList.add(yearMonth);

            yearMonthDay = new ArrayList<>();
            dayList.add(yearMonthDay);
            int mouth = calendar.get(Calendar.MONTH) + 1;
            if (i == year) {
                for (int j = 0; j < mouth; j++) {
                    yearMonth.add(mMonthList1[j]);
                    monthDay = new ArrayList<>();
                    yearMonthDay.add(monthDay);
                    int monthName = j + 1, daySize = 0;
                    if (monthName == 1 || monthName == 3 || monthName == 5 || monthName == 7 || monthName == 8 || monthName == 10 || monthName == 12) {
                        daySize = 31;
                    } else if (monthName == 4 || monthName == 6 || monthName == 9 || monthName == 11) {
                        daySize = 30;
                    } else {//2月
                        if (monthName == 2) {
                            if ((i % 4 == 0 && i % 100 != 0) || i % 400 == 0) {//闰年29天
                                daySize = 29;
                            } else {
                                daySize = 28;
                            }
                        }
                    }
                    if (monthName == mouth) {
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        for (int k = 1; k <= day; k++) {
                            monthDay.add(String.valueOf(k));
                        }
                    } else {
                        for (int k = 1; k <= daySize; k++) {
                            monthDay.add(String.valueOf(k));
                        }
                    }
                }
            } else {
                for (int j = 0; j < 12; j++) {
                    yearMonth.add(mMonthList1[j]);
                    monthDay = new ArrayList<>();
                    yearMonthDay.add(monthDay);
                    int monthName = j + 1, daySize = 0;
                    if (monthName == 1 || monthName == 3 || monthName == 5 || monthName == 7 || monthName == 8 || monthName == 10 || monthName == 12) {
                        daySize = 31;
                    } else if (monthName == 4 || monthName == 6 || monthName == 9 || monthName == 11) {
                        daySize = 30;
                    } else {//2月
                        if (monthName == 2) {
                            if ((i % 4 == 0 && i % 100 != 0) || i % 400 == 0) {//闰年29天
                                daySize = 29;
                            } else {
                                daySize = 28;
                            }
                        }
                    }
                    for (int k = 1; k <= daySize; k++) {
                        monthDay.add(String.valueOf(k));
                    }
                }
            }
        }
        OptionsPickerView pvOptionsTime = new OptionsPickerBuilder(context, (options1, option2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            if (listener != null) {
                String dateSelect = dayList.get(options1).get(option2).get(options3) + " " + monthList.get(options1).get(option2) + " " + yearList.get(options1);
                int monthName = option2 + 1;
                int dayName = options3 + 1;
                String mMonth, mDay;
                if (monthName < 10) {
                    mMonth = "0" + monthName;
                } else {
                    mMonth = monthName + "";
                }
                if (dayName < 10) {
                    mDay = "0" + dayName;
                } else {
                    mDay = dayName + "";
                }
                String dateForm = yearList.get(options1) + "-" + mMonth + "-" + mDay;
                listener.dateTimeResult(true, type, dateSelect, dateForm);
            }
        }, 2).setSelectOptions(yearList.size() -1)
                .build();
        pvOptionsTime.setPicker(yearList, monthList, dayList);
        return pvOptionsTime;
    }
}
