package com.pucho.service.impl;

import java.util.List;

import org.activejpa.entity.Condition;
import org.activejpa.entity.Filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.pucho.core.instrumentation.QueueNames;
import com.pucho.domain.Answer;
import com.pucho.domain.Question;
import com.pucho.domain.SMSAction;
import com.pucho.domain.User;
import com.pucho.helper.NewSMSRequest;
import com.pucho.helper.SMSWorkerRequest;
import com.pucho.service.MQService;
import com.pucho.service.NotificationService;
import com.pucho.utils.JsonUtil;

/**
 * Created by dinesh.rathore on 27/09/15.
 */
public class SMSNotificationService implements NotificationService {

	private MQService mqService;
	
	@Inject
	public SMSNotificationService(MQService mqService) {
		this.mqService = mqService;
	}

	@Override
	public void notificationRequest(Question question) {
		
	}

	@Override
	public void notificationRequest(Answer answer) {
		//fetch question from user, then fetch user, then if user has phone number, send message
		Long id = answer.getQuestionId();
		Filter filter = new Filter();
		filter.addCondition("id", Condition.Operator.eq, id);
		List<Question> quesList = Question.where(filter);
		if (quesList != null && quesList.size() != 0) {
			Question ques = quesList.get(0);
			User user = ques.getUser();
			if (user.getPhone() != null && !user.getPhone().trim().isEmpty()) {
				StringBuilder builder = new StringBuilder();
				builder.append("Hi ");
				builder.append(user.getFullName()+ "! We have found an answer for your question : ");
				builder.append(ques.getTitle());
				builder.append(" The answer is: ");
				builder.append(answer.getContent());
				NewSMSRequest smsRequest = new NewSMSRequest();
				smsRequest.setPhoneNo(user.getPhone());
				smsRequest.setMessage(builder.toString());
				try {
					SMSWorkerRequest workerRequest = new SMSWorkerRequest();
					workerRequest.setSmsAction(SMSAction.SEND_SMS);
					workerRequest.setSerializedEntity(JsonUtil.serializeJson(smsRequest));
					this.mqService.sendMessage(QueueNames.SMSQUEUE, JsonUtil.serializeJson(workerRequest));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
	}

    
}
