package com.bugTracker.Bug.Tracker.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    @JsonProperty(value = "first_name")
    private String fName;
    @JsonProperty(value = "last_name")
    private String lName;
    @JsonProperty(value = "email_id")
    private String email;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "roles")
    private String roles;
}
