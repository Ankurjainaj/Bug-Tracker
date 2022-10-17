package com.bugTracker.Bug.Tracker.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBugDto {

    @JsonProperty("bug_id")
    private String bugId;
    @JsonProperty("user_id")
    private String userId;
}
