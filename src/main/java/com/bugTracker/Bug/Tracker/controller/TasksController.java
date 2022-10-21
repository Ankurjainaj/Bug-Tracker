package com.bugTracker.Bug.Tracker.controller;

import com.bugTracker.Bug.Tracker.dto.CreateTaskDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/task")
public class TasksController {

    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public ResponseEntity<?> tasks(Model model, Principal principal) {
        ResponseModel responseModel = taskService.getAllTasks(model, principal);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/project/{project_id}")
    public ResponseEntity<?> projectTasks(Model model, Principal principal, @PathVariable(value = "project_id") String projectId) {
        ResponseModel responseModel = taskService.getProjectById(model, principal, projectId);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addTask(@RequestBody CreateTaskDto task, Principal principal) {
        ResponseModel responseModel = taskService.addTask(task, principal);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/{task_id}")
    public ResponseEntity<?> task(Model model, @PathVariable(value = "task_id") String taskId, Principal principal) {
        ResponseModel responseModel = taskService.getTaskById(model, taskId, principal);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PostMapping("/{task_id}/edit")
    public ResponseEntity<?> editTaskForm(Model model, @PathVariable(value = "task_id") String taskId, Principal principal) {
        ResponseModel responseModel = taskService.editTaskById(model, taskId, principal);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PostMapping(value = "/edit")
    public ResponseEntity<?> editTask(@ModelAttribute CreateTaskDto task, Principal principal) {
        ResponseModel responseModel = taskService.editTask(task, principal);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteTask(@RequestParam(value = "task_id") String taskId) {
        ResponseModel responseModel = taskService.deleteTask(taskId);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}
