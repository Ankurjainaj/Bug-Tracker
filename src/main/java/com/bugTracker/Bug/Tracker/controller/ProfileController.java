package com.bugTracker.Bug.Tracker.controller;


import com.bugTracker.Bug.Tracker.dto.ChangePassword;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/profile")
@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("")
    public ResponseEntity<?> profile(Model model, Principal principal) {
        ResponseModel response = profileService.getProfile(model, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/password/change")
    public ResponseEntity<?> changePw(@RequestBody ChangePassword cp, Principal principal) {
        ResponseModel response = profileService.changePassword(cp, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
