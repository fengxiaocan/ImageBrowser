package com.app.baselib.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class StringDefaultAdapter implements JsonSerializer<String>, JsonDeserializer<String> {
	@Override
	public String deserialize(JsonElement json,Type typeOfT,JsonDeserializationContext context)
	throws JsonParseException
	{
		try {
			return json.getAsString();
		} catch (Exception e) {
			return "";
		}
	}
	
	@Override
	public JsonElement serialize(String src,Type typeOfSrc,JsonSerializationContext context) {
		return new JsonPrimitive(src);
	}
}
