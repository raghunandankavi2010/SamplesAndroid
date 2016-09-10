package com.pucho.configuration;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by dinesh.rathore on 27/09/15.
 */

@JsonSnakeCase
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TranslatorConfiguration {
    private String clientId;
    private String clientSecret;
}
