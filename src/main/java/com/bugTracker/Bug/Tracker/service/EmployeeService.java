package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.CreateUserDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.entity.User;
import org.springframework.ui.Model;

import java.security.Principal;

public interface EmployeeService {

    ResponseModel index(Model model, Principal principal);

    ResponseModel getAdmins(Model model, Principal principal);

    ResponseModel getFilteredEmployees(Model model, Principal principal);

    ResponseModel addUser(CreateUserDto user);

    ResponseModel getEmployeeByEmail(Model model, Principal principal, String email);
}
