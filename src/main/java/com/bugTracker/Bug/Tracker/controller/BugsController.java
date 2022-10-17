package com.bugTracker.Bug.Tracker.controller;

import com.bugTracker.Bug.Tracker.dto.CreateBugDto;
import com.bugTracker.Bug.Tracker.dto.DeleteBugDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.service.BugServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.security.Principal;

@RestController
@RequestMapping("/bugs")
@Validated
public class BugsController {

    @Autowired
    private BugServiceImpl bugService;

    @GetMapping("")
    public String bugs(Model model, Principal principal) {
        return bugService.bugForUser(model, principal);
    }

    @GetMapping("/project/{project_id}")
    public String projectBug(Model model, Principal principal, @PathVariable(value = "project_id") @NotBlank String projectId) {
        return bugService.bugForProject(model, principal, projectId);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addBug(@RequestBody CreateBugDto bug, Principal principal) {
        ResponseModel response = bugService.addBug(bug, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{bug_id}")
    public ResponseEntity<?> bug(Model model, @PathVariable(value = "bug_id") String bugId, Principal principal) {
        ResponseModel responseModel = bugService.getBugById(bugId, model, principal);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PutMapping("/{bug_id}/edit")
    public ResponseEntity<?> editBugForm(Model model, @PathVariable(value = "bug_id") String bugId, Principal principal) {
        ResponseModel responseModel = bugService.editBugForm(bugId, model, principal);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PostMapping(value = "/edit")
    public ResponseEntity<?> editBug(@RequestBody CreateBugDto bug, Principal principal) {
        ResponseModel response = bugService.editBug(bug, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteBug(@RequestBody DeleteBugDto bug) {
        ResponseModel response = bugService.deleteBug(bug);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
