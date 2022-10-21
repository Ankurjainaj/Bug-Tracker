package com.bugTracker.Bug.Tracker.controller;

import com.bugTracker.Bug.Tracker.dto.CreateTodoDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping(value = "/addtodo")
    public ResponseEntity<?> handleTodo(@RequestBody CreateTodoDto todo, Principal principal) {
        ResponseModel response = todoService.addTodo(todo, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
