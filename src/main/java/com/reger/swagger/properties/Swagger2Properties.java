package com.reger.swagger.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(Swagger2Properties.prefix)
public class Swagger2Properties {
	public static final String prefix = "spring";

	private Map<String, Swagger2GroupProperties> swaggerGroup;

	public Map<String, Swagger2GroupProperties> getSwaggerGroup() {
		return swaggerGroup;
	}

	public void setSwaggerGroup(Map<String, Swagger2GroupProperties> swaggerGroup) {
		this.swaggerGroup = swaggerGroup;
	}
}
