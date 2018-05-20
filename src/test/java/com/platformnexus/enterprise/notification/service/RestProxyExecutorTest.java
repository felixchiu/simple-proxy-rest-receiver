package com.platformnexus.enterprise.notification.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platformnexus.enterprise.notification.SimpleProxyRestReceiverTest;
import com.platformnexus.enterprise.notification.data.dto.message.NotificationMessage;
import com.platformnexus.enterprise.notification.data.repository.notification.IncomingEventRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;

public class RestProxyExecutorTest extends SimpleProxyRestReceiverTest {

    @Autowired
    private IncomingEventRepository repository;

    private final static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RestProxyExecutor executor;

    @Test
    public void execute() throws IOException {
        JsonNode message = mapper.readValue(getFile("test_data/notification_json_01.json"), JsonNode.class);
        NotificationMessage notificationMessage = NotificationMessage.builder()
                .receivedDatetime(new Date())
                .entityName("lims-case")
                .message(message)
                .token("XXX123")
                .build();
        executor.execute(notificationMessage);

    }
}