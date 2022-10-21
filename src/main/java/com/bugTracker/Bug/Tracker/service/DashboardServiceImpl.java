package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.dto.RolesOfUser;
import com.bugTracker.Bug.Tracker.entity.Todo;
import com.bugTracker.Bug.Tracker.entity.User;
import com.bugTracker.Bug.Tracker.repository.*;
import com.bugTracker.Bug.Tracker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Override
    public ResponseModel dashboard(Model model, Principal principal) {
        Pageable page = PageRequest.of(0, 5);

        User loggedUser = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", Utils.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Dashboard | Bug Tracker");
        model.addAttribute("isUnread", notificationRepository.isThereUnread(loggedUser.getId()));

        model.addAttribute("projectsCount", projectRepository.projectsCount(loggedUser.getId()));
        model.addAttribute("tasksCount", taskRepository.tasksCount(loggedUser.getId()));
        model.addAttribute("bugsCount", bugRepository.bugsCount(loggedUser.getId()));
        model.addAttribute("todos", todoRepository.getTodos(loggedUser.getId()));

        model.addAttribute("uncompletedBugs", bugRepository.uncompletedBugsCount(loggedUser.getId()));
        model.addAttribute("uncompletedTasks", taskRepository.uncompletedTaskCount(loggedUser.getId()));

        model.addAttribute("projects", projectRepository.getProjects(loggedUser.getId(), page));
        model.addAttribute("unreadNotification", notificationRepository.getUnreadNotifications(loggedUser.getId()));

        model.addAttribute("todo", new Todo());
        userService.updateLastLoginDate(loggedUser.getId());

        return ResponseModel.builder().message("index").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }
}
