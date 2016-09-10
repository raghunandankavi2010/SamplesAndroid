package com.pucho.configuration;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by alkumar2 on 27/06/15.
 */
@JsonSnakeCase
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MostoConfiguration {

    private String smsUrl;
    private String user;
    private String password;
    private String sender;

}
