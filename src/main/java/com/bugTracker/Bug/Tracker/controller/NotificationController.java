package com.bugTracker.Bug.Tracker.controller;

import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<?> notifications(Model model, Principal principal) {
        ResponseModel response = notificationService.getAllNotifications(model, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{notification_id}")
    public ResponseEntity<?> notification(Model model, Principal principal, @PathVariable(value = "notification_id") String notificationId) {
        ResponseModel response = notificationService.getNotification(model, principal, notificationId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/remove")
    public ResponseEntity<?> removeNotification(@RequestParam(value = "notification_id") String notificationId) {
        ResponseModel response = notificationService.removeNotification(notificationId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
