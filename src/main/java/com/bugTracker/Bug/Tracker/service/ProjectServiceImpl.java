package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.CreateProjectDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.dto.RolesOfUser;
import com.bugTracker.Bug.Tracker.dto.UserInProject;
import com.bugTracker.Bug.Tracker.entity.Project;
import com.bugTracker.Bug.Tracker.entity.User;
import com.bugTracker.Bug.Tracker.repository.NotificationRepository;
import com.bugTracker.Bug.Tracker.repository.ProjectRepository;
import com.bugTracker.Bug.Tracker.repository.UserRepository;
import com.bugTracker.Bug.Tracker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public ResponseModel getAllProjects(Model model, Principal principal) {
        User loggedUser = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", Utils.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Projects | Bug Tracker");
        model.addAttribute("isUnread", notificationRepository.isThereUnread(loggedUser.getId()));

        model.addAttribute("projects", projectRepository.getAllProjects(loggedUser.getId()));
        model.addAttribute("project", new Project());

        return ResponseModel.builder().message("projects").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel addNewProject(CreateProjectDto project, Principal principal) {
        User sender = userRepository.findByEmail(principal.getName());
        Project p = new Project();
        UserInProject userInProject = new UserInProject(sender.getId(), 1);
        p.setStatus(1);
        p.setDescription(project.getDescription());
        p.setReadme(project.getReadme());
        p.setUsers(Set.of(userInProject));
        p.setTitle(project.getTitle());
        p.setContributors(1);
        projectRepository.save(p);
        return ResponseModel.builder().message("redirect:/projects").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel deleteProject(String projectId) {

        Project p = projectRepository.getProjectById(projectId);
        p.setStatus(5);
        projectRepository.save(p);
        return ResponseModel.builder().message("redirect:/projects").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel editProject(CreateProjectDto project, Principal principal) {
//        User user = userRepository.findByEmail(principal.getName());
        Project p = projectRepository.getProjectById(project.getProjectId());
//        UserInProject userInProject = new UserInProject(user.getId(), 1);
        p.setDescription(project.getDescription());
        p.setReadme(project.getReadme());
//        p.setUsers(Set.of(userInProject));
        p.setTitle(project.getTitle());
        p.setContributors(1);
//        pr.updateProject(project.getTitle(), project.getDescription(), project.getReadme(), project.getProjectId());
        projectRepository.save(p);
        return ResponseModel.builder().message("redirect:/project/" + project.getProjectId()).status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel getProject(Model model, String projectId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        try {
            if (projectRepository.isItTheirProject(projectId, user.getId()) != null) {
                Project project = projectRepository.getProjectById(projectId);

                if (project != null) {
                    model.addAttribute("foundProject", project);

                    model.addAttribute("user", user);
                    model.addAttribute("isAdmin", Utils.isAdmin(user));
                    model.addAttribute("pageTitle", "Project #" + projectId + " | Bug Tracker");
                    model.addAttribute("isUnread", notificationRepository.isThereUnread(user.getId()));
                    return ResponseModel.builder().message("project").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
                }
                return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
            }
        } catch (Exception e) {
            return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
        }
        return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel editProjectForm(Model model, String projectId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        try {
            if (projectRepository.isItTheirProject(projectId, user.getId()) != null) {
                Project project = projectRepository.getProjectById(projectId);

                if (project != null) {
                    model.addAttribute("project", project);

                    model.addAttribute("user", user);
                    model.addAttribute("isAdmin", Utils.isAdmin(user));
                    model.addAttribute("pageTitle", "Project #" + projectId + " | Bug Tracker");
                    model.addAttribute("projects", projectRepository.getAllProjects(user.getId()));
                    model.addAttribute("isUnread", notificationRepository.isThereUnread(user.getId()));
                    return ResponseModel.builder().message("editProject").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
                }
                return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
            }
        } catch (Exception e) {
            return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
        }
        return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }
}
