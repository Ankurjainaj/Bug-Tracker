package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.CreateBugDto;
import com.bugTracker.Bug.Tracker.dto.DeleteBugDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.entity.Bug;
import org.springframework.ui.Model;

import java.security.Principal;

public interface BugService {
    String bugForUser(Model model, Principal principal);

    String bugForProject(Model model, Principal principal, String projectid);

    ResponseModel addBug(CreateBugDto bug, Principal principal);

    ResponseModel getBugById(String bugId, Model model, Principal principal);

    ResponseModel editBugForm(String bugId, Model model, Principal principal);

    ResponseModel editBug(CreateBugDto bug, Principal principal);

    ResponseModel deleteBug(DeleteBugDto bug);
}
