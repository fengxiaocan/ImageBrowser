package com.app.baselib.sql;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 21/8/18
 * @desc ...
 */
public interface IExecSQLBridge<T extends SqliteSuper> {
	String[] execSqlWhereArgs(T t);
	
	String execSqlWhereClause(T t);
}
