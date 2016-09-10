package com.pucho.configuration;

import io.dropwizard.Configuration;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@JsonSnakeCase
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PuchoConfiguration extends Configuration
{
    private Map<String,String> db;
    private String fileStoragePath;
    private MostoConfiguration mostoConfiguration;
    private TranslatorConfiguration translatorConfiguration;
    private ElasticSearchConfiguration elasticSearchConfiguration;
    private MlConfiguration mlConfiguration;
    private OAuthConfiguration oAuthconfiguration;
    private GCMConfiguration gcmConfiguration;
    private RabbitMQConfiguration rabbitMQConfiguration;
}
