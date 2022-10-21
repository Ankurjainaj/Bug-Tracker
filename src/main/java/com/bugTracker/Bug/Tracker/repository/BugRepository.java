package com.bugTracker.Bug.Tracker.repository;

import com.bugTracker.Bug.Tracker.entity.Bug;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugRepository extends MongoRepository<Bug, String> {

    @Query(value = "{'user_id':?0, 'status':{$ne:5}}", sort = "{'bug_id':-1}")
    List<Bug> getAllBugs(String id);

    @Query(value = "{'project_id':?0, 'status':{$ne:5}}", count = true)
    int getBugCount(String projectId);

    @Query(value = "{'project_id':?0, 'status':{$ne:5}}", sort = "{'bug_id':-1}")
    List<Bug> getProjectBugs(String projectId);

    @Query(value = "{'user_id':?0, 'status':{$ne:5}}", count = true)
    long bugsCount(String userId);

    @Query(value = "{$and:[{'user_id':?0}, {'status':{$in:[1,2]}}]}", count = true)
    long uncompletedBugsCount(String userId);

    @Query(value = "{'bug_id':?0, 'status':{$ne:5}}")
    Bug getBugById(String bugId);

    @Query(value = "{'bug_id':?0,'user_id':?1, 'status':{$ne:5}}", count = true)
    long isItTheirBug(String bugId, String userId);

    @Query(value = "{'user_id':?0, 'status':3}", count = true)
    long getCompletedBugsCount(String userId);
}
