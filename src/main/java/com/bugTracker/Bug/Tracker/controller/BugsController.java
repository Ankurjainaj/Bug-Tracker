package com.bugTracker.Bug.Tracker.controller;

import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.entity.Bug;
import com.bugTracker.Bug.Tracker.service.BugServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.security.Principal;

@RestController
@RequestMapping("/bugs")
@Validated
public class BugsController {

    @Autowired
    private BugServiceImpl bugService;

    @GetMapping("")
    public String bugs(Model model, Principal principal) {
        return bugService.bugForUser(model, principal);
    }

    @GetMapping("/project/{projectId}")
    public String projectBug(Model model, Principal principal, @PathVariable(value = "project_id") @NotBlank String projectId) {
        return bugService.bugForProject(model, principal, projectId);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addBug(@RequestBody Bug bug, Principal principal) {
        ResponseModel response = bugService.addBug(bug, principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @RequestMapping("/bug/{bugid}")
//    public String bug(Model model, @PathVariable(value="bugid") String bugid, Principal principal) {
//        User user = ur.findByEmail(principal.getName());
//        try {
//            if(br.isItTheirBug(Long.parseLong(bugid), user.getId())) {
//                Bug bug = br.getBugById(Long.parseLong(bugid));
//
//                if(bug != null) {
//                    model.addAttribute("foundBug", bug);
//
//                    model.addAttribute("user", user);
//                    model.addAttribute("isAdmin", ur.isAdmin(user));
//                    model.addAttribute("pageTitle", "Bug #"+bugid+" | Bug Tracker");
//                    model.addAttribute("isUnread", nr.isThereUnread(user.getId()));
//                    return "bug";
//                }
//                return "error";
//            }
//        } catch (Exception e) {
//            return "error";
//        }
//        return "error";
//    }
//
//    @RequestMapping("/bug/{bugid}/edit")
//    public String editBugForm(Model model, @PathVariable(value="bugid") String bugid, Principal principal) {
//        User user = ur.findByEmail(principal.getName());
//        try {
//            if(br.isItTheirBug(Long.parseLong(bugid), user.getId())) {
//                Bug bug = br.getBugById(Long.parseLong(bugid));
//
//                if(bug != null) {
//                    model.addAttribute("bug", bug);
//
//                    model.addAttribute("user", user);
//                    model.addAttribute("isAdmin", ur.isAdmin(user));
//                    model.addAttribute("pageTitle", "Bug #"+bugid+" | Bug Tracker");
//                    model.addAttribute("projects", pr.getProjects(user.getId()));
//                    model.addAttribute("isUnread", nr.isThereUnread(user.getId()));
//                    return "editBug";
//                }
//                return "error";
//            }
//        } catch (Exception e) {
//            return "error";
//        }
//        return "error";
//    }
//
//    @RequestMapping(value = "/bugs/edit", method = RequestMethod.POST)
//    public String editBug(@ModelAttribute Bug bug, Principal principal) {
//        User user = ur.findByEmail(principal.getName());
//        br.updateBug(bug.getBugId(), pr.getProjectIdByName(bug.getProjectName()), bug.getTitle(), bug.getDescription(), bug.getStatus());
//
//        return "redirect:/bug/"+bug.getBugId();
//    }
//
//    @RequestMapping(value = "/bug/delete", method = RequestMethod.POST)
//    public String deleteBug(@ModelAttribute Bug bug) {
//        Bug b = br.getBugById(bug.getBugId());
//        br.deleteBug(b.getBugId());
//        return "redirect:/bugs";
//    }


}
