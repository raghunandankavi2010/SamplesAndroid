package com.pucho.core.instrumentation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.configuration.RabbitMQConfiguration;
import com.pucho.data.QuestionESData;
import com.pucho.domain.Question;
import com.pucho.helper.ELasticIndexUnit;
import com.pucho.service.MLService;
import com.pucho.service.MQService;
import com.pucho.utils.JsonUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class QuestionUpdateHandler implements Runnable {

    private final static String QUEUE_NAME = QueueNames.QUESTIONUPDATEQUEUE;
    private final String index = ElasticSearchConstants.index;
    private final String questionType = ElasticSearchConstants.questionType;
    private final RabbitMQConfiguration rabbitMQConfiguration;
    private final MQService mqService;
    private MLService mlService;
    private static final Logger logger = LoggerFactory.getLogger(QuestionUpdateHandler.class);

    @Inject
    public QuestionUpdateHandler(PuchoConfiguration configuration, MQService mqService, MLService mlService) {
        this.rabbitMQConfiguration = configuration.getRabbitMQConfiguration();
        this.mqService = mqService;
        this.mlService = mlService;
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
                    // System.out.println("Worker Received:" + message);
                    Question question = JsonUtil.deserializeJson(message, Question.class);
                    List<String> tags = this.mlService.getTags(question.getTitle());
                    QuestionESData quesEsData = question.toESData();
                    quesEsData.setTags(tags);
                    ELasticIndexUnit unit = new ELasticIndexUnit(index, questionType,
                            question.getId().toString(), quesEsData.toString(), null);
                    this.mqService.sendMessage(QueueNames.ELASTICSYNCQUEUE, JsonUtil.serializeJson(unit));
                } catch (JsonProcessingException e) {
//                    logger.error("Unable to parse to NewSMSRequest: " + message);
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
