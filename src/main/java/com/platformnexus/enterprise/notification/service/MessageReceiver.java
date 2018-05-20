package com.platformnexus.enterprise.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platformnexus.enterprise.notification.data.dto.message.NotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Felix Chiu on 2/22/18.
 */
@Slf4j
@Service
public class MessageReceiver {

    private final static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RestProxyExecutor restProxyExecutor;

    public void receiveMessage(String message) {
        log.debug("message: {}", message);
        try {
            NotificationMessage notificationMessage = mapper.readValue(message, NotificationMessage.class);
            restProxyExecutor.execute(notificationMessage);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
