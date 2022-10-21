package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.ChangePassword;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import org.springframework.ui.Model;

import java.security.Principal;

public interface ProfileService {
    ResponseModel getProfile(Model model, Principal principal);

    ResponseModel changePassword(ChangePassword cp, Principal principal);
}
