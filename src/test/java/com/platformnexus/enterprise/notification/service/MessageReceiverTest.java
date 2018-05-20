package com.platformnexus.enterprise.notification.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platformnexus.enterprise.notification.SimpleProxyRestReceiverTest;
import com.platformnexus.enterprise.notification.data.dto.message.NotificationMessage;
import com.platformnexus.enterprise.notification.data.repository.notification.IncomingEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.*;

@Slf4j
public class MessageReceiverTest extends SimpleProxyRestReceiverTest {

    @Autowired
    private MessageReceiver receiver;

    @Autowired
    private IncomingEventRepository repository;

    private final static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void receiveMessage() throws Exception{
        JsonNode message = mapper.readValue(getFile("test_data/notification_json_01.json"), JsonNode.class);
        NotificationMessage notificationMessage = NotificationMessage.builder()
                .receivedDatetime(new Date())
                .entityName("lims-case")
                .message(message)
                .token("XXX123")
                .build();

        receiver.receiveMessage(mapper.writeValueAsString(notificationMessage));
        Thread.sleep(5000);
        log.info("Data: {}", repository.findAll());

    }

}