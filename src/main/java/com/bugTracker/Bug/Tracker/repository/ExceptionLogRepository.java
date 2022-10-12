package com.bugTracker.Bug.Tracker.repository;

import com.bugTracker.Bug.Tracker.entity.ExceptionLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionLogRepository extends MongoRepository<ExceptionLog, String> {
}
