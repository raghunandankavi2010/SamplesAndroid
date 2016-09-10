package com.pucho.helper;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by dinesh.rathore on 23/01/15.
 */
public class GenericResponse {
    boolean success = true;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    SuccessResponse successResponse;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    ErrorResponse failure;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ErrorResponse getFailure() {
        return failure;
    }

    public void setFailure(ErrorResponse failure) {
        this.failure = failure;
        this.success = false;
    }

    public SuccessResponse getSuccessResponse() {
        return successResponse;
    }

    public void setSuccessResponse(SuccessResponse successResponse) {
        this.successResponse = successResponse;
    }

    @Override
    public String toString() {
        return "CreateSlotResponse{" +
            "success=" + success +
            ", failure=" + failure +
            '}';
    }
}
