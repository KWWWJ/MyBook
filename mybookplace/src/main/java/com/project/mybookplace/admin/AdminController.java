package com.project.mybookplace.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mybookplace.review.domain.Review;
import com.project.mybookplace.review.service.ReviewService;
import com.project.mybookplace.user.domain.User;
import com.project.mybookplace.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	@Autowired
	ReviewService reviewService;
	@Autowired
	UserService userService;
	

	@GetMapping("/ban")
	public String ban(Model model, HttpSession session) {
		getPage(model, "admin/ban", "banFragment");
		
		if(session.getAttribute("userId") != null) {
			User user = userService.getUser((Integer)session.getAttribute("userId"));
			if(user.isAdmin() == false) {
				return "redirect:/home";
			} else {
				return "index/layout.html";
			}
		} else {
			return "redirect:/home";
		}
	}
	
	
	
	@GetMapping("/searchuser")
	public String searchUser(Model model, HttpSession session) {
		getPage(model, "admin/search-user", "searchUserFragment");
		if(session.getAttribute("userId") != null) {
			User user = userService.getUser((Integer)session.getAttribute("userId"));
			if(user.isAdmin() == false) {
				return "redirect:/home";
			} else {
				return "index/layout.html";
			}
		} else {
			return "redirect:/home";
		}
	}
	
	@ResponseBody
	@PostMapping("getUser")
	public String getUser(@RequestParam(name = "name") String name) throws JsonProcessingException {
		List<User> users = userService.seaechName(name);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonReviews = objectMapper.writeValueAsString(users);
		return jsonReviews;
	}
	
	@ResponseBody
	@PostMapping("banReview")
	public void banReview(@RequestParam(name = "id") Integer id) {
		Review review = reviewService.getReview(id);
		review.setBan(true);
		reviewService.banR(review);
	}
	
	@ResponseBody
	@PostMapping("banUser")
	public void banUser(@RequestParam(name = "id") Integer id) {
		User user = userService.getUser(id);
		user.setBan(true);
		userService.banState(user);
	}
	
	@ResponseBody
	@PostMapping("adminUser")
	public void adminUser(@RequestParam(name = "id") Integer id) {
		User user = userService.getUser(id);
		user.setAdmin(true);
		userService.adminState(user);
	}
	
	@ResponseBody
	@PostMapping("getBanReview")
	public String getBanReview(@RequestParam(name = "id", required=false) String id) throws JsonProcessingException {
		User user = userService.getUser(id);
		if(user != null) {
			List<Review> reviews = reviewService.getBanAll(user.getId());
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonReviews = objectMapper.writeValueAsString(reviews);
			return jsonReviews;
		}
		return "redirect:/ban";
	}
	
	private void getPage(Model model, String html, String fragment) {
		model.addAttribute("menu", html);
		model.addAttribute("menuHead", fragment);
	}
}
