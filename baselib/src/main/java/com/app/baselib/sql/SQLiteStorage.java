package com.app.baselib.sql;

import java.util.LinkedHashMap;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 23/8/18
 * @desc ...
 */
public class SQLiteStorage {
	protected ISqliteAdapter mAdapters;
	protected LinkedHashMap<String,Integer> tabNameMap;
	
	public SQLiteStorage(ISqliteAdapter adapters) {
		this.mAdapters = adapters;
		this.tabNameMap = new LinkedHashMap<>(8);
	}
	
	public void registerTabName(String tabName,@IBaseType int type) {
		tabNameMap.put(tabName,type);
	}
	
	ISqliteAdapter getAdapters() {
		return mAdapters;
	}
	
	String tableName(){
		return mAdapters.tableName();
	}
}
