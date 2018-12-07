package com.app.baselib.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.app.baselib.log.LogUtils;
import com.app.baselib.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.app.baselib.sql.SuperSqlOpenHelper.SQL_ID_EQUALS;
import static com.app.baselib.sql.SuperSqlOpenHelper.SQL_ID_NAME;

/**
 * The type Sqlite route.
 *
 * @author noah
 * @email fengxiaocan @gmail.com
 * @create 1 /8/18
 * @desc ...
 */
abstract class SqliteRoute extends ISqliteAdapter {
	/**
	 * Save long.
	 *
	 * @param <T> the type parameter
	 * @param supper the supper
	 * @return the long
	 */
	public static synchronized <T extends SqliteSuper> long save(T supper) {
		if (supper != null) {
			return supper.save();
		}
		return -1;
	}
	
	/**
	 * Save all.
	 *
	 * @param <T> the type parameter
	 * @param supper the supper
	 */
	public static synchronized <T extends SqliteSuper> void saveAll(T... supper) {
		if (supper != null) {
			SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
			for (T aSuper : supper) {
				ContentValues value = new ContentValues();
				aSuper.setContentValue(value);
				long insert = db.insert(aSuper.tableName(),null,value);
			}
			db.close();
		}
	}
	
	/**
	 * Save all.
	 *
	 * @param <T> the type parameter
	 * @param list the list
	 */
	public static synchronized <T extends SqliteSuper> void saveAll(List<T> list) {
		if (list != null && list.size() > 0) {
			SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
			StringBuilder builder = new StringBuilder("INSERT INTO ");
			T t = list.get(0);
			String tableName = t.tableName();
			builder.append(tableName);
			for (int i = 0;i < list.size();i++) {
				T t1 = list.get(i);
				if (i == 0) {
					ContentValues value = new ContentValues();
					t1.setContentValue(value);
					builder.append("(");
					for (String key : value.keySet()) {
						builder.append(key);
						builder.append(",");
					}
					builder.delete(builder.length() - 1,builder.length());
					builder.append(") VALUES (");
					
					for (String key : value.keySet()) {
						Object o = value.get(key);
						if (o instanceof Boolean) {
							builder.append("'");
							builder.append(((Boolean)o).booleanValue() ? 1 : 0);
							builder.append("'");
							builder.append(",");
						} else {
							builder.append("'");
							builder.append(o);
							builder.append("'");
							builder.append(",");
						}
					}
					builder.delete(builder.length() - 1,builder.length());
					builder.append("),");
				} else {
					insert(t1,builder);
				}
			}
			builder.delete(builder.length() - 1,builder.length());
			try {
				db.execSQL(builder.toString());
			} catch (SQLException e) {
				LogUtils.e("noah",e.getMessage());
				e.printStackTrace();
			}
			db.close();
		}
	}
	
	/**
	 * Insert.
	 *
	 * @param <T> the type parameter
	 * @param aSuper the a super
	 * @param builder the builder
	 */
	static synchronized <T extends SqliteSuper> void insert(T aSuper,StringBuilder builder) {
		ContentValues value = new ContentValues();
		aSuper.setContentValue(value);
		builder.append("(");
		for (String key : value.keySet()) {
			Object o = value.get(key);
			if (o instanceof Boolean) {
				builder.append("'");
				builder.append(((Boolean)o).booleanValue() ? 1 : 0);
				builder.append("'");
				builder.append(",");
			} else {
				builder.append("'");
				builder.append(o);
				builder.append("'");
				builder.append(",");
			}
		}
		builder.delete(builder.length() - 1,builder.length());
		builder.append("),");
	}
	
	/**
	 * Update.
	 *
	 * @param <T> the type parameter
	 * @param list the list
	 */
	public static synchronized <T extends SqliteSuper> void update(List<T> list) {
		if (list != null) {
			SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
			for (T aSuper : list) {
				ContentValues value = new ContentValues();
				aSuper.setContentValue(value);
				db.update(aSuper.tableName(),value,SQL_ID_EQUALS,new String[]{
						String.valueOf(aSuper.getSqlId())
				});
			}
			db.close();
		}
	}
	
	/**
	 * Update.
	 *
	 * @param <T> the type parameter
	 * @param list the list
	 * @param whereClause the where clause
	 * @param whereArgs the where args
	 */
	public static synchronized <T extends SqliteSuper> void update(List<T> list,String whereClause,String[] whereArgs) {
		if (list != null) {
			SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
			for (T aSuper : list) {
				ContentValues value = new ContentValues();
				aSuper.setContentValue(value);
				db.update(aSuper.tableName(),value,whereClause,whereArgs);
			}
			db.close();
		}
	}
	
	/**
	 * Update.
	 *
	 * @param <T> the type parameter
	 * @param list the list
	 * @param bridge the bridge
	 */
	public static synchronized <T extends SqliteSuper> void update(List<T> list,IExecSQLBridge bridge) {
		if (list != null && bridge != null) {
			SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
			for (T aSuper : list) {
				ContentValues value = new ContentValues();
				aSuper.setContentValue(value);
				db.update(aSuper.tableName(),value,bridge.execSqlWhereClause(aSuper),bridge.execSqlWhereArgs(aSuper));
			}
			db.close();
		}
	}
	
	/**
	 * Update.
	 *
	 * @param <T> the type parameter
	 * @param suppers the suppers
	 */
	public static synchronized <T extends SqliteSuper> void update(T suppers) {
		if (suppers != null) {
			SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
			ContentValues value = new ContentValues();
			suppers.setContentValue(value);
			db.update(suppers.tableName(),value,SQL_ID_EQUALS,new String[]{
					String.valueOf(suppers.getSqlId())
			});
			db.close();
		}
	}
	
	/**
	 * Update.
	 *
	 * @param <T> the type parameter
	 * @param supper the supper
	 * @param whereClause the where clause
	 * @param whereArgs the where args
	 */
	public static synchronized <T extends SqliteSuper> void update(T supper,String whereClause,String[] whereArgs) {
		if (supper != null) {
			SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
			ContentValues value = new ContentValues();
			supper.setContentValue(value);
			db.update(supper.tableName(),value,whereClause,whereArgs);
			db.close();
		}
	}
	
	/**
	 * Delete.
	 *
	 * @param <T> the type parameter
	 * @param list the list
	 * @param whereClause the where clause
	 * @param whereArgs the where args
	 */
	public static synchronized <T extends SqliteSuper> void delete(List<T> list,String whereClause,String[] whereArgs) {
		if (list != null) {
			SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
			for (T aSuper : list) {
				db.delete(aSuper.tableName(),whereClause,whereArgs);
			}
			db.close();
		}
	}
	
	/**
	 * Create table.
	 *
	 * @param db the db
	 * @param tableName the tableName
	 */
	public static synchronized void createTable(SQLiteDatabase db,String tableName,SQLiteStorage storage) {
		if (storage != null && storage.tabNameMap != null && storage.tabNameMap.size() > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("CREATE TABLE IF NOT EXISTS ");//不存在才创建
			sb.append(tableName);
			sb.append("(");
			sb.append(SQL_ID_NAME);
			sb.append(" INTEGER PRIMARY KEY AUTOINCREMENT ");
			for (String key : storage.tabNameMap.keySet()) {
				//NULL、INTEGER、REAL（浮点数字）、TEXT(字符串文本)和BLOB(二进制对象)
				//INTEGER –整数，对应Java 的byte、short、int 和long。
				//REAL – 小数，对应Java 的float 和double。
				//TEXT – 字串，对应Java 的String。
				sqlCreateList(sb,key,storage.tabNameMap.get(key));
			}
			sb.append(")");
			try {
				db.execSQL(sb.toString());
			} catch (SQLException e) {
				LogUtils.e("sqlitehelper",e.getMessage());
			}
		}
	}
	
	/**
	 * Sql create list.
	 *
	 * @param buffer the buffer
	 * @param tabName the tab name
	 * @param type the type
	 */
	protected static void sqlCreateList(StringBuffer buffer,String tabName,int type) {
		if (!TextUtils.isEmpty(tabName)) {
			buffer.append(",");
			buffer.append(tabName);
			buffer.append(" ");
			buffer.append(getSqlType(type));
		}
	}
	
	/**
	 * Gets sql type.
	 *
	 * @param type the type
	 * @return the sql type
	 */
	protected static @SQLiteType
	String getSqlType(int type) {
		switch (type) {
			default:
			case IBaseType.STRING:
			case IBaseType.CHAR:
				return SQLiteType.TEXT;
			case IBaseType.INT:
			case IBaseType.LONG:
			case IBaseType.BOOLEAN:
			case IBaseType.SHORT:
			case IBaseType.BYTE:
				return SQLiteType.INTEGER;
			case IBaseType.DOUBLE:
			case IBaseType.FLOAT:
				return SQLiteType.REAL;
		}
	}
	
	/**
	 * Clear table.
	 *
	 * @param tableName the table name
	 */
	public static synchronized void clearTable(String tableName) {
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		db.delete(tableName,null,null);
		db.close();
	}
	
	/**
	 * Clear table.
	 *
	 * @param tableNames the table names
	 */
	public static synchronized void clearTable(String... tableNames) {
		if (tableNames != null) {
			SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
			for (String tableName : tableNames) {
				db.delete(tableName,null,null);
			}
			db.close();
		}
	}
	
	/**
	 * 删除表
	 *
	 * @param db the db
	 * @param tableName 表名称
	 */
	public static synchronized void deleteTable(SQLiteDatabase db,String tableName) {
		try {
			db.execSQL("DROP TABLE IF EXISTS " + tableName);
		} catch (Exception e) {
			LogUtils.e("sqlitehelper",e.getMessage());
		}
	}
	
	/**
	 * 删除表
	 *
	 * @param db the db
	 * @param tableNames 表名称
	 */
	public static synchronized void deleteTable(SQLiteDatabase db,String... tableNames) {
		if (tableNames != null) {
			for (String tableName : tableNames) {
				try {
					db.execSQL("DROP TABLE IF EXISTS " + tableName);
				} catch (Exception e) {
					LogUtils.e("sqlitehelper",e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Copy table.
	 *
	 * @param db the db
	 * @param newTableName the new table name
	 * @param oldTableName the old table name
	 */
	public static synchronized void copyTable(SQLiteDatabase db,String newTableName,String oldTableName) {
		try {
			db.execSQL(StringUtils.join("CREATE TABLE ",newTableName," AS SELECT * FROM ",oldTableName));
		} catch (Exception e) {
			LogUtils.e("sqlitehelper",e.getMessage());
		}
	}
	
	/**
	 * Copy table.
	 *
	 * @param newTableName the new table name
	 * @param oldTableName the old table name
	 */
	public static synchronized void copyTable(String newTableName,String oldTableName) {
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		try {
			db.execSQL(StringUtils.join("CREATE TABLE ",newTableName," AS SELECT * FROM ",oldTableName));
		} catch (Exception e) {
			LogUtils.e("sqlitehelper",e.getMessage());
		}
		db.close();
	}
	
	/**
	 * Gets table count.
	 *
	 * @param tableName the table name
	 */
	public static synchronized void getTableCount(String tableName) {
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		String sql = StringUtils.join("SELECT COUNT(*) AS TOTAL FROM ",tableName);
		try {
			db.execSQL(sql);
		} catch (Exception e) {
			LogUtils.e("sqlitehelper",e.getMessage());
		}
		db.close();
	}
	
	/**
	 * Add table column.
	 *
	 * @param tableName the table name
	 * @param column the column
	 */
	public static synchronized void addTableColumn(String tableName,String column) {
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		String sql = StringUtils.join("ALTER TABLE ",tableName," ADD COLUMN ",column," ",SQLiteType.TEXT);
		try {
			db.execSQL(sql);
		} catch (Exception e) {
			LogUtils.e("sqlitehelper",e.getMessage());
		}
		db.close();
	}
	
	/**
	 * Add table column.
	 *
	 * @param db the db
	 * @param tableName the table name
	 * @param column the column
	 * @param type the type
	 */
	public static synchronized void addTableColumn(SQLiteDatabase db,String tableName,String column,@SQLiteType String type) {
		String sql = StringUtils.join("ALTER TABLE ",tableName," ADD COLUMN ",column," ",type);
		try {
			db.execSQL(sql);
		} catch (Exception e) {
			LogUtils.e("sqlitehelper",e.getMessage());
		}
	}
	
	/**
	 * Add table columns.
	 *
	 * @param tableName the table name
	 * @param columns the columns
	 */
	public static synchronized void addTableColumns(String tableName,String... columns) {
		if (columns != null) {
			SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
			for (String column : columns) {
				try {
					String sql = StringUtils.join("ALTER TABLE ",tableName," ADD COLUMN ",column," ",SQLiteType.TEXT);
					db.execSQL(sql);
				} catch (Exception e) {
					LogUtils.e("sqlitehelper",e.getMessage());
				}
			}
			db.close();
		}
	}
	
	/**
	 * Add table column.
	 *
	 * @param tableName 表名
	 * @param column 列名称
	 * @param type 该类的存储数据类型：VARCHAR
	 */
	public static synchronized void addTableColumn(String tableName,String column,@SQLiteType String type) {
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		try {
			db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + column + " " + type);
		} catch (Exception e) {
			LogUtils.e("sqlitehelper",e.getMessage());
		}
		db.close();
	}
	
	/**
	 * Delete.
	 *
	 * @param <T> the type parameter
	 * @param list the list
	 * @param bridge the bridge
	 */
	public static synchronized <T extends SqliteSuper> void delete(List<T> list,IExecSQLBridge bridge) {
		if (list != null && bridge != null) {
			SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
			for (T aSuper : list) {
				db.delete(aSuper.tableName(),bridge.execSqlWhereClause(aSuper),bridge.execSqlWhereArgs(aSuper));
			}
			db.close();
		}
	}
	
	/**
	 * Delete.
	 *
	 * @param <T> the type parameter
	 * @param list the list
	 */
	public static synchronized <T extends SqliteSuper> void delete(List<T> list) {
		if (list != null) {
			SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
			for (T aSuper : list) {
				db.delete(aSuper.tableName(),SQL_ID_EQUALS,new String[]{
						String.valueOf(aSuper.getSqlId())
				});
			}
			db.close();
		}
	}
	
	/**
	 * Find all list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param selection the selection
	 * @param selectionArgs the selection args
	 * @param orderBy the order by
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findAll(
			String tableName,String[] columns,String selection,String[] selectionArgs,String orderBy,CreateBuilder<T> builder
	)
	{
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		Cursor cursor = db.query(tableName,columns,selection,selectionArgs,null,null,orderBy);
		List<T> list = new ArrayList<>();
		
		SQLiteStorage storage = SqliteHelper.TABLE_TAB_NAME.get(tableName);
		if (storage != null && storage.tabNameMap != null && storage.tabNameMap.size() > 0) {
			while (cursor.moveToNext()) {
				T t = findCursor(storage.tabNameMap,builder,cursor);
				list.add(t);
			}
		}
		cursor.close();
		db.close();
		return list;
	}
	
	/**
	 * Find all list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param selection the selection
	 * @param selectionArgs the selection args
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findAll(
			String tableName,String selection,String[] selectionArgs,CreateBuilder<T> builder
	)
	{
		return findAll(tableName,null,selection,selectionArgs,null,builder);
	}
	
	/**
	 * Find all list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findAll(
			String tableName,CreateBuilder<T> builder
	)
	{
		return findAll(tableName,null,null,null,null,builder);
	}
	
	/**
	 * Find like list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param where the where
	 * @param like the like
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findLike(
			String tableName,String where,String like,CreateBuilder<T> builder
	)
	{
		return findLike(tableName,null,where,like,null,false,0,0,builder);
	}
	
	/**
	 * Find in list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param where the where
	 * @param in the in
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findIn(
			String tableName,String where,String[] in,CreateBuilder<T> builder
	)
	{
		return findIn(tableName,null,where,in,null,false,0,0,builder);
	}
	
	/**
	 * Find like list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param like the like
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findLike(
			String tableName,String[] columns,String where,String like,CreateBuilder<T> builder
	)
	{
		return findLike(tableName,columns,where,like,null,false,0,0,builder);
	}
	
	/**
	 * Find in list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param in the in
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findIn(
			String tableName,String[] columns,String where,String[] in,CreateBuilder<T> builder
	)
	{
		return findIn(tableName,columns,where,in,null,false,0,0,builder);
	}
	
	/**
	 * Find like list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param like the like
	 * @param orderBy the order by
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findLike(
			String tableName,String[] columns,String where,String like,String orderBy,CreateBuilder<T> builder
	)
	{
		return findLike(tableName,columns,where,like,orderBy,false,0,0,builder);
	}
	
	/**
	 * Find in list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param in the in
	 * @param orderBy the order by
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findIn(
			String tableName,String[] columns,String where,String[] in,String orderBy,CreateBuilder<T> builder
	)
	{
		return findIn(tableName,columns,where,in,orderBy,false,0,0,builder);
	}
	
	/**
	 * Find in list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param where the where
	 * @param in the in
	 * @param orderBy the order by
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findIn(
			String tableName,String where,String[] in,String orderBy,CreateBuilder<T> builder
	)
	{
		return findIn(tableName,null,where,in,orderBy,false,0,0,builder);
	}
	
	/**
	 * Find like list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param like the like
	 * @param orderBy the order by
	 * @param limit the limit
	 * @param offset the offset
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findLike(
			String tableName,String[] columns,String where,String like,String orderBy,int limit,int offset,CreateBuilder<T> builder
	)
	{
		return findLike(tableName,columns,where,like,orderBy,false,limit,offset,builder);
	}
	
	/**
	 * Find in list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param in the in
	 * @param orderBy the order by
	 * @param limit the limit
	 * @param offset the offset
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findIn(
			String tableName,String[] columns,String where,String[] in,String orderBy,int limit,int offset,CreateBuilder<T> builder
	)
	{
		return findIn(tableName,columns,where,in,orderBy,false,limit,offset,builder);
	}
	
	/**
	 * Find like list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param like the like
	 * @param orderBy the order by
	 * @param limit the limit
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findLike(
			String tableName,String[] columns,String where,String like,String orderBy,int limit,CreateBuilder<T> builder
	)
	{
		return findLike(tableName,columns,where,like,orderBy,false,limit,0,builder);
	}
	
	/**
	 * Find in list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param in the in
	 * @param orderBy the order by
	 * @param limit the limit
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findIn(
			String tableName,String[] columns,String where,String[] in,String orderBy,int limit,CreateBuilder<T> builder
	)
	{
		return findIn(tableName,columns,where,in,orderBy,false,limit,0,builder);
	}
	
	
	/**
	 * Find order list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param selectionArgs the selection args
	 * @param orderBy the order by
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findOrder(
			String tableName,String[] columns,String where,String[] selectionArgs,String orderBy,CreateBuilder<T> builder
	)
	{
		return find(tableName,columns,where,selectionArgs,orderBy,false,0,0,builder);
	}
	
	
	/**
	 * Find order list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param where the where
	 * @param selectionArgs the selection args
	 * @param orderBy the order by
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findOrder(
			String tableName,String where,String[] selectionArgs,String orderBy,CreateBuilder<T> builder
	)
	{
		return find(tableName,null,where,selectionArgs,orderBy,false,0,0,builder);
	}
	
	/**
	 * Find order list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param selectionArgs the selection args
	 * @param orderBy the order by
	 * @param isDesc the is desc
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findOrder(
			String tableName,String[] columns,String where,String[] selectionArgs,String orderBy,boolean isDesc,CreateBuilder<T> builder
	)
	{
		return find(tableName,columns,where,selectionArgs,orderBy,isDesc,0,0,builder);
	}
	
	
	/**
	 * Find order list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param selectionArgs the selection args
	 * @param orderBy the order by
	 * @param isDesc the is desc
	 * @param limit the limit
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findOrder(
			String tableName,String[] columns,String where,String[] selectionArgs,String orderBy,boolean isDesc,int limit,CreateBuilder<T> builder
	)
	{
		return find(tableName,columns,where,selectionArgs,orderBy,isDesc,limit,0,builder);
	}
	
	/**
	 * Find order list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param selectionArgs the selection args
	 * @param orderBy the order by
	 * @param isDesc the is desc
	 * @param limit the limit
	 * @param offset the offset
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findOrder(
			String tableName,String[] columns,String where,String[] selectionArgs,String orderBy,boolean isDesc,int limit,int offset,CreateBuilder<T> builder
	)
	{
		return find(tableName,columns,where,selectionArgs,orderBy,isDesc,limit,offset,builder);
	}
	
	/**
	 * Find like list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param like the like
	 * @param orderBy the order by
	 * @param isDesc the is desc
	 * @param limit the limit
	 * @param offset the offset
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findLike(
			String tableName,String[] columns,String where,String like,String orderBy,boolean isDesc,int limit,int offset,CreateBuilder<T> builder
	)
	{
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		String string = buildQueryString(tableName,columns,where,like,orderBy,isDesc,limit,offset);
		Cursor cursor = db.rawQuery(string,null);
		List<T> list = new ArrayList<>();
		SQLiteStorage storage = SqliteHelper.TABLE_TAB_NAME.get(tableName);
		if (storage != null && storage.tabNameMap != null && storage.tabNameMap.size() > 0) {
			while (cursor.moveToNext()) {
				T t = findCursor(storage.tabNameMap,builder,cursor);
				list.add(t);
			}
		}
		cursor.close();
		db.close();
		return list;
	}
	
	/**
	 * Find in list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param in the in
	 * @param orderBy the order by
	 * @param isDesc the is desc
	 * @param limit the limit
	 * @param offset the offset
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> findIn(
			String tableName,String[] columns,String where,String[] in,String orderBy,boolean isDesc,int limit,int offset,CreateBuilder<T> builder
	)
	{
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		String string = buildQueryString(tableName,columns,where,in,orderBy,isDesc,limit,offset);
		Cursor cursor = db.rawQuery(string,null);
		List<T> list = new ArrayList<>();
		SQLiteStorage storage = SqliteHelper.TABLE_TAB_NAME.get(tableName);
		if (storage != null && storage.tabNameMap != null && storage.tabNameMap.size() > 0) {
			while (cursor.moveToNext()) {
				T t = findCursor(storage.tabNameMap,builder,cursor);
				list.add(t);
			}
		}
		cursor.close();
		db.close();
		return list;
	}
	
	/**
	 * Find list.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param where the where
	 * @param selectionArgs the selection args
	 * @param orderBy the order by
	 * @param isDesc the is desc
	 * @param limit the limit
	 * @param offset the offset
	 * @param builder the builder
	 * @return the list
	 */
	public static synchronized <T extends SqliteSuper> List<T> find(
			String tableName,String[] columns,String where,String[] selectionArgs,String orderBy,boolean isDesc,int limit,int offset,CreateBuilder<T> builder
	)
	{
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		String string = buildQueryString(tableName,columns,where,"",orderBy,isDesc,limit,offset);
		Cursor cursor = db.rawQuery(string,selectionArgs);
		List<T> list = new ArrayList<>();
		SQLiteStorage storage = SqliteHelper.TABLE_TAB_NAME.get(tableName);
		if (storage != null && storage.tabNameMap != null && storage.tabNameMap.size() > 0) {
			while (cursor.moveToNext()) {
				T t = findCursor(storage.tabNameMap,builder,cursor);
				list.add(t);
			}
		}
		cursor.close();
		db.close();
		return list;
	}
	
	public static synchronized <T extends SqliteSuper> T findOne(
			String tableName,String[] columns,String where,String[] selectionArgs,String orderBy,boolean isDesc,int limit,int offset,CreateBuilder<T> builder
	)
	{
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = SqliteHelper.getInstance().getWriteDatabase();
			String string = buildQueryString(tableName,columns,where,"",orderBy,isDesc,limit,offset);
			cursor = db.rawQuery(string,selectionArgs);
			SQLiteStorage storage = SqliteHelper.TABLE_TAB_NAME.get(tableName);
			if (storage != null && storage.tabNameMap != null && storage.tabNameMap.size() > 0) {
				if (cursor.moveToNext()) {
					T t = findCursor(storage.tabNameMap,builder,cursor);
					return t;
				}
			}
			return null;
		} catch (Exception e) {
			LogUtils.e("noah",e.getMessage());
			e.printStackTrace();
			return null;
		}finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		}
	}
	
	public static synchronized <T extends SqliteSuper> List<T> find(
			String tableName,String[] columns,String where,String[] selectionArgs,CreateBuilder<T> builder
	)
	{
		
		return find(tableName,columns,where,selectionArgs,null,false,0,0,builder);
	}
	
	public static synchronized <T extends SqliteSuper> List<T> find(
			String tableName,String where,String[] selectionArgs,CreateBuilder<T> builder
	)
	{
		
		return find(tableName,null,where,selectionArgs,null,false,0,0,builder);
	}
	
	public static synchronized <T extends SqliteSuper> T findLast(
			String tableName,CreateBuilder<T> builder
	)
	{
		
		return findOne(tableName,null,null,null,null,true,0,0,builder);
	}
	
	public static synchronized <T extends SqliteSuper> T findFrist(
			String tableName,CreateBuilder<T> builder
	)
	{
		
		return findOne(tableName,null,null,null,null,false,0,0,builder);
	}
	
	
	/**
	 * Build query string string.
	 *
	 * @param tables the tables
	 * @param columns the columns
	 * @param where the where
	 * @param like the like
	 * @param orderBy 排序的目标列名称
	 * @param isDesc 是否倒序
	 * @param limit the limit
	 * @param offset the offset
	 * @return string
	 */
	protected static String buildQueryString(
			String tables,String[] columns,String where,String like,String orderBy,boolean isDesc,int limit,int offset
	)
	{
		
		StringBuilder query = new StringBuilder(120);
		
		query.append("SELECT ");
		if (columns != null && columns.length != 0) {
			appendColumns(query,columns);
		} else {
			query.append("* ");
		}
		query.append("FROM ");
		query.append(tables);
		appendClause(query," WHERE ",where);
		if (!TextUtils.isEmpty(like)) {
			appendClause(query," LIKE ",StringUtils.join("'%",like,"%'"));
		}
		appendClause(query," ORDER BY ",orderBy);
		if (!TextUtils.isEmpty(orderBy)) {
			query.append(isDesc ? " DESC " : " ASC ");
		}
		if (limit > 0) {
			appendClause(query," LIMIT ",String.valueOf(limit));
			if (offset > 0) {
				appendClause(query," OFFSET ",String.valueOf(offset));
			}
		}
		return query.toString();
	}
	
	/**
	 * Build query string string.
	 *
	 * @param tables the tables
	 * @param columns the columns
	 * @param where the where
	 * @param in the in
	 * @param orderBy the order by
	 * @param isDesc the is desc
	 * @param limit the limit
	 * @param offset the offset
	 * @return the string
	 */
	protected static String buildQueryString(
			String tables,String[] columns,String where,String[] in,String orderBy,boolean isDesc,int limit,int offset
	)
	{
		
		StringBuilder query = new StringBuilder(120);
		
		query.append("SELECT ");
		if (columns != null && columns.length != 0) {
			appendColumns(query,columns);
		} else {
			query.append("* ");
		}
		query.append("FROM ");
		query.append(tables);
		appendClause(query," WHERE ",where);
		if (in != null && in.length > 0) {
			StringBuilder builder = new StringBuilder("(");
			for (int i = 0;i < in.length;i++) {
				if (i != 0) {
					builder.append(",");
				}
				builder.append("'");
				builder.append(in[i]);
				builder.append("'");
			}
			builder.append(")");
			appendClause(query," IN ",builder.toString());
		}
		appendClause(query," ORDER BY ",orderBy);
		if (!TextUtils.isEmpty(orderBy)) {
			query.append(isDesc ? " DESC " : " ASC ");
		}
		if (limit > 0) {
			appendClause(query," LIMIT ",String.valueOf(limit));
			if (offset > 0) {
				appendClause(query," OFFSET ",String.valueOf(offset));
			}
		}
		return query.toString();
	}
	
	/**
	 * Add the names that are non-null in columns to s, separating
	 * them with commas.
	 *
	 * @param s the s
	 * @param columns the columns
	 */
	protected static void appendColumns(StringBuilder s,String[] columns) {
		int n = columns.length;
		for (int i = 0;i < n;i++) {
			String column = columns[i];
			if (column != null) {
				if (i > 0) {
					s.append(", ");
				}
				s.append(column);
			}
		}
		s.append(' ');
	}
	
	private static void appendClause(StringBuilder s,String name,String clause) {
		if (!TextUtils.isEmpty(clause)) {
			s.append(name);
			s.append(clause);
		}
	}
	
	/**
	 * Find by id t.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param sqlId the sql id
	 * @param builder the builder
	 * @return the t
	 */
	public static <T extends SqliteSuper> T findById(
			String tableName,int sqlId,CreateBuilder<T> builder
	)
	{
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		Cursor cursor = db.query(tableName,null,SQL_ID_EQUALS,new String[]{String.valueOf(sqlId)},null,null,null);
		SQLiteStorage storage = SqliteHelper.TABLE_TAB_NAME.get(tableName);
		if (storage != null && storage.tabNameMap != null && storage.tabNameMap.size() > 0) {
			if (cursor.moveToNext()) {
				T t = findCursor(storage.tabNameMap,builder,cursor);
				cursor.close();
				db.close();
				return t;
			}
		}
		cursor.close();
		db.close();
		return null;
	}
	
	/**
	 * Gets count.
	 *
	 * @param tableName the table name
	 * @return the count
	 */
	public static int getCount(
			String tableName
	)
	{
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		Cursor cursor = db.query(tableName,null,null,null,null,null,null);
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}
	
	/**
	 * Gets count.
	 *
	 * @param tableName the table name
	 * @param selection the selection
	 * @param selectionArgs the selection args
	 * @return the count
	 */
	public static int getCount(
			String tableName,String selection,String[] selectionArgs
	)
	{
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		Cursor cursor = db.query(tableName,null,selection,selectionArgs,null,null,null);
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}
	
	/**
	 * Find by sql t.
	 *
	 * @param <T> the type parameter
	 * @param tableName the table name
	 * @param columns the columns
	 * @param selection the selection
	 * @param selectionArgs the selection args
	 * @param orderBy the order by
	 * @param builder the builder
	 * @return the t
	 */
	public static <T extends SqliteSuper> T findBySql(
			String tableName,String[] columns,String selection,String[] selectionArgs,String orderBy,CreateBuilder<T> builder
	)
	{
		SQLiteDatabase db = SqliteHelper.getInstance().getWriteDatabase();
		Cursor cursor = db.query(tableName,columns,selection,selectionArgs,null,null,orderBy);
		SQLiteStorage storage = SqliteHelper.TABLE_TAB_NAME.get(tableName);
		if (storage != null && storage.tabNameMap != null && storage.tabNameMap.size() > 0) {
			if (cursor.moveToNext()) {
				T t = findCursor(storage.tabNameMap,builder,cursor);
				cursor.close();
				db.close();
				return t;
			}
		}
		cursor.close();
		db.close();
		return null;
	}
	
	/**
	 * Find cursor t.
	 *
	 * @param <T> the type parameter
	 * @param map the map
	 * @param builder the builder
	 * @param cursor the cursor
	 * @return the t
	 */
	static <T extends SqliteSuper> T findCursor(LinkedHashMap<String,Integer> map,CreateBuilder<T> builder,Cursor cursor) {
		int id = cursor.getInt(0);
		T t = builder.builder();
		t.findId(id);
		for (String key : map.keySet()) {
			int name = cursor.getColumnIndex(key);
			if (name >= 0) {
				switch (map.get(key)) {
					default:
					case IBaseType.STRING:
						String value1 = cursor.getString(name);
						t.findResult(key,value1);
						break;
					case IBaseType.INT:
						int value2 = cursor.getInt(name);
						t.findResult(key,value2);
						break;
					case IBaseType.LONG:
						long value3 = cursor.getLong(name);
						t.findResult(key,value3);
						break;
					case IBaseType.BOOLEAN:
						int value4 = cursor.getInt(name);
						t.findResult(key,value4 == 1);
						break;
					case IBaseType.DOUBLE:
						double value5 = cursor.getDouble(name);
						t.findResult(key,value5);
						break;
					case IBaseType.FLOAT:
						float value6 = cursor.getFloat(name);
						t.findResult(key,value6);
						break;
					case IBaseType.SHORT:
						short value7 = cursor.getShort(name);
						t.findResult(key,value7);
						break;
					case IBaseType.BYTE:
						byte value8 = (byte)cursor.getInt(name);
						t.findResult(key,value8);
						break;
					case IBaseType.CHAR:
						char value9 = (char)cursor.getInt(name);
						t.findResult(key,value9);
						break;
				}
			}
		}
		return t;
	}
}
