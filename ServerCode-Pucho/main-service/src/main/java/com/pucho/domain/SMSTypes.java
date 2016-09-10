package com.pucho.domain;


public enum SMSTypes {
    INVALID(0, "invalid"),REGISTER(1, "register"), ASK(2, "ask");
    
    private Integer index;
    private String keyword;
    
    private SMSTypes(Integer index, String keyword) {
        this.index = index;
        this.keyword = keyword;
    }
    
    public static SMSTypes fromInt(Integer index) {
        SMSTypes smsType = SMSTypes.INVALID;
        switch(index) {
            case 1:
                smsType = SMSTypes.REGISTER;
                break;
            case 2:
                smsType = SMSTypes.ASK;
                break;
        }
        return smsType;
    }
    
    public static SMSTypes fromKeyword(String key) {
        SMSTypes smsType = SMSTypes.INVALID;
        switch(key) {
            case "register":
                smsType = SMSTypes.REGISTER;
                break;
            case "ask":
                smsType = SMSTypes.ASK;
                break;
        }
        return smsType;
    }
    
    public String getKeyword() {
        return this.keyword;
    }
    
    public Integer toInt() {
        return this.index;
    }
}