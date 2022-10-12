package com.bugTracker.Bug.Tracker.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInProject {

    @Field(value = "user_id")
    private String userId;
    @Field(value = "status")
    private Integer status = 1;
}
