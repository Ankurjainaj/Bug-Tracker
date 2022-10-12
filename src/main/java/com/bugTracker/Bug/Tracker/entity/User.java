package com.bugTracker.Bug.Tracker.entity;

import com.bugTracker.Bug.Tracker.dto.RolesOfUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;

@Data
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    @Field(value = "id")
    private String id;
    @Field(value = "first_name")
    private String fName;
    @Field(value = "last_name")
    private String lName;
    @Field(value = "email_id")
    private String email;
    @Field(value = "password")
    private String password;
    @Field(value = "created_date")
    private String created;
    @Field(value = "last_login")
    private String lastLogin;
    @Field(value = "image_path")
    private String image;
    @Field(value = "is_admin")
    private boolean isAdmin;
    @Field(value = "status")
    private Integer status = 1;     //Default status = 1 and in case of Soft delete status = 5

    @Field(value = "roles")
    private HashSet<RolesOfUser> roles;

    public User() {
        id = new ObjectId().toString();
    }

}
