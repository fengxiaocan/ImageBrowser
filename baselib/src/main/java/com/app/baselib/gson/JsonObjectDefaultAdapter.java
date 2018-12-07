package com.app.baselib.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class JsonObjectDefaultAdapter implements JsonSerializer<JsonObject>, JsonDeserializer<JsonObject> {
	
	@Override
	public JsonObject deserialize(
			JsonElement json,Type typeOfT,JsonDeserializationContext context)
	throws JsonParseException
	{
		try {
			return json.getAsJsonObject();
		} catch (Exception e) {
			return new JsonObject();
		}
	}
	
	@Override
	public JsonElement serialize(
			JsonObject src,Type typeOfSrc,JsonSerializationContext context)
	{
		return src;
	}
}
