package com.my_first_project.SCM.controllers;

import com.my_first_project.SCM.forms.UserForm;
import com.my_first_project.SCM.helper.Message;
import com.my_first_project.SCM.helper.MessageType;
import com.my_first_project.SCM.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import com.my_first_project.SCM.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}

	@RequestMapping("/home")
	public String home(Model model) {
		System.out.println("Home Page Handler");
		model.addAttribute("name", "Ronak Rajendra Behera");
		model.addAttribute("youtubeChannel", "RonakFoods");
		model.addAttribute("linkedInLink", "https://www.google.com/");
		return "Home";
	}

	// About
	@RequestMapping("/about")
	public String aboutPage(Model model) {
		model.addAttribute("isLogin", true);
		System.out.println("About Page Handler");
		return "about";
	}

	// Services
	@RequestMapping("/service")
	public String servicePage() {
		System.out.println("Service Page Handler");
		return "service";
	}

	// Contact
	@GetMapping("/contact")
	public String contact() {
		return new String("contact");
	}


	// This is login page
	// Login
	@GetMapping("/login")
	public String login() {
		return new String("login");
	}

	// Registration	page
	// Sign up
	@GetMapping("/register")
	public String register(Model model) {

		UserForm userForm = new UserForm();
		// default data

		// userForm.setName("Ronak");
		// userForm.setAbout("This is about you : Write something about yourself");
		model.addAttribute("userForm", userForm);
		return new String("register");
	}

	// Processing register

	@PostMapping("/do-register")
	public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
			HttpSession session) {
		System.out.println("Processing registration");
		// fetch form data

		System.out.println(userForm);
		// validate form data

		if (rBindingResult.hasErrors()) {
			return "register";
		}

		// userservice
		// save data in database

		// User user = User.builder()
		// .name(userForm.getName())
		// .email(userForm.getEmail())
		// .password(userForm.getPassword())
		// .about(userForm.getAbout())
		// .phoneNumber(userForm.getPhoneNumber())
		// .profilePic("https://imgs.search.brave.com/zMgzISpQsidLMr4H0bJVZDgza-W9SZbpUMVLLf2dJ6Q/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9jZG5i/bG9nLnBpY3NhcnQu/Y29tLzIwMjEvMDMv/Y29tcG9zaXRpb24t/NzgweDUyMC5wbmc")
		// .build();

		User user = new User();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		user.setAbout(userForm.getAbout());
		user.setPhoneNumber(userForm.getPhoneNumber());
		user.setEnabled(false);
		user.setProfilePic(
				"https://imgs.search.brave.com/zMgzISpQsidLMr4H0bJVZDgza-W9SZbpUMVLLf2dJ6Q/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9jZG5i/bG9nLnBpY3NhcnQu/Y29tLzIwMjEvMDMv/Y29tcG9zaXRpb24t/NzgweDUyMC5wbmc");

		User savedUser = userService.saveUser(user);
		System.out.println("user saved -> ");

		// message = "Registration Successful"

		Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();

		session.setAttribute("message", message);

		// redirect login page
		return "redirect:/register";
	}

}
