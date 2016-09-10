package com.pucho.helper;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSnakeCase
public class ELasticIndexUnit {
	private String index;
	private String type;
	private String id;
	private String bodyAsJson;
	private String parent;
}
