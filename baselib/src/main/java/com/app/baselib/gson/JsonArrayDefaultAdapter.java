package com.app.baselib.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class JsonArrayDefaultAdapter implements JsonSerializer<JsonArray>, JsonDeserializer<JsonArray> {
	
	@Override
	public JsonArray deserialize(
			JsonElement json,Type typeOfT,JsonDeserializationContext context)
	throws JsonParseException
	{
		try {
			return json.getAsJsonArray();
		} catch (Exception e) {
			return new JsonArray();
		}
	}
	
	@Override
	public JsonElement serialize(
			JsonArray src,Type typeOfSrc,JsonSerializationContext context)
	{
		return src;
	}
}
