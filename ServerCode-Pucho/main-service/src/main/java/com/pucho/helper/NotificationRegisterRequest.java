package com.pucho.helper;

import java.util.Date;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonSnakeCase
public class NotificationRegisterRequest {

    private Long userId;
    
    private String registrationId;
}
