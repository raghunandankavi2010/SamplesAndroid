package com.pucho.helper;

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
public class GCMMessageRequest {

    private String to;

    private GCMMessagebody data;
}
