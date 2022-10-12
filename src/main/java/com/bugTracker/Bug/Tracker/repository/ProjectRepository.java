package com.bugTracker.Bug.Tracker.repository;

import com.bugTracker.Bug.Tracker.entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    @Query(value = "{'status':{$ne:5},'users':{$elemMatch:{'user_id':?0, 'status':{$ne:5}}}}", sort = "{'project_id':-1}")
    List<Project> getAllProjects(String userId);

    @Query(value = "{'project_id':?0,'status':{$ne:5},'users':{$elemMatch:{'user_id':?1, 'status':{$ne:5}}}}")
    Project isItTheirProject(String projectId, String userId);

    @Query(value = "{'title':?0, 'status':{$ne:5}}")
    Project getProjectIdByName(String name);
}
