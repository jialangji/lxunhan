package com.ay.lxunhan.widget.azlist;

import android.text.TextUtils;

import com.ay.lxunhan.bean.FriendBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataUtils {
    public static List<AZItemEntity<FriendBean>> fillData(List<FriendBean> list) {
        List<AZItemEntity<FriendBean>> sortList = new ArrayList<>();
        for (FriendBean aDate : list) {
            AZItemEntity<FriendBean> item = new AZItemEntity<>();
            item.setValue(aDate);
            //汉字转换成拼音
            String pinyin =  PinyinUtils.getPingYin(aDate.getNickname());
            if (!TextUtils.isEmpty(pinyin) && pinyin.length() > 0) {
                //取第一个首字母
                String letters = pinyin.substring(0, 1).toUpperCase();
                // 正则表达式，判断首字母是否是英文字母
                if (letters.matches("[A-Z]")) {
                    item.setSortLetters(letters.toUpperCase());
                } else {
                    item.setSortLetters("#");
                }
                sortList.add(item);
            }
        }
        return sortList;

    }

    public static List<String> getLetters(List<AZItemEntity<FriendBean>> list) {
        List<String> sortList = new ArrayList<>();
        for (AZItemEntity<FriendBean> aDate : list) {
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(aDate.getValue().getNickname());
            //取第一个首字母
            String letters = pinyin.substring(0, 1).toUpperCase();
            sortList.add(letters);
        }
        //去重
        List<String> resultList = new ArrayList<>();
        Set<String> uniqueValues = new HashSet<>();
        for (String s : sortList) {
            if (uniqueValues.add(s)) {
                resultList.add(s);
            }
        }
        return resultList;

    }
}
