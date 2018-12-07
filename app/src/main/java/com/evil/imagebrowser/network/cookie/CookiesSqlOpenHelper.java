package com.evil.imagebrowser.network.cookie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CookiesSqlOpenHelper extends SQLiteOpenHelper {
    public static final String COOKIES_SQL_NAME = "CookiesSqlite.db";
    public static final String COOKIES_SQL_TABLE_NAME = "cookies";
    public static final String CREATETABLE = "CREATE TABLE " +
                                             COOKIES_SQL_TABLE_NAME +
                                             "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                             "name TEXT," +
                                             "value TEXT," +
                                             "expiresAt INTEGER," +
                                             "domain TEXT," +
                                             "path TEXT," +
                                             "cookieKey TEXT," +
                                             "secure INTEGER," +
                                             "httpOnly INTEGER," +
                                             "persistent INTEGER," +
                                             "hostOnly INTEGER)";
    private static final int COOKIE_VERSION = 1; //数据库版本

    /**
     * 创建数据库
     * 参数一：数据库名
     * 参数二：模式，一般为MOE_PRIVATE
     * 参数三：游标工厂对象，一般写null，表示系统自动提供
     */
    public CookiesSqlOpenHelper(
            Context context
    )
    {
        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类

        super(context,COOKIES_SQL_NAME,null,COOKIE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {

    }
}
