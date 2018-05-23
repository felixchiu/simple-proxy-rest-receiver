package com.platformnexus.enterprise.notification.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.platformnexus.enterprise.notification.connector.ConnectorHelper;
import com.platformnexus.enterprise.notification.data.dto.message.NotificationMessage;
import com.platformnexus.enterprise.notification.data.entity.notification.IncomingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RestProxyExecutor {

    @Value("${proxy.config.environment.isProd}")
    private Boolean isProduction;

    @Value("${proxy.config.application.name}")
    private String applicationName;

    @Value("${proxy.target.rest.method}")
    private String method;

    @Value("${proxy.config.environment.name}")
    private String environmentName;

    @Value("${proxy.source.queue.name}")
    private String queueName;

    @Value("${proxy.source.identity.name}")
    private String identityName;

    @Value("${proxy.target.rest.url}")
    private String url_template;

    @Value("${proxy.target.rest.username}")
    private String username;

    @Value("${proxy.target.rest.password}")
    private String password;

    @Autowired
    private IncomingEventService service;

    private final RestTemplate restTemplate = new RestTemplate();

    @Async("restExecutor")
    public void execute(NotificationMessage message) {

        String entityId = message.getMessage().get(identityName).asText();
        String url = String.format(url_template, entityId);

        IncomingEvent incomingEvent = IncomingEvent.builder()
                .token(message.getToken())
                .receivedDatetime(message.getReceivedDatetime())
                .isProduction(isProduction)
                .applicationName(applicationName)
                .method(method.toUpperCase())
                .environmentName(environmentName)
                .queueName(queueName)
                .isCompleted(false)
                .identityName(identityName)
                .identityValue(entityId)
                .url(url)
                .username(username)
                .build();
        incomingEvent = service.persistIncomingEvent(incomingEvent);
        log.debug("Before: Incoming Event: {}", incomingEvent);
        try {
            ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(url, HttpMethod.valueOf(incomingEvent.getMethod()), ConnectorHelper.getHttpEntity(username, password), JsonNode.class);
            log.info("Response Json: {}", responseEntity.getBody());
        } catch (Exception e) {
            if (incomingEvent.getIsProduction()) {
                log.error("[NOTIFY SUPPORT]: {}", incomingEvent);
                e.printStackTrace();
            }
        }
        incomingEvent = service.completeIncomingEvent(incomingEvent.getId());
        log.debug("Completed: Incoming Event: {}", incomingEvent);

    }

}
