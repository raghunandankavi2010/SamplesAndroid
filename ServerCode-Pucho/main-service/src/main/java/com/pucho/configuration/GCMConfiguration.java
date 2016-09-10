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
public class GCMConfiguration {

    private String serverKey;
    private String url;
}
