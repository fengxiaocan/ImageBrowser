package com.app.baselib.gson;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class FloatDefaultAdapter implements JsonSerializer<Float>, JsonDeserializer<Float> {
	@Override
	public Float deserialize(JsonElement json,Type typeOfT,JsonDeserializationContext context)
	throws JsonParseException
	{
		try {
			return json.getAsFloat();
		} catch (Exception e) {
			String asString = json.getAsString();
			if (TextUtils.isEmpty(asString) || "null".equals(asString))
			{
				//定义为int类型,如果后台返回""或者null,则返回0
				return 0F;
			}else {
				try {
					return Float.valueOf(asString);
				} catch (Exception e1) {
					return 0F;
				}
			}
		}
	}
	
	@Override
	public JsonElement serialize(Float src,Type typeOfSrc,JsonSerializationContext context) {
		return new JsonPrimitive(src);
	}
}
