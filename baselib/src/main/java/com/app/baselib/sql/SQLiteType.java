package com.app.baselib.sql;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 21/8/18
 * @desc ...
 */
@StringDef({SQLiteType.NULL,SQLiteType.INTEGER,SQLiteType.REAL,SQLiteType.TEXT,SQLiteType.BLOB})
@Retention(RetentionPolicy.SOURCE)
public @interface SQLiteType {
	//NULL、INTEGER、REAL（浮点数字）、TEXT(字符串文本)和BLOB(二进制对象)
	String NULL = "NULL";
	String INTEGER = "INTEGER";
	String REAL = "REAL";
	String TEXT = "TEXT";
	String BLOB = "BLOB";
}
