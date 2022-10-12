package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.entity.Bug;
import org.springframework.ui.Model;

import java.security.Principal;

public interface BugService {
    String bugForUser(Model model, Principal principal);

    String bugForProject(Model model, Principal principal, String projectid);

    ResponseModel addBug(Bug bug, Principal principal);
}
