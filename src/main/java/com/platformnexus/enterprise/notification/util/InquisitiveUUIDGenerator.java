package com.platformnexus.enterprise.notification.util;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.UUIDGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 * Created by Felix Chiu on 5/5/18.
 */
@Slf4j
public class InquisitiveUUIDGenerator extends UUIDGenerator {


    private String entityName;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) {
        entityName = params.getProperty(ENTITY_NAME);
        log.info("UUID Configure for {}", entityName);
        log.info("Properties: {}", params);
        super.configure(type, params, serviceRegistry);
    }

    @Override
    public Serializable generate(SessionImplementor session, Object object) {
        Serializable id = session
                .getEntityPersister(entityName, object)
                .getIdentifier(object, session);


        if (id == null) {
            /** If ID value doesn't available, using supper class UUID generator  **/
            return super.generate(session, object);
        } else {
            /** Else, using the ID value already come with **/
            return id;
        }
    }
}
