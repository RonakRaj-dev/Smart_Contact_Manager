package com.my_first_project.SCM.helper;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    

    public static String getEmailOfLoggedInUser(Authentication authentication){

        // agar user apne email id se login kiya to email kaise nikalenge

        if(authentication instanceof OAuth2AuthenticatedPrincipal){


            var oauth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            var clientID = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User)authentication.getPrincipal();

            String username = "";

            if(clientID.equalsIgnoreCase("google")){

                // agar google sign in kiya hai to kaise 

                System.out.println("Getting email from google client");
                username=oauth2User.getAttribute("email").toString();


            } else if (clientID.equalsIgnoreCase("github")) {
                // agar github sign in kiya hai to kaise

                System.out.println("Getting email from github client");
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                : oauth2User.getAttribute("login").toString() + "@gmail.com";
            }
            
            return username;

        } else {
            System.out.println("Getting data from local database");
            return authentication.getName();
        }

    }
}
