package com.bugTracker.Bug.Tracker.repository;

import com.bugTracker.Bug.Tracker.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
}
