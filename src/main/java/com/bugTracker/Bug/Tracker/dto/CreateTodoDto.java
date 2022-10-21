package com.bugTracker.Bug.Tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTodoDto {

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "task")
    private String task;

    @JsonProperty(value = "status")
    private int status;
}
