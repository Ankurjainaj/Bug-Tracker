package com.bugTracker.Bug.Tracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification implements Serializable {

    @Id
    @Field(value = "notification_id")
    private String notificationId;
    @Field(value = "user_id")
    private String userId;
    @Field(value = "message")
    private String message;
    @Field(value = "date_time")
    private String dateTime;
    @Field(value = "is_opened")
    private boolean isOpened;
    @Field(value = "status")
    private Integer status;

    public Notification() {
        notificationId = new ObjectId().toString();
    }
}
