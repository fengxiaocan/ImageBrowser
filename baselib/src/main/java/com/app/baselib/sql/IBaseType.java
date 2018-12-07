package com.app.baselib.sql;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 22/8/18
 * @desc ...
 */
@IntDef({
		IBaseType.INT,
		IBaseType.BYTE,
		IBaseType.SHORT,
		IBaseType.LONG,
		IBaseType.FLOAT,
		IBaseType.DOUBLE,
		IBaseType.BOOLEAN,
		IBaseType.CHAR,
		IBaseType.STRING
})
@Retention(RetentionPolicy.SOURCE)
/**
 * 基本数据类型
 */
public @interface IBaseType {
	int INT = 0;
	int BYTE = 1;
	int SHORT = 2;
	int LONG = 3;
	int FLOAT = 4;
	int DOUBLE = 5;
	int BOOLEAN = 6;
	int CHAR = 7;
	int STRING = 8;
}
