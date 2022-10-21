package com.bugTracker.Bug.Tracker.dto;

import com.bugTracker.Bug.Tracker.enums.WorkStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {

    @JsonProperty(value = "task_id")
    private String taskId;
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "status")
    private WorkStatus status;
    @JsonProperty(value = "project_name")
    private String projectName;
}
