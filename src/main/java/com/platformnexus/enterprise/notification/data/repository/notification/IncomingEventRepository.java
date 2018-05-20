package com.platformnexus.enterprise.notification.data.repository.notification;

import com.platformnexus.enterprise.notification.data.entity.notification.IncomingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomingEventRepository extends JpaRepository<IncomingEvent, Long> {
}
