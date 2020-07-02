package com.ay.lxunhan.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dell on 2018/3/2.
 */

public class MySqliteHelper extends SQLiteOpenHelper{
    public static final String CREATE_STUDENT = "create table history ("

            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "

            + "userId varchar(100), "

            + "type varchar(10), " //1 首页，2视频 ，3 直播，4未关注好友

            + "content varchar(20))"; //搜索内容

    public MySqliteHelper(Context context) {
        super(context,"hlx",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
