package com.pucho.domain;


public enum NotificationType {
    INVALID(0), UPVOTE(1), TOPIC_GOT_QUESTION(2), QUESTION_GOT_ANSWER(3), NEW_FOLLOWER(4);
    
    private final Integer value;

    
    public Integer getInt() {
        return this.value;
    }

    private NotificationType(Integer value) {
        this.value = value;
    }
    
    public static NotificationType fromInt(Integer value) {
        NotificationType notificationType = NotificationType.INVALID;
        switch(value) {
            case 1:
                notificationType = NotificationType.UPVOTE;
                break;
            case 2:
                notificationType = NotificationType.TOPIC_GOT_QUESTION;
                break;
            case 3:
                notificationType = NotificationType.QUESTION_GOT_ANSWER;
                break;
            case 4:
                notificationType = NotificationType.NEW_FOLLOWER;
                break;
        }
        return notificationType;
    }
}
