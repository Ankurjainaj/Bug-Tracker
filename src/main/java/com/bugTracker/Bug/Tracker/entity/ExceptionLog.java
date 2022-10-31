package com.bugTracker.Bug.Tracker.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@Document(value = "exception_log")
public class ExceptionLog implements Serializable {

    @Id
    @Field(name = "log_id")
    private String logId;

    @Field(name = "request_log_id")
    private String requestLogId;

    @Field(name = "request_url")
    private String requestUrl;

    @Field(name = "request_json")
    private String requestJson;

    @Field(name = "response_json")
    private String responseJson;

    @Field(name = "creation_date")
    private String creationDate;

    @Field(name = "updation_date")
    private String updationDate;

    @Field(name = "message")
    private String message;

    public ExceptionLog() {
        logId = new ObjectId().toString();
    }
}
