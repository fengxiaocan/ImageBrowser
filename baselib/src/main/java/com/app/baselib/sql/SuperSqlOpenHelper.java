package com.app.baselib.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedHashMap;

public class SuperSqlOpenHelper extends SQLiteOpenHelper {
	public static final  String SQL_ID_NAME = "_ID";
	public static final  String SQL_ID_EQUALS = "_ID=?";
	private ISqliteHelper mSqlite;
	
	/**
	 * 创建数据库
	 * 参数一：数据库名
	 * 参数二：模式，一般为MOE_PRIVATE
	 * 参数三：游标工厂对象，一般写null，表示系统自动提供
	 */
	public SuperSqlOpenHelper(
			Context context,ISqliteHelper sqlite
	)
	{
		super(context,sqlite.getDatabaseName(),null,sqlite.getDatabaseVersion());
		mSqlite = sqlite;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建数据库
		LinkedHashMap<String,SQLiteStorage> tabName = ISqliteHelper.TABLE_TAB_NAME;
		if (tabName != null) {
			for (String tableName : tabName.keySet()) {
				SqliteRoute.createTable(db,tableName,tabName.get(tableName));
			}
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
		mSqlite.onUpgrade(db,oldVersion,newVersion);
	}
}
