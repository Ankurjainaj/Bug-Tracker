package com.bugTracker.Bug.Tracker.controller;

import com.bugTracker.Bug.Tracker.dto.CreateProjectDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<?> projects(Model model, Principal principal) {
        ResponseModel response = projectService.getAllProjects(model, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addProject(@RequestBody CreateProjectDto project, Principal principal) {
        ResponseModel response = projectService.addNewProject(project, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{project_id}")
    public ResponseEntity<?> project(Model model, @PathVariable(value = "project_id") String projectId, Principal principal) {
        ResponseModel responseModel = projectService.getProject(model, projectId, principal);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PostMapping("/{project_id}/edit")
    public ResponseEntity<?> editProjectForm(Model model, @PathVariable(value = "project_id") String projectId, Principal principal) {
        ResponseModel responseModel = projectService.editProjectForm(model, projectId, principal);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PostMapping(value = "/edit")
    public ResponseEntity<?> editProject(@RequestBody CreateProjectDto project, Principal principal) {
        ResponseModel responseModel = projectService.editProject(project, principal);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteProject(@RequestParam(value = "project_id") String projectId) {
        ResponseModel responseModel = projectService.deleteProject(projectId);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}
