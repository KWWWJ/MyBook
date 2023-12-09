package com.project.mybookplace.review.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.mybookplace.review.service.ReviewService;

@Controller
public class ReviewController {
	@Autowired
	ReviewService reviewService;
	
	@GetMapping("/review")
	public String reviewPage(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "order", required = false) String order, Model model) {
		model.addAttribute("menu", "review/reviewList");
		model.addAttribute("menuHead", "reviewListFragment");
		if(reviewService.getReviewAll(order) != null) {
			model.addAttribute("reviewList", reviewService.getReviewAll(order));
			System.out.println(model.getAttribute("reviewList"));
		}
		return "index/layout.html";
	}
	
	@GetMapping("/reviewList")
	public String reviewList(Model model){
		
		return "redirect:/review";
	}
	
	@GetMapping("/bookReviewList")
	public String BookReviewList(@RequestParam(name = "bookId", required = false) Long bookId,
			@RequestParam(name = "order", required = false) String order, Model model){
		if(reviewService.getBookReiews(bookId, order) != null) {
			model.addAttribute("reviewList", reviewService.getBookReiews(bookId, order));
		}
		return "redirect:/review";
	}
	
	@GetMapping("/genreReviewList")
	public String genreReviewList(@RequestParam(name = "genreId", required = false) int genreId,
			@RequestParam(name = "order", required = false) String order, Model model){
		if(reviewService.getGenre(genreId, order) != null) {
			model.addAttribute("reviewList", reviewService.getGenre(genreId, order));
		}
		return "redirect:/review";
	}
}
