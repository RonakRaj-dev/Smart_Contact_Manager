package com.my_first_project.SCM.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.my_first_project.SCM.entities.Providers;
import com.my_first_project.SCM.entities.User;
import com.my_first_project.SCM.helper.AppConstants;
import com.my_first_project.SCM.repositories.UserRepositories;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepositories userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("OAuthAuthenticationSuccessHandler");

        // identify the provider

        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        var oauth2User = (DefaultOAuth2User) authentication.getPrincipal();

        oauth2User.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });

        User user = new User();
        user.setUserID(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            // google

            user.setEmail(oauth2User.getAttribute("email").toString());
            user.setProfilePic(oauth2User.getAttribute("picture").toString());
            user.setName(oauth2User.getAttribute("name").toString());
            user.setProviderID(oauth2User.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created by using Google Sign in");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            // github

            String email = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                    : oauth2User.getAttribute("login").toString() + "@gmail.com";
            String picture = oauth2User.getAttribute("avatar_url").toString();
            String name = oauth2User.getAttribute("login").toString();
            String providerId = oauth2User.getName();
            

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderID(providerId);
            user.setProvider(Providers.GITHUB);
            user.setAbout("This account is created by using Github Sign in");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("linkedin")) {
            // linkedin
        } else {
            logger.info("OAuthAuthenticationSuccessHandler: Unknown provider");
        }

        /*
         * DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
         * 
         * logger.info(user.getName());
         * 
         * user.getAttributes().forEach((key, value) -> {
         * logger.info("{} => {}", key, value);
         * });
         * 
         * logger.info(user.getAuthorities().toString());
         * 
         * String email = user.getAttribute("email").toString();
         * String name = user.getAttribute("name").toString();
         * String picture = user.getAttribute("picture").toString();
         * 
         * // create user and save in database
         * 
         * User user1 = new User();
         * user1.setEmail(email);
         * user1.setName(name);
         * user1.setProfilePic(picture);
         * user1.setPassword("password");
         * user1.setUserID(UUID.randomUUID().toString());
         * user1.setProvider(Providers.GOOGLE);
         * user1.setEnabled(true);
         * 
         * user1.setEmailVerified(true);
         * user1.setProviderID(user.getName());
         * user1.setRoleList(List.of(AppConstants.ROLE_USER));
         * 
         * User user2 = userRepo.findByEmail(email).orElse(null);
         * if(user2==null){
         * userRepo.save(user1);
         * logger.info("User Saved: " + email);
         * }
         */

        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null) {
            userRepo.save(user);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

    //
}
