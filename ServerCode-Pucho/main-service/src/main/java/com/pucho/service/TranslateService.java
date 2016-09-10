package com.pucho.service;

/**
 * Created by dinesh.rathore on 27/09/15.
 */
public interface TranslateService {
    public String translateString(String input,String destLan) ;
    public String[] translateStrings(String[] input, String destLan);
}
