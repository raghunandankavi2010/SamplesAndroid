package com.pucho.core.instrumentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.configuration.RabbitMQConfiguration;
import com.pucho.helper.IncomingSMSRequest;
import com.pucho.helper.NewSMSRequest;
import com.pucho.helper.OTPRequest;
import com.pucho.helper.SMSWorkerRequest;
import com.pucho.service.SMSService;
import com.pucho.utils.JsonUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class SMSWorker implements Runnable {

	private final static String QUEUE_NAME = QueueNames.SMSQUEUE;
	private final SMSService smsService;
	private final RabbitMQConfiguration rabbitMQConfiguration;
	private static final Logger logger = LoggerFactory.getLogger(SMSWorker.class);

	@Inject
	public SMSWorker(SMSService smsService, PuchoConfiguration configuration) {
		this.smsService = smsService;
		this.rabbitMQConfiguration = configuration.getRabbitMQConfiguration();
	}

	@Override
	public void run() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(this.rabbitMQConfiguration.getHost());
			// factory.setPassword(this.rabbitMQConfiguration.getPassword());
			// factory.setPort(this.rabbitMQConfiguration.getPort());
			// factory.setUsername(this.rabbitMQConfiguration.getUsername());
			// factory.setUri(this.rabbitMQConfiguration.getUri());
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			// System.out.println(" [*]Node Worker Waiting for messages.");

			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(QUEUE_NAME, true, consumer);

			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				try {
					System.out.println("Sms Worker Received:" + message);
					SMSWorkerRequest smsWorkerRequest = JsonUtil.deserializeJson(message, SMSWorkerRequest.class);
					switch (smsWorkerRequest.getSmsAction()) {
					case INCOMING_SMS:
						IncomingSMSRequest incomingSMSRequest = JsonUtil
								.deserializeJson(smsWorkerRequest.getSerializedEntity(), IncomingSMSRequest.class);
						this.smsService.processSMS(incomingSMSRequest);
						break;
					case OTP:
						OTPRequest otpRequest = JsonUtil.deserializeJson(smsWorkerRequest.getSerializedEntity(),
								OTPRequest.class);
						this.smsService.sendOTP(otpRequest);
						break;
					case SEND_SMS:
						NewSMSRequest smsRequest = JsonUtil.deserializeJson(smsWorkerRequest.getSerializedEntity(),
								NewSMSRequest.class);
						this.smsService.sendSMS(smsRequest.getPhoneNo(), smsRequest.getMessage());
						break;
					default:
						logger.error("Invalid SMS Action: " + message);
						break;
					}
				} catch (JsonProcessingException e) {
					// logger.error("Unable to parse to NewSMSRequest: " +
					// message);
				} catch (Exception e) {
					logger.error("Invalid SMS Action: " + message);
					e.printStackTrace();
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
