package com.pucho.helper;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.*;

import java.util.Date;

/**
 * Created by dinesh.rathore on 25/07/15.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonSnakeCase
public class NewSMSRequest {
    private String phoneNo;
    private String message;
    private Date time;

}
