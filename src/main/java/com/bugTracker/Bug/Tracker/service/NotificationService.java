package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import org.springframework.ui.Model;

import java.security.Principal;

public interface NotificationService {
    ResponseModel getAllNotifications(Model model, Principal principal);

    ResponseModel getNotification(Model model, Principal principal, String notificationId);

    ResponseModel removeNotification(String notificationId);
}
