package com.bugTracker.Bug.Tracker.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel implements Serializable {

    @JsonProperty("status")
    @JsonAlias("status_code")
    private int status;

    @JsonProperty("current_server_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String currentServerTime;

    private String message;
}
