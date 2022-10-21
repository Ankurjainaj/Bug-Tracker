package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.ChangePassword;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.dto.RolesOfUser;
import com.bugTracker.Bug.Tracker.entity.User;
import com.bugTracker.Bug.Tracker.repository.NotificationRepository;
import com.bugTracker.Bug.Tracker.repository.UserRepository;
import com.bugTracker.Bug.Tracker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private BugServiceImpl bugService;
    @Autowired
    private TaskServiceImpl taskService;

    @Override
    public ResponseModel getProfile(Model model, Principal principal) {
        User loggedUser = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", Utils.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Profile | Bug Tracker");
        model.addAttribute("isUnread", notificationRepository.isThereUnread(loggedUser.getId()));

        model.addAttribute("completedBugs", bugService.completedBugsCount(loggedUser.getId()));
        model.addAttribute("completedTasks", taskService.completedTasksCount(loggedUser.getId()));

        model.addAttribute("changePassword", new ChangePassword());

        return ResponseModel.builder().message("profile").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel changePassword(ChangePassword cp, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(cp.getOldPassword(), user.getPassword()))
            return ResponseModel.builder().message("redirect:/profile?wrongpassword").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();

        if (!cp.getNewPassword().equals(cp.getNewPasswordAgain()))
            return ResponseModel.builder().message("redirect:/profile?notmatch").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();

        String encodedPw = encoder.encode(cp.getNewPassword());
        user.setPassword(encodedPw);
        userRepository.save(user);
        return ResponseModel.builder().message("redirect:/profile?success").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }
}
