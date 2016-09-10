package com.pucho.service;

import com.pucho.domain.Answer;
import com.pucho.domain.Question;

/**
 * Created by dinesh.rathore on 27/09/15.
 */
public interface NotificationService {

    public void notificationRequest(Question question);
    
    public void notificationRequest(Answer answer);
}
