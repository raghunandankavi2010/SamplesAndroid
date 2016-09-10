package com.pucho.core.instrumentation;

import com.pucho.annotations.PushNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.pucho.annotations.SMSNotification;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.configuration.RabbitMQConfiguration;
import com.pucho.domain.Answer;
import com.pucho.helper.ELasticIndexUnit;
import com.pucho.service.MQService;
import com.pucho.service.NotificationService;
import com.pucho.utils.JsonUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;


public class AnswerUpdateHandler implements Runnable {

    private final String index = ElasticSearchConstants.index;
    private final String answerType = ElasticSearchConstants.answerType;
    private final static String QUEUE_NAME = QueueNames.ANSWERUPDATEQUEUE;
    private final RabbitMQConfiguration rabbitMQConfiguration;
    private final MQService mqService;
    private final NotificationService smsNotificationService;
    private final NotificationService pushNotificationService;
    private static final Logger logger = LoggerFactory.getLogger(AnswerUpdateHandler.class);

    @Inject
    public AnswerUpdateHandler(PuchoConfiguration configuration, MQService mqService, @SMSNotification NotificationService smsNotificationService, @PushNotification NotificationService pushNotificationService) {
        this.rabbitMQConfiguration = configuration.getRabbitMQConfiguration();
        this.mqService = mqService;
        this.smsNotificationService = smsNotificationService;
        this.pushNotificationService = pushNotificationService;
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
                    Answer answer = JsonUtil.deserializeJson(message, Answer.class);
                    ELasticIndexUnit unit = new ELasticIndexUnit(index, answerType, answer.getId().toString(), answer.toESData().toString(),
                            answer.getQuestionId().toString());
                    this.mqService.sendMessage(QueueNames.ELASTICSYNCQUEUE, JsonUtil.serializeJson(unit));
                    this.smsNotificationService.notificationRequest(answer);
                    this.pushNotificationService.notificationRequest(answer);
                } catch (JsonProcessingException e) {
                    //logger.error("Unable to parse to NewSMSRequest: " + message);
                }
                catch (Exception e) {
                	e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
