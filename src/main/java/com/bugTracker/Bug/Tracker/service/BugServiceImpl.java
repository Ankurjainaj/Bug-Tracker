package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.dto.RolesOfUser;
import com.bugTracker.Bug.Tracker.entity.Bug;
import com.bugTracker.Bug.Tracker.entity.Project;
import com.bugTracker.Bug.Tracker.entity.User;
import com.bugTracker.Bug.Tracker.repository.BugRepository;
import com.bugTracker.Bug.Tracker.repository.NotificationRepository;
import com.bugTracker.Bug.Tracker.repository.ProjectRepository;
import com.bugTracker.Bug.Tracker.repository.UserRepository;
import com.bugTracker.Bug.Tracker.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;

@Service
@Slf4j
public class BugServiceImpl implements BugService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BugRepository bugRepository;

    @Override
    public String bugForUser(Model model, Principal principal) {
        User loggedUser = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Bugs | Bug Tracker");
        model.addAttribute("isUnread", notificationRepository.isThereUnread(loggedUser.getId()) != 0);
        model.addAttribute("allBug", bugRepository.getAllBugs(loggedUser.getId()));
        model.addAttribute("projects", projectRepository.getAllProjects(loggedUser.getId()));

        model.addAttribute("bug", new Bug());
        return "bugs";
    }

    @Override
    public String bugForProject(Model model, Principal principal, String projectId) {

        User loggedUser = userRepository.findByEmail(principal.getName());
        try {
            if (projectRepository.isItTheirProject(projectId, loggedUser.getId()) != null) {
                model.addAttribute("user", loggedUser);
                model.addAttribute("isAdmin", isAdmin(loggedUser));
                model.addAttribute("pageTitle", "Bugs | Bug Tracker");
                model.addAttribute("isUnread", notificationRepository.isThereUnread(loggedUser.getId()) != 0);

                model.addAttribute("allBug", bugRepository.getProjectBugs(projectId));

                model.addAttribute("projects", projectRepository.getAllProjects(loggedUser.getId()));
                model.addAttribute("bug", new Bug());

                return "bugs";
            } else {
                return "error";
            }
        } catch (Exception e) {
            log.error("Error occurred at bug for projects API call");
            return "error";
        }
    }

    @Override
    public ResponseModel addBug(Bug bug, Principal principal) {
        String currentServerTime = Utils.getCurrentServerTime();
        User sender = userRepository.findByEmail(principal.getName());
        Project project = projectRepository.getProjectIdByName(bug.getProjectName());
        Bug b = new Bug();
        b.setDescription(bug.getDescription());
        b.setStatus(bug.getStatus());
        b.setTitle(bug.getTitle());
        b.setProjectId(project.getProjectId());
        b.setUserId(sender.getId());
        b.setProjectName(bug.getProjectName());
        b.setCreated(currentServerTime);
        bugRepository.save(b);
        return ResponseModel.builder().currentServerTime(currentServerTime).status(HttpStatus.OK.value()).message("redirect:/bugs").build();
    }

    public boolean isAdmin(User loggedUser) {
        boolean admin = false;
        if (loggedUser.getRoles() != null && loggedUser.getRoles().isEmpty())
            return false;
        for (RolesOfUser role : loggedUser.getRoles()) {
            if (role.getStatus() != 5 && role.getRole().equalsIgnoreCase("admin")) {
                admin = true;
                break;
            }
        }
        return admin;
    }


}
