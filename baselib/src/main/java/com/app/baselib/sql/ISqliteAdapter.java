package com.app.baselib.sql;

import android.content.ContentValues;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 1/8/18
 * @desc ...
 */
public abstract class ISqliteAdapter {
	
	protected abstract String tableName();
	
	protected abstract void registerTabName(SQLiteStorage storage);
	
	protected abstract void setContentValue(ContentValues value);
	
	protected abstract void findId(int result);
	
	protected abstract void findResult(String tabName,byte result);
	
	protected abstract void findResult(String tabName,short result);
	
	protected abstract void findResult(String tabName,int result);
	
	protected abstract void findResult(String tabName,long result);
	
	protected abstract void findResult(String tabName,float result);
	
	protected abstract void findResult(String tabName,double result);
	
	protected abstract void findResult(String tabName,boolean result);
	
	protected abstract void findResult(String tabName,String result);
	
	protected abstract boolean isNeedUpgrade(int oldVersion,int newVersion);
	
	protected abstract boolean upgradeNeedDeleteSource();
}
