package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.CreateBugDto;
import com.bugTracker.Bug.Tracker.dto.DeleteBugDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.entity.Bug;
import com.bugTracker.Bug.Tracker.entity.Project;
import com.bugTracker.Bug.Tracker.entity.User;
import com.bugTracker.Bug.Tracker.enums.WorkStatus;
import com.bugTracker.Bug.Tracker.repository.BugRepository;
import com.bugTracker.Bug.Tracker.repository.NotificationRepository;
import com.bugTracker.Bug.Tracker.repository.ProjectRepository;
import com.bugTracker.Bug.Tracker.repository.UserRepository;
import com.bugTracker.Bug.Tracker.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        model.addAttribute("isAdmin", Utils.isAdmin(loggedUser));
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
                model.addAttribute("isAdmin", Utils.isAdmin(loggedUser));
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
    public ResponseModel addBug(CreateBugDto bug, Principal principal) {
        String currentServerTime = Utils.getCurrentServerTime();
        User sender = userRepository.findByEmail(principal.getName());
        Project project = projectRepository.getProjectIdByName(bug.getProjectName());
        Bug b = new Bug();
        b.setDescription(bug.getDescription());
        if (bug.getStatus() == 1)
            b.setStatus(WorkStatus.InProgress);
        if (bug.getStatus() == 2)
            b.setStatus(WorkStatus.Pending);
        if (bug.getStatus() == 3)
            b.setStatus(WorkStatus.Done);
        if (bug.getStatus() == 5)
            b.setStatus(WorkStatus.Cancelled);
        b.setTitle(bug.getTitle());
        b.setProjectId(project.getProjectId());
        b.setUserId(sender.getId());
        b.setProjectName(bug.getProjectName());
        b.setCreated(currentServerTime);
        b.setModifiedDate(currentServerTime);
        bugRepository.save(b);
        return ResponseModel.builder().currentServerTime(currentServerTime).status(HttpStatus.OK.value()).message("redirect:/bugs").build();
    }

    @Override
    public ResponseModel getBugById(String bugId, Model model, Principal principal) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCurrentServerTime(Utils.getCurrentServerTime());
        responseModel.setStatus(HttpStatus.BAD_REQUEST.value());
        responseModel.setMessage("error");
        User user = userRepository.findByEmail(principal.getName());
        try {
            if (bugRepository.isItTheirBug(bugId, user.getId()) != 0) {
                Bug bug = bugRepository.getBugById(bugId);

                if (bug != null) {
                    model.addAttribute("foundBug", bug);

                    model.addAttribute("user", user);
                    model.addAttribute("isAdmin", Utils.isAdmin(user));
                    model.addAttribute("pageTitle", "Bug #" + bugId + " | Bug Tracker");
                    model.addAttribute("isUnread", notificationRepository.isThereUnread(user.getId()));
                    responseModel.setMessage("bug");
                    responseModel.setStatus(HttpStatus.OK.value());
                    return responseModel;
                }
                return responseModel;
            }
        } catch (Exception e) {
            return responseModel;
        }
        return responseModel;
    }

    @Override
    public ResponseModel editBugForm(String bugId, Model model, Principal principal) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCurrentServerTime(Utils.getCurrentServerTime());
        responseModel.setStatus(HttpStatus.BAD_REQUEST.value());
        responseModel.setMessage("error");
        Pageable page = PageRequest.of(0, 5);
        User user = userRepository.findByEmail(principal.getName());
        try {
            if (bugRepository.isItTheirBug(bugId, user.getId()) != 0) {
                Bug bug = bugRepository.getBugById(bugId);

                if (bug != null) {
                    model.addAttribute("bug", bug);
                    model.addAttribute("user", user);
                    model.addAttribute("isAdmin", Utils.isAdmin(user));
                    model.addAttribute("pageTitle", "Bug #" + bugId + " | Bug Tracker");
                    model.addAttribute("projects", projectRepository.getProjects(user.getId(), page));
                    model.addAttribute("isUnread", notificationRepository.isThereUnread(user.getId()));
                    responseModel.setStatus(HttpStatus.OK.value());
                    responseModel.setMessage("editBug");
                    return responseModel;
                }
                return responseModel;
            }
        } catch (Exception e) {
            return responseModel;
        }
        return responseModel;
    }

    @Override
    public ResponseModel editBug(CreateBugDto bug, Principal principal) {
        String currentServerTime = Utils.getCurrentServerTime();
        if (bug.getBugId() == null || bug.getBugId().isBlank())
            return ResponseModel.builder().currentServerTime(currentServerTime).status(HttpStatus.BAD_REQUEST.value()).message("error").build();

        Project project = projectRepository.getProjectIdByName(bug.getProjectName());
        Bug b = bugRepository.getBugById(bug.getBugId());
        b.setDescription(bug.getDescription());
        if (bug.getStatus() == 1)
            b.setStatus(WorkStatus.InProgress);
        if (bug.getStatus() == 2)
            b.setStatus(WorkStatus.Pending);
        if (bug.getStatus() == 3)
            b.setStatus(WorkStatus.Done);
        if (bug.getStatus() == 5)
            b.setStatus(WorkStatus.Cancelled);
        b.setTitle(bug.getTitle());
        b.setProjectId(project.getProjectId());
        b.setModifiedDate(currentServerTime);
        bugRepository.save(b);
        return ResponseModel.builder().currentServerTime(currentServerTime).status(HttpStatus.OK.value()).message("redirect:/bug/" + bug.getBugId()).build();
    }

    @Override
    public ResponseModel deleteBug(DeleteBugDto bug) {

        Bug b = bugRepository.getBugById(bug.getBugId());
        b.setStatus(WorkStatus.Cancelled);
        bugRepository.save(b);
        return ResponseModel.builder().message("redirect:/bugs").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    public long completedBugsCount(String userId) {
        return bugRepository.getCompletedBugsCount(userId);
    }

}
