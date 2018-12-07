package com.evil.imagebrowser.network.cookie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.ashokvarma.bottomnavigation.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Cookies sql helper.
 */
public class CookiesSqlHelper {
    private static CookiesSqlHelper sHelper;
    private CookiesSqlOpenHelper mHelper;
    private Context mContext;

    /**
     * Instantiates a new Cookies sql helper.
     */
    private CookiesSqlHelper() {
        mContext = Utils.getContext();
        init();
    }

    /**
     * Gets helper.
     *
     * @return the helper
     */
    public static CookiesSqlHelper getHelper() {
        synchronized (CookiesSqlHelper.class) {
            if (sHelper == null) {
                sHelper = new CookiesSqlHelper();
            }
        }
        return sHelper;
    }

    /**
     * Init.
     */
    protected void init() {
        mHelper = getOpenHelper();
        SQLiteDatabase database = mHelper.getWritableDatabase();
        database.close();
    }

    /**
     * Gets open helper.
     *
     * @return the open helper
     */
    public CookiesSqlOpenHelper getOpenHelper() {
        if (mHelper == null) {
            mHelper = new CookiesSqlOpenHelper(mContext);
        }
        return mHelper;
    }

    /**
     * Gets write database.
     *
     * @return the write database
     */
    public SQLiteDatabase getWriteDatabase() {
        return getOpenHelper().getWritableDatabase();
    }

    /**
     * Gets read database.
     *
     * @return the read database
     */
    public SQLiteDatabase getReadDatabase() {
        return getOpenHelper().getReadableDatabase();
    }

    /**
     * Query all list.
     *
     * @return the list
     */
    public synchronized List<CookieInfo> queryAll() {
        SQLiteDatabase db = getWriteDatabase();
        Cursor cursor = db.query(COOKIES_SQL_TABLE_NAME,null,null,null,null,null,null);
        List<CookieInfo> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            CookieInfo info = new CookieInfo();
            int name = cursor.getColumnIndex("name");
            String nameValue = cursor.getString(name);
            info.setName(nameValue);
            int value = cursor.getColumnIndex("value");
            String valueValue = cursor.getString(value);
            info.setValue(valueValue);
            int expiresAt = cursor.getColumnIndex("expiresAt");
            long expiresAtValue = cursor.getLong(expiresAt);
            info.setExpiresAt(expiresAtValue);
            int domain = cursor.getColumnIndex("domain");
            String domainValue = cursor.getString(domain);
            info.setDomain(domainValue);
            int path = cursor.getColumnIndex("path");
            String pathValue = cursor.getString(path);
            info.setDomain(pathValue);
            int cookieKey = cursor.getColumnIndex("cookieKey");
            String cookieKeyValue = cursor.getString(cookieKey);
            info.setDomain(cookieKeyValue);
            int secure = cursor.getColumnIndex("secure");
            boolean secureValue = cursor.getInt(secure) == 1;
            info.setSecure(secureValue);
            int httpOnly = cursor.getColumnIndex("httpOnly");
            boolean httpOnlyValue = cursor.getInt(httpOnly) == 1;
            info.setHttpOnly(httpOnlyValue);
            int persistent = cursor.getColumnIndex("persistent");
            boolean persistentValue = cursor.getInt(persistent) == 1;
            info.setPersistent(persistentValue);
            int hostOnly = cursor.getColumnIndex("hostOnly");
            boolean hostOnlyValue = cursor.getInt(hostOnly) == 1;
            info.setPersistent(hostOnlyValue);
            list.add(info);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 单个插入
     *
     * @param info the info
     */
    public synchronized void insertSingle(CookieInfo info) {
        SQLiteDatabase db = getWriteDatabase();
        insert(db,info);
        db.close();
    }

    /**
     * Insert.
     *
     * @param infos the infos
     */
    public synchronized void insert(List<CookieInfo> infos) {
        SQLiteDatabase db = getWriteDatabase();
        for (CookieInfo info : infos) {
            insert(db,info);
        }
        db.close();
    }

    /**
     * Delete single.
     *
     * @param info the info
     */
    public void deleteSingle(CookieInfo info) {
        deleteSingle(info.getCookieKey());
    }

    /**
     * Clear.
     */
    public synchronized void clear() {
        SQLiteDatabase db = getWriteDatabase();
        db.execSQL("delete from " + COOKIES_SQL_TABLE_NAME);
        db.close();
    }

    /**
     * Delete.
     *
     * @param info the info
     */
    public synchronized void delete(List<CookieInfo> info) {
        SQLiteDatabase db = getWriteDatabase();
        for (CookieInfo cookieInfo : info) {
            delete(db,"cookieKey=? ",new String[]{cookieInfo.getCookieKey()});
        }
        db.close();
    }

    /**
     * Delete single.
     *
     * @param cookieKey the cookie key
     */
    public synchronized void deleteSingle(String cookieKey) {
        SQLiteDatabase db = getWriteDatabase();
        delete(db,"cookieKey=? ",new String[]{cookieKey});
        db.close();
    }

    private void delete(SQLiteDatabase db,String whereClause,String[] whereArgs) {
        db.delete(COOKIES_SQL_TABLE_NAME,whereClause,whereArgs);
    }

    private void insert(SQLiteDatabase db,CookieInfo info) {
        ContentValues values = putContentValue(info);
        db.insert(COOKIES_SQL_TABLE_NAME,null,values);
    }

    /**
     * Update single.
     *
     * @param info the info
     */
    public synchronized void updateSingle(CookieInfo info) {
        SQLiteDatabase db = getWriteDatabase();
        update(db,info);
        db.close();
    }

    /**
     * Update.
     *
     * @param infos the infos
     */
    public synchronized void update(List<CookieInfo> infos) {
        SQLiteDatabase db = getWriteDatabase();
        for (CookieInfo info : infos) {
            update(db,info);
        }
        db.close();
    }

    private void update(SQLiteDatabase db,CookieInfo info) {
        ContentValues values = putContentValue(info);
        db.update(COOKIES_SQL_TABLE_NAME,values,"cookieKey=? ",new String[]{info.getCookieKey()});
    }

    private ContentValues putContentValue(CookieInfo info) {
        ContentValues values = new ContentValues();
        values.put("name",info.getName());
        values.put("value",info.getValue());
        values.put("expiresAt",info.getExpiresAt());
        values.put("domain",info.getDomain());
        values.put("path",info.getPath());
        values.put("cookieKey",info.getCookieKey());
        values.put("secure",info.isSecure() ? 1 : 0);
        values.put("httpOnly",info.isHttpOnly() ? 1 : 0);
        values.put("persistent",info.isPersistent() ? 1 : 0);
        values.put("hostOnly",info.isHostOnly() ? 1 : 0);
        return values;
    }
}
