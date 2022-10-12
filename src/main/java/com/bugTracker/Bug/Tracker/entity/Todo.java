package com.bugTracker.Bug.Tracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@Document(collection = "todo")
public class Todo {

    @Id
    @Field(value = "todo_id")
    private String todoId;
    @Field(value = "user_id")
    private String userId;
    @Field(value = "task")
    private String task;
    @Field(value = "status")
    private String status;

    public Todo() {
        todoId = new ObjectId().toString();
    }

}
