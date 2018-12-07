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

public class BooleanDefaultAdapter implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {
	@Override
	public Boolean deserialize(JsonElement json,Type typeOfT,JsonDeserializationContext context)
	throws JsonParseException
	{
		try {
			return json.getAsBoolean();
		} catch (Exception e) {
			String asString = json.getAsString();
			if (TextUtils.isEmpty(asString) || "null".equals(asString))
			{
				//定义为int类型,如果后台返回""或者null,则返回0
				return false;
			}else {
				try {
					return Boolean.valueOf(asString);
				} catch (Exception e1) {
					return false;
				}
			}
		}
	}
	
	@Override
	public JsonElement serialize(Boolean src,Type typeOfSrc,JsonSerializationContext context) {
		return new JsonPrimitive(src);
	}
}
