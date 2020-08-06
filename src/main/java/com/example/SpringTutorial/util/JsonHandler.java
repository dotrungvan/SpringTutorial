package com.example.SpringTutorial.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonHandler {
	private ObjectMapper objectMapper = new ObjectMapper();

	public Object fromJson(String json, Class<?> type) throws JsonMappingException, JsonProcessingException {
		return objectMapper.readValue(json, type);
	}

	public String toJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	public void setDateFormat(String dateFormat) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		objectMapper.setDateFormat(df);
	}
}
