package com.app.baselib.sql;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.app.baselib.util.StringUtils;

import static com.app.baselib.sql.SuperSqlOpenHelper.SQL_ID_EQUALS;

/**
 * The type Sqlite super.
 *
 * @author noah
 * @email fengxiaocan @gmail.com
 * @create 31 /7/18
 * @desc ...
 */
public abstract class SqliteSuper extends SqliteRoute {
	public static final String SQL_ID_NAME = "_ID";
	/**
	 * The M sql id.
	 * 数据库id
	 */
	protected int mSqlId = -1;
	
	/**
	 * Gets sql id.
	 *
	 * @return the sql id
	 */
	public int getSqlId() {
		return mSqlId;
	}
	
	/**
	 * Sets sql id.
	 *
	 * @param sqlId the sql id
	 */
	public void setSqlId(int sqlId) {
		this.mSqlId = sqlId;
	}
	
	/**
	 * Save long.
	 * 保存到数据库中
	 *
	 * @return the long 获取插入到数据库的id
	 */
	public long save() {
		ContentValues value = new ContentValues();
		setContentValue(value);
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		long insert = db.insert(tableName(),null,value);
		db.close();
		return insert;
	}
	
	/**
	 * 保存或更新
	 *
	 * @return
	 */
	public long saveOrUpdata() {
		ContentValues value = new ContentValues();
		setContentValue(value);
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		long update = db.update(tableName(),value,SQL_ID_EQUALS,new String[]{String.valueOf(getSqlId())});
		if (update < 0) {
			update = db.insert(tableName(),null,value);
		}
		db.close();
		return update;
	}
	
	private String getSearchMajor(String[] keys) {
		if (keys == null || keys.length == 0) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0;i < keys.length;i++) {
			if (i != keys.length - 1) {
				builder.append(keys[i]);
				builder.append("=?&");
			} else {
				builder.append(keys[i]);
				builder.append("=?");
			}
		}
		return builder.toString();
	}
	
	/**
	 * 保存或更新
	 *
	 * @return
	 */
	public long saveOrUpdataByMajorKey(String[] majorKey,String[] majorValue) {
		ContentValues value = new ContentValues();
		setContentValue(value);
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		long update = db.update(tableName(),value,getSearchMajor(majorKey),majorValue);
		if (update < 0) {
			update = db.insert(tableName(),null,value);
		}
		db.close();
		return update;
	}
	
	/**
	 * 保存或更新
	 *
	 * @return
	 */
	public long saveOrUpdataByMajorKey(String majorKey,String majorValue) {
		ContentValues value = new ContentValues();
		setContentValue(value);
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		long update = db.update(tableName(),value,StringUtils.join(majorKey,"=?"),new String[]{
				majorValue
		});
		if (update < 0) {
			update = db.insert(tableName(),null,value);
		}
		db.close();
		return update;
	}
	
	/**
	 * Update int.
	 * 更新到数据库中
	 *
	 * @return the int
	 */
	public int update() {
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		ContentValues value = new ContentValues();
		setContentValue(value);
		int update = db.update(tableName(),value,SQL_ID_EQUALS,new String[]{String.valueOf(getSqlId())});
		db.close();
		return update;
	}
	
	/**
	 * Update int.
	 * 更新到数据库中
	 *
	 * @param whereClause the where clause 筛选目标="name=?"
	 * @param whereArgs the where args 筛选条件
	 * @return the int
	 */
	public int update(String whereClause,String[] whereArgs) {
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		ContentValues value = new ContentValues();
		setContentValue(value);
		int update = db.update(tableName(),value,whereClause,whereArgs);
		db.close();
		return update;
	}
	
	/**
	 * Delete int.
	 * 在数据库中删除
	 *
	 * @return the int
	 */
	public int delete() {
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		int delete = db.delete(tableName(),SQL_ID_EQUALS,new String[]{String.valueOf(getSqlId())});
		db.close();
		return delete;
	}
	
	/**
	 * Delete int.
	 * 按条件在数据库中删除
	 *
	 * @param whereClause the where clause
	 * @param whereArgs the where args
	 * @return the int
	 */
	public int delete(String whereClause,String[] whereArgs) {
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		int delete = db.delete(tableName(),whereClause,whereArgs);
		db.close();
		return delete;
	}
	
	@Override
	protected void findId(int result) {
		setSqlId(result);
	}
	
	@Override
	protected void findResult(String tabName,byte result) {
	
	}
	
	@Override
	protected void findResult(String tabName,short result) {
	
	}
	
	@Override
	protected void findResult(String tabName,int result) {
	
	}
	
	@Override
	protected void findResult(String tabName,long result) {
	
	}
	
	@Override
	protected void findResult(String tabName,float result) {
	
	}
	
	@Override
	protected void findResult(String tabName,double result) {
	
	}
	
	@Override
	protected void findResult(String tabName,boolean result) {
	
	}
}
