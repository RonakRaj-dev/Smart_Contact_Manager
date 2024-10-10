package com.my_first_project.SCM.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my_first_project.SCM.helper.Helper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
    //user dasboard page

    @PostMapping("/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }
    

    //user profile page

    @GetMapping("/profile")
    public String userProfile(Authentication authentication) {
        String username = Helper.getEmailOfLoggedInUser(authentication);

        logger.info("User logged in: {}", username);

        return "user/profile";
    }


    //user add contact page


    //user view contacts


    //user edit contact 


    //user search contact


    //user delete contact


}
