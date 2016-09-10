package com.pucho.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    public static enum Reason {
       NOT_FOUND,STATE_NOT_ALLOWED,EDIT_NOT_ALLOWED,ERROR_TICKET_CREATION,LOGIN_FAILED, INVALID_OTP, USER_DOES_NOT_EXIST, INVALID_SEARCH_TYPE, INVALID_ID_TOKEN

    }

    @JsonProperty("error_code")
    String errorCode;

    @JsonProperty("reason")
    Reason reason;

    @JsonProperty("info")
    Map<String, String> info;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

}
