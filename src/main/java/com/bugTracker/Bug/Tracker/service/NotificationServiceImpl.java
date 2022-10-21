package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.dto.RolesOfUser;
import com.bugTracker.Bug.Tracker.entity.Notification;
import com.bugTracker.Bug.Tracker.entity.User;
import com.bugTracker.Bug.Tracker.repository.NotificationRepository;
import com.bugTracker.Bug.Tracker.repository.UserRepository;
import com.bugTracker.Bug.Tracker.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Objects;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public ResponseModel getAllNotifications(Model model, Principal principal) {
        User loggedUser = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", Utils.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Notifications | Bug Tracker");
        model.addAttribute("isUnread", notificationRepository.isThereUnread(loggedUser.getId()));

        model.addAttribute("notifications", notificationRepository.getAllNotifications(loggedUser.getId()));

        return ResponseModel.builder().message("notifications").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel getNotification(Model model, Principal principal, String notificationId) {

        User user = userRepository.findByEmail(principal.getName());
        try {
            Notification n = notificationRepository.getNotificationById(notificationId);
            if (n != null) {
                if (Objects.equals(n.getUserId(), user.getId())) {
                    model.addAttribute("notification", n);

                    model.addAttribute("user", user);
                    model.addAttribute("isAdmin", Utils.isAdmin(user));
                    model.addAttribute("pageTitle", "Notification #" + notificationId + " | Bug Tracker");
                    model.addAttribute("isUnread", notificationRepository.isThereUnread(user.getId()));

                    if (!n.isOpened()) {
                        n.setOpened(true);
                        notificationRepository.save(n);
                    }
                    return ResponseModel.builder().message("notification").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
                }
                return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
            }
        } catch (Exception e) {
            return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
        }
        return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel removeNotification(String notificationId) {
        Notification n = notificationRepository.getNotificationById(notificationId);
        n.setStatus(5);
        notificationRepository.save(n);
        return ResponseModel.builder().message("redirect:/notifications").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }
}
