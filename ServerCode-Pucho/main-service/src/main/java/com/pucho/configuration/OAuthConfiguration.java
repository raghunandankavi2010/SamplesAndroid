package com.pucho.configuration;

import java.util.List;
import java.util.Map;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonSnakeCase
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OAuthConfiguration {
	private List<String> clientIdList;
	private List<String> appsDomainList;
}
