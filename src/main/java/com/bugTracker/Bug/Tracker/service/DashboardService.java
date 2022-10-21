package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import org.springframework.ui.Model;

import java.security.Principal;

public interface DashboardService {
    ResponseModel dashboard(Model model, Principal principal);
}
