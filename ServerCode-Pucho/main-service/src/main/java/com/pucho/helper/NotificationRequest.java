package com.pucho.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private Integer notificationType;
    
    private Long questionId;

    private Long answerId;

    private Long userId;

    private String userName;
    
    private Long subjectId;
    
    private String subjectName;
}
