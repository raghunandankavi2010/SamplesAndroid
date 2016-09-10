package com.pucho.configuration;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonSnakeCase
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MlConfiguration {
        private String host;
	private String taggingApi;
}
