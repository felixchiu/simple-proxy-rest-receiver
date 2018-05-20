package com.platformnexus.enterprise.notification.data.entity.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="incoming_event", indexes = {
        @Index(name = "idx_incoming_event_id_value", columnList = "identity_value", unique = false),
        @Index(name = "idx_incoming_event_received_datetime", columnList = "received_datetime", unique = false),
        @Index(name = "idx_incoming_event_queue_name", columnList = "queue_name", unique = false),
        @Index(name = "idx_incoming_event_application_name", columnList = "application_name", unique = false)
})
@Entity(name="incomingEvent")
@EntityListeners(EntityListener.class)
public class IncomingEvent {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="token", nullable=false)
    private String token;

    @Column(name="received_datetime", nullable=false)
    private Date receivedDatetime;

    @Column(name="production", nullable=false)
    private Boolean isProduction;

    @Column(name="application_name", nullable=false)
    private String applicationName;

    @Column(name="method", nullable=false)
    private String method;

    @Column(name="environment_name", nullable=false)
    private String environmentName;

    @Column(name="queue_name", nullable=false)
    private String queueName;

    @Column(name="identity_name", nullable=false)
    private String identityName;

    @Column(name="identity_value", nullable=false)
    private String identityValue;

    @Column(name="completed", nullable=false)
    private Boolean isCompleted;

    @Column(name="url", nullable=false)
    private String url;

    @Column(name="username")
    private String username;

    //----- System Attributes ---
    @Column(name="log_datetime", nullable=false)
    private Date logDatetime;

    @Version
    @Column(name = "version", nullable=false)
    private Integer version;


}
