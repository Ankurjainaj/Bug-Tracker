package com.bugTracker.Bug.Tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorPageController {

    @GetMapping("")
    public String error() {
        return "error";
    }
}
