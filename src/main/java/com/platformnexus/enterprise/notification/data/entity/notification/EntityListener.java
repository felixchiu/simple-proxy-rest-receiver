package com.platformnexus.enterprise.notification.data.entity.notification;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.util.Date;

public class EntityListener {
    @PrePersist
    public void prePersist(IncomingEvent entity) {
        entity.setLogDatetime(new Timestamp(new Date().getTime()));
    }

    @PreUpdate
    public void preUpdate(IncomingEvent entity) {
        entity.setLogDatetime(new Timestamp(new Date().getTime()));
    }

}