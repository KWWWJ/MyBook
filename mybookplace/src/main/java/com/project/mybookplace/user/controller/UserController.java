package com.project.mybookplace.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.mybookplace.user.domain.User;
import com.project.mybookplace.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/regist")
	public String regist(Model model) {
		model.addAttribute("menu", "user/regist");
		model.addAttribute("menuHead", "registFragment");
		return "index/layout.html";
	}
	
	@PostMapping("/userInfo")
	public String registUser(@RequestParam Map<String, String> data) {
		System.out.println("name : "+data.get("name"));
		User user = new User();
		user.setName(data.get("name"));
		user.setUserId(data.get("userId"));
		user.setPassword(data.get("password"));
		user.setEmail(data.get("email"));
		
		userService.regist(user);
		
		return "redirect:/home";
	}
	
	@PostMapping("/login")
	public String loginPage(@RequestParam Map<String, String> data, HttpSession session) {
		
		try {
			
			User tempUser = new User();
			tempUser.setUserId(data.get("userId"));
			tempUser.setPassword(data.get("password"));
			
			User user = userService.login(tempUser);
			
			
			
			if(user != null) {
				session.setAttribute("userName", user.getName());
				session.setAttribute("userId", user.getId());
			}
			
		}catch(Exception e) {
			
		}
		
		return "redirect:/home";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.setAttribute("userName", null);
		session.setAttribute("userId", null);
		
		return "redirect:/home";
	}
}
