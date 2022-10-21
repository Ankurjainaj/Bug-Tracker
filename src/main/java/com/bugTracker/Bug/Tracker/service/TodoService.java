package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.CreateTodoDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.entity.Todo;

import java.security.Principal;

public interface TodoService {
    ResponseModel addTodo(CreateTodoDto todo, Principal principal);
}
