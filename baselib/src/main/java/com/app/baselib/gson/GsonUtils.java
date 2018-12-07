package com.app.baselib.gson;

import com.app.baselib.log.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 文 件 名: GsonUtils
 * 创 建 人: qczhang
// * 创建日期: 2016/a11/17 0017 14:57
// * 描    述: ${desc} TODO
 */
public class GsonUtils {
	
	private static final Gson sGson = new GsonBuilder()
//			.registerTypeAdapter(Integer.class,new IntegerDefaultAdapter())
//			.registerTypeAdapter(String.class,new StringDefaultAdapter())
//			.registerTypeAdapter(Long.class,new LongDefaultAdapter())
//			.registerTypeAdapter(Double.class,new DoubleDefaultAdapter())
//			.registerTypeAdapter(int.class,new IntegerDefaultAdapter())
//			.registerTypeAdapter(long.class,new LongDefaultAdapter())
//			.registerTypeAdapter(double.class,new DoubleDefaultAdapter())
//			.registerTypeAdapter(new TypeToken<JsonObject>(){}.getType(),new ElementTypeAdapter())
//			.registerTypeAdapter(new TypeToken<JsonArray>(){}.getType(),new ElementTypeAdapter())
			.disableHtmlEscaping().create();
	
	/**
	 * 把json解析成T类型
	 *
	 * @param json 哟啊解析的json
	 * @return 返回结果
	 */
	public static <T> T get(String json,Class<T> clazz) {
		try {
			return (T)sGson.fromJson(json,clazz);
		} catch (Exception e) {
			LogUtils.e("noah","Gson clazz= " + clazz.getName());
		}
		return null;
	}
	
	public static <T> T get(String json,T t) {
		try {
			return (T)sGson.fromJson(json,t.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> String toJson(T t) {
		String json = sGson.toJson(t);
		return json;
	}
	
	public static Gson getGson() {
		return sGson;
	}
}

