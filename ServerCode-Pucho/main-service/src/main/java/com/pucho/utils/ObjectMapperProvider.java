package com.pucho.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum ObjectMapperProvider {

	INSTANCE;
	private ObjectMapper objectMapper;

	public void set(ObjectMapper objectMapper) {
		if (objectMapper != null) {
			this.objectMapper = objectMapper;
		}
	}

	public ObjectMapper get() {
		return objectMapper;
	}

}
