package com.bugTracker.Bug.Tracker.repository;

import com.bugTracker.Bug.Tracker.entity.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Roles, String> {
}
