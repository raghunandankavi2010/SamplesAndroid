package com.pucho.helper;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomingSMSRequest {

    private String phoneNo;
    private String message;
    private String time;
}
