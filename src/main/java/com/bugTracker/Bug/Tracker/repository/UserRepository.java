package com.bugTracker.Bug.Tracker.repository;

import com.bugTracker.Bug.Tracker.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{'email':?0, 'status':{$ne:5}}")
    User findByEmail(String email);

    @Query(value = "{'id':?0, 'status':{$ne:5}}")
    User findByUserId(String userId);

    @Query(value = "{'status':{$ne:5}}")
    List<User> getAllUser();
}
