package com.pucho.service.impl;

import com.google.inject.Inject;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.configuration.RabbitMQConfiguration;
import com.pucho.service.MQService;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class RabbitMQServiceImpl implements MQService {

    private RabbitMQConfiguration rabbitMQConfiguration;
    
    @Inject
    public RabbitMQServiceImpl(PuchoConfiguration configuration) {
        this.rabbitMQConfiguration = configuration.getRabbitMQConfiguration();
    }


    @Override
    public void sendMessage(String queue, String message) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(this.rabbitMQConfiguration.getHost());
            // factory.setPassword(this.rabbitMQConfiguration.getPassword());
            // factory.setPort(this.rabbitMQConfiguration.getPort());
            // factory.setUsername(this.rabbitMQConfiguration.getUsername());
            // factory.setUri(this.rabbitMQConfiguration.getUri());
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(queue, true, false, false, null);

            channel.basicPublish("", queue, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();
        } catch (Exception e) {

        }
    }

}
