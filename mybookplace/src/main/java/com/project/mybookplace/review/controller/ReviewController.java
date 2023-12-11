package com.project.mybookplace.review.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.mybookplace.review.domain.Review;
import com.project.mybookplace.review.service.ReviewService;
import com.project.mybookplace.user.domain.User;
import com.project.mybookplace.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReviewController {
	@Autowired
	ReviewService reviewService;
	@Autowired
	UserService userService;
	
	@GetMapping("/review")
	public String reviewPage(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "genre", required = false) String genre,
			Model model) {
		
		// 페이지 헤더와 내용 HTML 불러오기
		model.addAttribute("menu", "review/reviewList");
		model.addAttribute("menuHead", "reviewListFragment");
		
		// 페이지 마다 10개의 리뷰를 가져오도록 시작점 설정
		int start = (page-1)*10;
		
		// 모든 리뷰를 가져와 order 파라미터의 기준으로 정렬
		if(genre.equals("기본")) {
			model.addAttribute("reviewList", reviewService.getReviewAll(start, order));
		} else {
			if(reviewService.getGenre(start, genre, order).isEmpty()) {
				model.addAttribute("reviewList", null);
			} else {
				model.addAttribute("reviewList", reviewService.getGenre(start, genre, order));
			}
		}
		
		
		// 페이지 수 보내주기(value는 한 페이지에 표시될 리뷰의 수)
		model.addAttribute("pageLength", reviewService.page(10));
		
		// 현재 페이지 보내주기
		model.addAttribute("nowPage", page);
		
		// 사용자에게 보여질 페이지 개수 보내주기
		model.addAttribute("startPage", reviewService.showPageNumber(page, 5));
		
		return "index/layout.html";
	}
	
	@GetMapping("/write")
	public String write(Model model){
		model.addAttribute("menu", "review/write");
		model.addAttribute("menuHead", "writeFragment");
		
		return "index/layout.html";
	}
	
	@GetMapping("/bookReviewList")
	public String BookReviewList(@RequestParam(name = "bookId", required = false) Long bookId,
			@RequestParam(name = "order", required = false) String order, Model model){
		if(reviewService.getBookReiews(1, bookId, order) != null) {
			model.addAttribute("reviewList", reviewService.getBookReiews(1, bookId, order));
		}
		return "redirect:/review";
	}
	
	@GetMapping("/add")
	public String add(@RequestParam Map<String, String> data, HttpSession session) {
		Review tempReview = new Review();
		
		tempReview.setTitle(data.get("title"));
		tempReview.setBookName(data.get("book-name"));
		tempReview.setContent(data.get("content"));
		tempReview.setBookId(Long.parseLong(data.get("book-id")));
		tempReview.setGenre(data.get("genre"));
		tempReview.setUserName((String)session.getAttribute("userName"));
		
		System.out.println("title : "+data.get("title"));
		System.out.println("book-name : "+data.get("book-name"));
		System.out.println("content : "+data.get("content"));
		System.out.println("book-id : "+data.get("book-id"));
		System.out.println("genre : "+data.get("genre"));
		System.out.println("userName : "+(String)session.getAttribute("userName"));
		
		User tempUser = userService.getUser(tempReview.getUserName());
		
		tempReview.setUserId(tempUser.getId());
		
		reviewService.writeReview(tempReview);
		
		return "redirect:/home";
	}
}
