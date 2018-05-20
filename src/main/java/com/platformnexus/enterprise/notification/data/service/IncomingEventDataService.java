package com.platformnexus.enterprise.notification.data.service;

import com.platformnexus.enterprise.notification.data.entity.notification.IncomingEvent;
import com.platformnexus.enterprise.notification.data.repository.notification.IncomingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomingEventDataService {

    @Autowired
    private IncomingEventRepository repository;

    public IncomingEvent persistIncomingEvent(IncomingEvent incomingEvent) {
        return repository.saveAndFlush(incomingEvent);
    }

    public IncomingEvent completeIncomingEvent(Long id) {
        IncomingEvent incomingEvent = repository.findOne(id);
        incomingEvent.setIsCompleted(true);
        return repository.saveAndFlush(incomingEvent);
    }


}
