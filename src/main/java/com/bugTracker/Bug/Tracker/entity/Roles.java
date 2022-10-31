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
@Document(collection = "roles")
public class Roles implements Serializable {

    @Field(value = "id")
    @Id
    private String id;
    @Field(value = "role")
    private String role;

    public Roles() {
        id = new ObjectId().toString();
    }

}
