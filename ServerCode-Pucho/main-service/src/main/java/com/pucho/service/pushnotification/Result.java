package com.pucho.service.pushnotification;


public class Result {
    private final String messageId;
    private final String canonicalRegistrationId;
    private final String errorCode;

    private Result(Builder builder) {
        canonicalRegistrationId = builder.canonicalRegistrationId;
        messageId = builder.messageId;
        errorCode = builder.errorCode;
    }

    /**
     * Gets the message id, if any.
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Gets the canonical registration id, if any.
     */
    public String getCanonicalRegistrationId() {
        return canonicalRegistrationId;
    }

    /**
     * Gets the error code, if any.
     */
    public String getErrorCodeName() {
        return errorCode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        if (messageId != null) {
            builder.append(" messageId=").append(messageId);
        }
        if (canonicalRegistrationId != null) {
            builder.append(" canonicalRegistrationId=")
                    .append(canonicalRegistrationId);
        }
        if (errorCode != null) {
            builder.append(" errorCode=").append(errorCode);
        }
        return builder.append(" ]").toString();
    }

    public static final class Builder {

        // optional parameters
        private String messageId;
        private String canonicalRegistrationId;
        private String errorCode;

        public Builder canonicalRegistrationId(String value) {
            canonicalRegistrationId = value;
            return this;
        }

        public Builder messageId(String value) {
            messageId = value;
            return this;
        }

        public Builder errorCode(String value) {
            errorCode = value;
            return this;
        }

        public Result build() {
            return new Result(this);
        }
    }
}
