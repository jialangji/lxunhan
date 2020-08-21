package com.ay.lxunhan.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ay.lxunhan.base.AppContext;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.UserInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.utils.db
 * @date 2020/7/1
 */
public class HistoryDao {
    private static volatile HistoryDao userDao;
    private SQLiteDatabase db;
    private MySqliteHelper helper;


    public static HistoryDao getInstance() {
        if (userDao == null) {
            synchronized (Contacts.class) {
                if (userDao == null) {
                    userDao = new HistoryDao(AppContext.context());
                }
            }
        }
        return userDao;
    }

    public HistoryDao(Context context) {
        helper = new MySqliteHelper(context);
    }

    /**
     * 历史搜索添加
     *
     * @param name
     */
    public void addHistory(String content) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", UserInfo.getInstance().getUserId());
        values.put("content",content);
        db.insert("history", null, values);
        db.close();
        deleteTen();
    }

    /**
     * 固定十条
     */
    public void deleteTen(){
        List<String> list=selectHistoryId();
        if (list.size()== 11){
            deleteReport(list.get(0));
        }
    }

    /**
     * 更新俩个数据之间的顺序
     */
    public void updateOrder(String content){
        //判断内容是否存在
        if (isExist(content)){//如果存在，删除数据库内原有内容，并从新添加
            deleteReport(getContentId(content));
            addHistory(content);
        }else { //如果不存在，则直接添加
            addHistory(content);
        }
    }

    public boolean isExist(String content){
        db = helper.getWritableDatabase();
        Cursor cursor = db.query("history",null,"userId = ?  and content=?",new String[]{UserInfo.getInstance().getUserId(),content},null,null,null);
        while (cursor.moveToNext()) {
            return true;
        }
        return false;
    }

    public String getContentId(String content){
        String id = null;
        db = helper.getWritableDatabase();
        Cursor cursor = db.query("history",null,"userId = ?  and content=?",new String[]{UserInfo.getInstance().getUserId(),content},null,null,null);
        while (cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndex("id"));
        }
        return id;
    }


    public void deleteAll(){
        db = helper.getWritableDatabase();
        db.delete("history", null,null);
        db.close();
    }


    /**
     * 删除报告
     */
    public void deleteReport(String id){
        db = helper.getWritableDatabase();
        db.delete("history", "id = ? ", new String[]{id});
        db.close();
    }

    /**
     * 查询历史记录id
     */
    public List<String> selectHistoryId() {
        List<String> list = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query("history",null,"userId = ?",new String[]{UserInfo.getInstance().getUserId()},null,null,null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("id"));
            list.add(name);
        }
        return list;
    }

    /**
     * 查询历史搜索表
     */
    public List<String> selectHistory() {
        List<String> list = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.query("history",null,"userId = ? ",new String[]{UserInfo.getInstance().getUserId()},null,null,null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("content"));
            list.add(name);
        }
        Collections.reverse(list);
        return list;
    }

}
