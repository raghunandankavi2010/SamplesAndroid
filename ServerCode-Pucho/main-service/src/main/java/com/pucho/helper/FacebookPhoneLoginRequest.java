package com.pucho.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.ws.rs.GET;

/**
 * Created by harshmathur on 8/14/16.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacebookPhoneLoginRequest {

    private String phoneNo;
    private String facebookAccountId;
    private String fullName;
}
