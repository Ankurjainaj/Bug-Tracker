package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.CreateUserDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.dto.RolesOfUser;
import com.bugTracker.Bug.Tracker.entity.Roles;
import com.bugTracker.Bug.Tracker.entity.User;
import com.bugTracker.Bug.Tracker.repository.*;
import com.bugTracker.Bug.Tracker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private BugRepository bugRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseModel index(Model model, Principal principal) {
        User loggedUser = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", Utils.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Employees | Bug Tracker");
        model.addAttribute("isUnread", notificationRepository.isThereUnread(loggedUser.getId()));

        model.addAttribute("employee", new User());
        model.addAttribute("allUsers", userRepository.getAllUser());

        return ResponseModel.builder().message("employees").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel getAdmins(Model model, Principal principal) {
        User loggedUser = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", Utils.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Employees | Bug Tracker");
        model.addAttribute("isUnread", notificationRepository.isThereUnread(loggedUser.getId()));

        model.addAttribute("employee", new User());
        model.addAttribute("allUsers", userRepository.getAllUser());

        return ResponseModel.builder().message("admins").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel getFilteredEmployees(Model model, Principal principal) {

        User loggedUser = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", Utils.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Employees | Bug Tracker");
        model.addAttribute("isUnread", notificationRepository.isThereUnread(loggedUser.getId()));

        model.addAttribute("employee", new User());
        model.addAttribute("allUsers", userRepository.getAllUser());

        return ResponseModel.builder().message("filteredEmployees").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel addUser(CreateUserDto user) {

        User found = userRepository.findByEmail(user.getEmail());
        if (found != null)
            return ResponseModel.builder().message("redirect:/employees?emailTaken").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();

        User u = new User();
        u.setFName(user.getFName());
        u.setLName(user.getLName());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        u.setCreated(Utils.getCurrentServerTime());
        Set<String> role = Set.of(user.getRoles().trim().split(","));
        HashSet<RolesOfUser> r = new HashSet<>();
        List<Boolean> collect = role.stream().map(ro -> r.add(new RolesOfUser(ro, 1))).collect(Collectors.toList());
        u.setRoles(r);
        userRepository.save(u);
        List<Roles> saveRole = new ArrayList<>();
        role.forEach(roles -> {
            Roles rol = new Roles();
            rol.setRole(roles);
            saveRole.add(rol);
        });
        roleRepository.saveAll(saveRole);

        return ResponseModel.builder().message("redirect:/employees").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }

    @Override
    public ResponseModel getEmployeeByEmail(Model model, Principal principal, String email) {

        User foundUser = userRepository.findByEmail(email);
        if (foundUser == null) {
            return ResponseModel.builder().message("error").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build();
        }

        User loggedUser = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", Utils.isAdmin(loggedUser));
        model.addAttribute("pageTitle", email + " | Bug Tracker");
        model.addAttribute("isUnread", notificationRepository.isThereUnread(loggedUser.getId()));

        model.addAttribute("foundEmployee", foundUser);
        model.addAttribute("totalBugs", bugRepository.getBugCount(foundUser.getId()));
        model.addAttribute("totalTasks", taskRepository.getTaskCount(foundUser.getId()));

        return ResponseModel.builder().message("employee").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }
}
