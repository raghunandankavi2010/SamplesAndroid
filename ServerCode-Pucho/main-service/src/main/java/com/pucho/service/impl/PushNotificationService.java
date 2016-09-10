package com.pucho.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.ning.http.client.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.pucho.configuration.GCMConfiguration;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.domain.*;
import com.pucho.helper.GCMMessageRequest;
import com.pucho.helper.GCMMessagebody;
import com.pucho.helper.NotificationRequest;
import com.pucho.service.NotificationService;
import com.pucho.utils.HttpClient;
import com.pucho.utils.JsonUtil;
import org.activejpa.entity.Condition;
import org.activejpa.entity.Filter;

/**
 * Created by dinesh.rathore on 27/09/15.
 */
public class PushNotificationService implements NotificationService {

	private final HttpClient httpClient = HttpClient.INSTANCE;
	private final GCMConfiguration gcmConfiguration;

	@Inject
	public PushNotificationService(PuchoConfiguration configuration) {
		this.gcmConfiguration = configuration.getGcmConfiguration();
	}

	
	public void notificationRequest(User user, NotificationRequest request) {
		GCMUser gcmUser = user.getGcmUser();
		if (gcmUser != null) {
			sendNotification(gcmUser, request);
		}
	}

	private void sendNotification(GCMUser gcmUser, NotificationRequest request) {
		String url = this.gcmConfiguration.getUrl();
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "Application/Json");
		headers.put("Authorization", "key=" + this.gcmConfiguration.getServerKey());
		GCMMessagebody gcmMessage = new GCMMessagebody();
		gcmMessage.setMessage(request);
		GCMMessageRequest msgRequest = new GCMMessageRequest();
		msgRequest.setTo(gcmUser.getRegistrationId());
		msgRequest.setData(gcmMessage);
		try {
			String body = JsonUtil.serializeJson(msgRequest);
			Response response = this.httpClient.executePost(url, body, headers);
			System.out.println("Notification: URL: "+url+" body: "+body+" headers: "+headers);
			if (response.getStatusCode() != javax.ws.rs.core.Response.Status.OK.getStatusCode()) {
				Integer status = response.getStatusCode();
				String responseBody = response.getResponseBody();
				System.out.println(response.getUri().toString());
				System.out.println(status);
				System.out.println(responseBody);
			}
			else {

			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void notificationRequest(Question question) {
		//implement later after activity tracking comes
	}


	@Override
	public void notificationRequest(Answer answer) {
		//implement later after activity tracking comes
		System.out.println("Notification: Answerid: "+answer.getId());
		NotificationRequest newAnswerRequest = new NotificationRequest();
		newAnswerRequest.setAnswerId(answer.getId());
		newAnswerRequest.setNotificationType(NotificationType.QUESTION_GOT_ANSWER.getInt());
		newAnswerRequest.setQuestionId(answer.getQuestionId());
		Filter filter = new Filter();
		filter.addCondition("followedQuestionId", Condition.Operator.eq, answer.getQuestionId());
		List<QuestionFollower> questionFollowerList = QuestionFollower.where(filter);
		for (QuestionFollower questionFollower: questionFollowerList) {
			newAnswerRequest.setUserId(questionFollower.getFollowerUserId());
			Filter userFilter =  new Filter();
			User user = User.findById(questionFollower.getFollowerUserId());
			notificationRequest(user, newAnswerRequest);
		}
		Filter questionFilter = new Filter();
		filter.addCondition("id", Condition.Operator.eq, answer.getQuestionId());
		List<Question> questionList = Question.where(questionFilter);
		if (questionList != null && questionList.size() !=0 ) {
			User asker = questionList.get(0).getUser();
			newAnswerRequest.setUserId(asker.getId());
			notificationRequest(asker, newAnswerRequest);
		}
	}

}
