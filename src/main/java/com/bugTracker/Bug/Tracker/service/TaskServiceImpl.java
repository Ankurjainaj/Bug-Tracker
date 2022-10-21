package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.CreateTaskDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.entity.Project;
import com.bugTracker.Bug.Tracker.entity.Task;
import com.bugTracker.Bug.Tracker.entity.User;
import com.bugTracker.Bug.Tracker.enums.WorkStatus;
import com.bugTracker.Bug.Tracker.repository.NotificationRepository;
import com.bugTracker.Bug.Tracker.repository.ProjectRepository;
import com.bugTracker.Bug.Tracker.repository.TaskRepository;
import com.bugTracker.Bug.Tracker.repository.UserRepository;
import com.bugTracker.Bug.Tracker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    public long completedTasksCount(String userId) {
        return taskRepository.getCompletedTaskCount(userId);
    }

    @Override
    public ResponseModel getAllTasks(Model model, Principal principal) {
        User loggedUser = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", Utils.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Tasks | Bug Tracker");
        model.addAttribute("isUnread", notificationRepository.isThereUnread(loggedUser.getId()));

        model.addAttribute("allTask", taskRepository.getAllTask(loggedUser.getId()));
        model.addAttribute("projects", projectRepository.getAllProjects(loggedUser.getId()));

        model.addAttribute("task", new Task());

        return ResponseModel.builder().message("tasks").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel deleteTask(String taskId) {
        Task t = taskRepository.getTaskById(taskId);
        t.setStatus(WorkStatus.Cancelled);
        taskRepository.save(t);
        return ResponseModel.builder().message("redirect:/tasks").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel getProjectById(Model model, Principal principal, String projectId) {
        User loggedUser = userRepository.findByEmail(principal.getName());
        try {
            if (projectRepository.isItTheirProject(projectId, loggedUser.getId()) != null) {
                model.addAttribute("user", loggedUser);
                model.addAttribute("isAdmin", Utils.isAdmin(loggedUser));
                model.addAttribute("pageTitle", "Tasks | Bug Tracker");
                model.addAttribute("isUnread", notificationRepository.isThereUnread(loggedUser.getId()));

                model.addAttribute("allTask", taskRepository.getProjectTasks(projectId));
                model.addAttribute("projects", projectRepository.getAllProjects(loggedUser.getId()));

                model.addAttribute("task", new Task());

                return ResponseModel.builder().message("tasks").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
            } else {
                return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
            }
        } catch (Exception e) {
            return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
        }
    }

    @Override
    public ResponseModel addTask(CreateTaskDto task, Principal principal) {
        User sender = userRepository.findByEmail(principal.getName());
        Project project = projectRepository.getProjectIdByName(task.getProjectName());
        Task t = new Task();
        t.setStatus(task.getStatus());
        t.setTitle(task.getTitle());
        t.setDescription(task.getDescription());
        t.setCreated(Utils.getCurrentServerTime());
        t.setUserId(sender.getId());
        t.setProjectId(project.getProjectId());
        t.setProjectName(task.getProjectName());
        taskRepository.save(t);
        return ResponseModel.builder().message("redirect:/tasks").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel getTaskById(Model model, String taskId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        try {
            if (taskRepository.isItTheirTask(taskId, user.getId()) != null) {
                Task task = taskRepository.getTaskById(taskId);

                if (task != null) {
                    model.addAttribute("foundTask", task);

                    model.addAttribute("user", user);
                    model.addAttribute("isAdmin", Utils.isAdmin(user));
                    model.addAttribute("pageTitle", "Task #" + taskId + " | Bug Tracker");
                    model.addAttribute("isUnread", notificationRepository.isThereUnread(user.getId()));
                    return ResponseModel.builder().message("task").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
                }
                return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
            }
        } catch (Exception e) {
            return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
        }
        return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel editTaskById(Model model, String taskId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        try {
            if (taskRepository.isItTheirTask(taskId, user.getId()) != null) {
                Task task = taskRepository.getTaskById(taskId);

                if (task != null) {
                    model.addAttribute("task", task);

                    model.addAttribute("user", user);
                    model.addAttribute("isAdmin", Utils.isAdmin(user));
                    model.addAttribute("pageTitle", "Task #" + taskId + " | Bug Tracker");
                    model.addAttribute("projects", projectRepository.getAllProjects(user.getId()));
                    model.addAttribute("isUnread", notificationRepository.isThereUnread(user.getId()));
                    return ResponseModel.builder().message("editTask").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
                }
                return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
            }
        } catch (Exception e) {
            return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
        }
        return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel editTask(CreateTaskDto task, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        Task t = taskRepository.isItTheirTask(task.getTaskId(), user.getId());
        t.setStatus(task.getStatus());
        t.setDescription(task.getDescription());
        t.setTitle(task.getTitle());
        t.setProjectName(task.getProjectName());
        return ResponseModel.builder().message("redirect:/task/" + task.getTaskId()).status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }
}
