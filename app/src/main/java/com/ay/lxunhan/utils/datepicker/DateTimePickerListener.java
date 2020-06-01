package com.ay.lxunhan.utils.datepicker;

/**
 * Created by jlj on 2019/03/19
 */

public interface DateTimePickerListener {
    /**
     *
     * @param dateOrTime
     * date=true;time=false
     * @param type
     * 判断操作位置类型
     * @param result
     * dd MMM yy 或 hh:mm aa
     * @param result1
     * yyyy-MM-dd 或 HH:mm
     */
    void dateTimeResult(boolean dateOrTime, int type, String result, String result1);
}
