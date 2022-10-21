package com.bugTracker.Bug.Tracker.controller;

import com.bugTracker.Bug.Tracker.dto.CreateUserDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.entity.User;
import com.bugTracker.Bug.Tracker.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public ResponseEntity<?> employees(Model model, Principal principal) {
        ResponseModel response = employeeService.index(model, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> admins(Model model, Principal principal) {
        ResponseModel response = employeeService.getAdmins(model, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/employee")
    public ResponseEntity<?> filteredEmployees(Model model, Principal principal) {
        ResponseModel response = employeeService.getFilteredEmployees(model, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/employee/add")
    public ResponseEntity<?> addUser(@RequestBody CreateUserDto user) {
        ResponseModel response = employeeService.addUser(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> employee(Model model, Principal principal, @PathVariable(value = "email") String email) {
        ResponseModel response = employeeService.getEmployeeByEmail(model, principal, email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
