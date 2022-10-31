package com.bugTracker.Bug.Tracker.controller;


import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.service.DashboardService;
import com.bugTracker.Bug.Tracker.service.DashboardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping("/")
    public ResponseEntity<?> dashboard(Model model, Principal principal) {
        ResponseModel response = service.dashboard(model, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
