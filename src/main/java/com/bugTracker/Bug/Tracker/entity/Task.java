package com.bugTracker.Bug.Tracker.entity;

import com.bugTracker.Bug.Tracker.enums.WorkStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Document(collection = "tasks")
public class Task implements Serializable {

    @Field(value = "task_id")
    @Id
    private String taskId;
    @Field(value = "project_id")
    private String projectId;
    @Field(value = "user_id")
    private String userId;
    @Field(value = "title")
    private String title;
    @Field(value = "description")
    private String description;
    @Field(value = "status")
    private WorkStatus status;
    @Field(value = "created_date")
    private String created;
    @Field(value = "project_name")
    private String projectName;

    public Task() {
        taskId = new ObjectId().toString();
    }

}
