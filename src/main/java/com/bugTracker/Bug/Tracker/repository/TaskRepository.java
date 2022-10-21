package com.bugTracker.Bug.Tracker.repository;

import com.bugTracker.Bug.Tracker.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    @Query(value = "{'user_id':?0, 'status':{$ne:5}}", count = true)
    long tasksCount(String userId);

    @Query(value = "{'user_id':?0, 'status':{$in:{1,2}}}", count = true)
    long uncompletedTaskCount(String userId);

    @Query(value = "{'project_id':?0, 'status':{$ne:5}}", count = true)
    long getTaskCount(String projectId);

    @Query(value = "{'user_id':?0, 'status':3}", count = true)
    long getCompletedTaskCount(String userId);

    @Query(value = "{'user_id':?0, 'status':{$ne:5}}", sort = "{task_id:-1}")
    List<Task> getAllTask(String userId);

    @Query(value = "{'task_id':?0, 'status':{$ne:5}}")
    Task getTaskById(String taskId);

    @Query(value = "{'project_id':?0, 'status':{$ne:5}}", sort = "{task_id:-1}")
    List<Task> getProjectTasks(String projectId);

    @Query(value = "{$and:[{'task_id':?0}, {'status':{$ne:5}}}, {'user_id':?1}]}")
    Task isItTheirTask(String taskId, String userId);
}
