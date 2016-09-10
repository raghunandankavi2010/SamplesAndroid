package com.pucho.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA. User: rahulaw Date: 20/01/14 Time: 4:34 PM To change this template
 * use File | Settings | File Templates.
 */
public final class JsonUtil {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

	static {
		OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);
		OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJECT_MAPPER.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
		OBJECT_MAPPER.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
	}

	public static final Gson createGson() {
		return new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}
	
	public static final Gson createGsonNew() {
		return gson;
	}

	public static final Gson createGson(Type type, JsonDeserializer<Map> deserializer) {
		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(type, deserializer)
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	}

	public static final <T> T deserializeJson(String json, Class<T> type)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = ObjectMapperProvider.INSTANCE.get();
		//For UTs
		if(null == objectMapper){
			objectMapper = OBJECT_MAPPER;
		}
		return objectMapper.readValue(json, type);
	}
	
	public static final <T> T deserializeJson(String json, TypeReference<T> valueTypeRef)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = ObjectMapperProvider.INSTANCE.get();
		//For UTs
		if(null == objectMapper){
			objectMapper = OBJECT_MAPPER;
		}
		return objectMapper.readValue(json, valueTypeRef);
	}

	public static final String serializeJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = ObjectMapperProvider.INSTANCE.get();
		//For UTs
		if(null == objectMapper){
			objectMapper = OBJECT_MAPPER;
		}
		return objectMapper.writeValueAsString(object);
	}
	
	public static final String serializeJsonQuietlyUsingGson(Object object)
	{
		try
		{
			return gson.toJson(object);
		} catch (Exception e)
		{
			return "";
		}
	}
}
