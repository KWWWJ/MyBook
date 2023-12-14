package com.project.mybookplace.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.mybookplace.review.domain.Review;
import com.project.mybookplace.review.service.ReviewService;
import com.project.mybookplace.user.domain.User;
import com.project.mybookplace.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
	@Autowired
	ReviewService reviewService;
	@Autowired
	UserService userService;
	
	@GetMapping("/home")
	public String home(Model model, HttpSession session) {
		
		User tempUser = new User();
		int userId = 0;
		
		if(session.getAttribute("userId") != null) {
			userId = (Integer)session.getAttribute("userId");
			tempUser = userService.getUser(userId);
			model.addAttribute("admin", tempUser.isAdmin());
		} else {
			model.addAttribute("admin", null);
		}
		
		return "home.html";
	}
	
	@GetMapping("/book")
	public String reviewPage(@RequestParam(name = "bookid", required = false) Long bookid, Model model) {
		getPage(model, "book/book", "bookFragment");
		
		return "index/layout.html";
	}
	
	@GetMapping("/bestseller")
	public String bestsellerPage(@RequestParam(name = "page", required = false) Integer page, Model model) {
		getPage(model, "book/bestseller", "bestsellerFragment");
		return "index/layout.html";
	}
	
	@GetMapping("/genre")
	public String genrePage(@RequestParam(name = "page", required = false) Integer page, Model model) {
		getPage(model, "book/bookList", "bookListFragment");
		model.addAttribute("nowPage", page);
		
		pageCount(model, page);
		
		return "index/layout.html";
	}
	
	@GetMapping("/upload")
	public String upload(Model model) {
		getPage(model, "admin/upload", "uploadFragment");
		return "index/layout.html";
	}
	
	
	
	private void pageCount(Model model, int page) {
		int newPage = ( page / 5 ) +1;
		int pageCount = 0;
		int startPage = 0;
		
		if(page < 21) {
			
			if(page % 5 == 0) {
				newPage = newPage - 1;
			}
			startPage = (newPage - 1) * 5 + 1;

			pageCount = newPage * 5;
		}
		
		model.addAttribute("startPage", startPage);
		model.addAttribute("pageCount", pageCount);
	}
	
	private void getPage(Model model, String html, String fragment) {
		model.addAttribute("menu", html);
		model.addAttribute("menuHead", fragment);
	}
}
