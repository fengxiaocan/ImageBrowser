package com.app.baselib.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.baselib.util.Utils;

/**
 * The type Cookies sql helper.
 */
public class SqliteHelper extends ISqliteHelper {
	private static volatile SqliteHelper mInstance;
	protected SuperSqlOpenHelper mHelper;
	protected Context mContext;
	
	private SqliteHelper() {
		mContext = Utils.getContext();
	}
	
	static SqliteHelper getInstance() {
		if (mInstance == null) {
			synchronized (SqliteHelper.class) {
				if (mInstance == null) {
					mInstance = new SqliteHelper();
				}
			}
		}
		return mInstance;
	}
	
	public static void initDatabase(String databaseName,int version,ISqliteAdapter... support) {
		getInstance().setDatabaseName(databaseName);
		getInstance().setDatabaseVersion(version);
		getInstance().setSupport(support);
		getInstance().initDatabase();
	}
	
	/**
	 * Init.
	 */
	protected synchronized void initDatabase() {
		mHelper = getOpenHelper();
		SQLiteDatabase database = mHelper.getWritableDatabase();
		database.close();
	}
	
	@Override
	public synchronized SuperSqlOpenHelper getOpenHelper() {
		if (mHelper == null) {
			mHelper = new SuperSqlOpenHelper(mContext,this);
		}
		return mHelper;
	}
	
	@Override
	public synchronized SQLiteDatabase getWriteDatabase() {
		return getOpenHelper().getWritableDatabase();
	}
	
	@Override
	public synchronized SQLiteDatabase getReadDatabase() {
		return getOpenHelper().getReadableDatabase();
	}
	
	@Override
	protected void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
		if (TABLE_TAB_NAME != null) {
			for (String key : TABLE_TAB_NAME.keySet()) {
				SQLiteStorage storage = TABLE_TAB_NAME.get(key);
				if (storage.mAdapters.isNeedUpgrade(oldVersion,newVersion)) {
					//是否需要升级数据库
					if (storage.mAdapters.upgradeNeedDeleteSource()) {
						//升级数据库是否要删除源文件，true：删除旧数据，false：保留原数据
						SqliteRoute.deleteTable(db,storage.mAdapters.tableName());
						SqliteRoute.createTable(db,storage.mAdapters.tableName(),storage);
					} else {
						//不删除源文件更新数据库字段
						String tableName = storage.mAdapters.tableName();
						Cursor query = db.query(tableName,null,null,null,null,null,null);
						
						if (storage.tabNameMap != null && storage.tabNameMap.size() > 0) {
							for (String columnKey : storage.tabNameMap.keySet()) {
								//如果表中没有该列数据，就会创建该字段
								createTableColumn(db,query,tableName,columnKey,SqliteRoute.getSqlType(storage.tabNameMap.get(columnKey)));
							}
						}
						query.close();
					}
					
				}
			}
		}
	}
	
	private void createTableColumn(SQLiteDatabase db,Cursor cursor,String tableName,String tabNames,String type) {
		int name = cursor.getColumnIndex(tabNames);
		if (name < 0) {
			SqliteRoute.addTableColumn(db,tableName,tabNames,type);
		}
	}
}
