package com.bugTracker.Bug.Tracker.repository;

import com.bugTracker.Bug.Tracker.entity.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
}
