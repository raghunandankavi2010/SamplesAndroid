package com.pucho.service;

import com.pucho.helper.GenericResponse;
import com.pucho.helper.IncomingSMSRequest;
import com.pucho.helper.NewSMSRequest;
import com.pucho.helper.OTPRequest;

/**
 * Created by dinesh.rathore on 03/05/15.
 */
public interface SMSService {

    public void sendSMS(String mobileNo,String sms);
    public GenericResponse newSMS(NewSMSRequest sms);
    public GenericResponse sendOTP(OTPRequest otpRequest);
    public GenericResponse validateOTP(String mobileNo,String code);
    public GenericResponse processSMS(IncomingSMSRequest request);
}
