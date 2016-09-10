package com.pucho.service;

public interface MQService {

    public void sendMessage(String queue, String message);
}
