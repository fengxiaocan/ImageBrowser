package com.app.baselib.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.app.baselib.util.Utils;

import java.util.LinkedHashMap;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 31/7/18
 * @desc ...
 */
abstract class ISqliteHelper {
	protected String databaseName;
	protected int databaseVersion = 1;
	protected static final LinkedHashMap<String,SQLiteStorage> TABLE_TAB_NAME = new LinkedHashMap<>();
	
	public Context getContext() {
		return Utils.getContext();
	}
	
	protected synchronized void setSupport(ISqliteAdapter... support) {
		TABLE_TAB_NAME.clear();
		if(support != null && support.length > 0){
			for (ISqliteAdapter adapter : support) {
				SQLiteStorage storage = new SQLiteStorage(adapter);
				adapter.registerTabName(storage);
				TABLE_TAB_NAME.put(adapter.tableName(),storage);
			}
		}
	}
	
	public String getDatabaseName() {
		if (TextUtils.isEmpty(databaseName)) {
			databaseName = getClass().getSimpleName();
		}
		return databaseName;
	}
	
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
	public int getDatabaseVersion() {
		if (databaseVersion < 1) {
			databaseVersion = 1;
		}
		return databaseVersion;
	}
	
	public void setDatabaseVersion(int databaseVersion) {
		this.databaseVersion = databaseVersion;
	}
	
	public abstract SuperSqlOpenHelper getOpenHelper();
	
	public abstract SQLiteDatabase getWriteDatabase();
	
	public abstract SQLiteDatabase getReadDatabase();
	
	protected abstract void initDatabase();
	
	protected abstract void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion);
}
