package com.pucho.configuration;

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
public class RabbitMQConfiguration {

    private String host;
    private Integer port;
    private String uri;
    private String username;
    private String password;
}
