package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.CreateTaskDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.entity.Task;
import org.springframework.ui.Model;

import java.security.Principal;

public interface TaskService {
    ResponseModel getAllTasks(Model model, Principal principal);

    ResponseModel deleteTask(String taskId);

    ResponseModel getProjectById(Model model, Principal principal, String projectId);

    ResponseModel addTask(CreateTaskDto task, Principal principal);

    ResponseModel getTaskById(Model model, String taskId, Principal principal);

    ResponseModel editTaskById(Model model, String taskId, Principal principal);

    ResponseModel editTask(CreateTaskDto task, Principal principal);

}
