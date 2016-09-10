package com.pucho.core.instrumentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.configuration.RabbitMQConfiguration;
import com.pucho.helper.ELasticIndexUnit;
import com.pucho.helper.NewSMSRequest;
import com.pucho.service.ElasticSearchService;
import com.pucho.service.MLService;
import com.pucho.utils.JsonUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ElasticSearchSyncWorker implements Runnable {

    private final static String QUEUE_NAME = QueueNames.ELASTICSYNCQUEUE;
    private final ElasticSearchService elasticSearchService;
    private final RabbitMQConfiguration rabbitMQConfiguration;
    private static final Logger logger = LoggerFactory.getLogger(SMSWorker.class);

    @Inject
    public ElasticSearchSyncWorker(ElasticSearchService elasticSearchService, PuchoConfiguration configuration) {
        this.elasticSearchService = elasticSearchService;
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
                    ELasticIndexUnit elasticSyncRequest = JsonUtil.deserializeJson(message, ELasticIndexUnit.class);
                    this.elasticSearchService.push(elasticSyncRequest);
                } catch (JsonProcessingException e) {
                	
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
