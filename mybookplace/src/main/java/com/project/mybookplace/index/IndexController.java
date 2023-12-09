package com.project.mybookplace.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	
	@GetMapping("/home")
	public String home(Model model) {
		return "home.html";
	}
	
	@GetMapping("/book")
	public String reviewPage(@RequestParam(name = "bookid", required = false) Long bookid, Model model) {
		model.addAttribute("menu", "menu/book");
		model.addAttribute("menuHead", "bookFragment");
		return "index/layout.html";
	}
	
	@GetMapping("/bestseller")
	public String bestsellerPage(@RequestParam(name = "page", required = false) Integer page, Model model) {
		model.addAttribute("menu", "menu/bestseller");
		model.addAttribute("menuHead", "bestsellerFragment");
		return "index/layout.html";
	}
	
	@GetMapping("/genre")
	public String genrePage(@RequestParam(name = "page", required = false) Integer page, Model model) {
		model.addAttribute("menu", "menu/bookList");
		model.addAttribute("menuHead", "bookListFragment");
		return "index/layout.html";
	}
	

}
