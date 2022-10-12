package com.bugTracker.Bug.Tracker.entity;

import com.bugTracker.Bug.Tracker.dto.UserInProject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;

@Data
@AllArgsConstructor
@Document(collection = "projects")
public class Project {

    @Id
    @Field(value = "project_id")
    private String projectId;
    @Field(value = "title")
    private String title;
    @Field(value = "description")
    private String description;
    @Field(value = "readme")
    private String readme;
    @Field(value = "task_count")
    private Integer taskCount = 0;
    @Field(value = "bug_count")
    private Integer bugCount = 0;
    @Field(value = "contributors")
    private Integer contributors = 0;
    @Field(value = "status")
    private Integer status = 1;

    @Field(value = "users")
    private HashSet<UserInProject> users;

    public Project() {
        projectId = new ObjectId().toString();
    }
}
