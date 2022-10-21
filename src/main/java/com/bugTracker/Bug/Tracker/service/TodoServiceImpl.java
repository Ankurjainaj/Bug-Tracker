package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.dto.CreateTodoDto;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.entity.Todo;
import com.bugTracker.Bug.Tracker.entity.User;
import com.bugTracker.Bug.Tracker.enums.WorkStatus;
import com.bugTracker.Bug.Tracker.repository.TodoRepository;
import com.bugTracker.Bug.Tracker.repository.UserRepository;
import com.bugTracker.Bug.Tracker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TodoRepository todoRepository;

    @Override
    public ResponseModel addTodo(CreateTodoDto todo, Principal principal) {
        User sender = userRepository.findByEmail(principal.getName());

        Todo todo1 = new Todo();
        todo1.setTask(todo.getTask());
        todo1.setUserId(sender.getId());
        if (todo.getStatus() == 1)
            todo1.setStatus(WorkStatus.InProgress);
        if (todo.getStatus() == 2)
            todo1.setStatus(WorkStatus.Pending);
        if (todo.getStatus() == 3)
            todo1.setStatus(WorkStatus.Done);
        if (todo.getStatus() == 5)
            todo1.setStatus(WorkStatus.Cancelled);
        todoRepository.save(todo1);

        return ResponseModel.builder().message("redirect:/").status(HttpStatus.OK.value()).currentServerTime(Utils.getCurrentServerTime()).build();
    }
}
