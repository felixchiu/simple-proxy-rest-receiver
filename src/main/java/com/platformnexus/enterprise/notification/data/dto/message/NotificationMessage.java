package com.platformnexus.enterprise.notification.data.dto.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationMessage {

    private String token;

    private JsonNode message;

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date receivedDatetime;

    private String entityName;
}
