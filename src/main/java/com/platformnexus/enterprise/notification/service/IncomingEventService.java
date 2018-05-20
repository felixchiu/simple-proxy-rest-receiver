package com.platformnexus.enterprise.notification.service;

import com.platformnexus.enterprise.notification.data.entity.notification.IncomingEvent;
import com.platformnexus.enterprise.notification.data.service.IncomingEventDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomingEventService {

    @Autowired
    private IncomingEventDataService dataService;

    public IncomingEvent persistIncomingEvent(IncomingEvent incomingEvent) {
        return dataService.persistIncomingEvent(incomingEvent);
    }

    public IncomingEvent completeIncomingEvent(Long id) {
        return dataService.completeIncomingEvent(id);
    }

}
