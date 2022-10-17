package com.bugTracker.Bug.Tracker.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBugDto {

    @JsonProperty("bug_id")
    private String bugId;
    @JsonProperty("project_id")
    private String projectId;
    @JsonProperty(value = "user_id")
    private String userId;
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "status")
    private Integer status;
    @JsonProperty(value = "created_date")
    private String created;
    @JsonProperty(value = "project_name")
    private String projectName;
}
