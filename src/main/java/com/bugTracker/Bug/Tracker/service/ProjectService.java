package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.CreateProjectDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import org.springframework.ui.Model;

import java.security.Principal;

public interface ProjectService {
    ResponseModel getAllProjects(Model model, Principal principal);

    ResponseModel addNewProject(CreateProjectDto project, Principal principal);

    ResponseModel deleteProject(String projectId);

    ResponseModel editProject(CreateProjectDto project, Principal principal);

    ResponseModel getProject(Model model, String projectId, Principal principal);

    ResponseModel editProjectForm(Model model, String projectId, Principal principal);
}
