package com.project.mybookplace.review.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.mybookplace.likes.domain.Likes;
import com.project.mybookplace.likes.service.LikesService;
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
	@Autowired
	LikesService likesService;
	
	// 리뷰 리스트로 보기
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
			model.addAttribute("reviewList", likesCount(reviewService.getReviewAll(start, order)));
		} else {
			if(reviewService.getGenre(start, genre, order).isEmpty()) {
				model.addAttribute("reviewList", null);
			} else {
				model.addAttribute("reviewList", likesCount(reviewService.getGenre(start, genre, order)));
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
	
	// likes를 따로 정리하기 위한 메서드
	private List<Review> likesCount(List list){
		List<Review> tempList = list;
		for(int i=0; i<tempList.size(); i++) {
			tempList.get(i).setLikes(likesService.likesCount(tempList.get(i).getId()));
			System.out.println("likes : "+tempList.get(i).getLikes());
		}
		return tempList;
	}
	
	// 리뷰 작성하기
	@GetMapping("/write")
	public String write(Model model){
		model.addAttribute("menu", "review/write");
		model.addAttribute("menuHead", "writeFragment");
		
		return "index/layout.html";
	}
	
	// 리뷰 보기
	@GetMapping("/read")
	public String read(@RequestParam(name = "id") Integer id, Model model, HttpSession session){
		model.addAttribute("menu", "review/read");
		model.addAttribute("menuHead", "readFragment");

		Review tempReview = reviewService.getReview(id);
		
		tempReview.setContent(tempReview.getContent().replace("\n", "<br>"));
		
		model.addAttribute("reviewData", tempReview);
		model.addAttribute("likes", likesService.likesCount(id));
		
		List<Likes> tempLikes = likesService.getAll(id);
		
		for(int i=0; i<tempLikes.size(); i++) {
			if(tempLikes.get(i).getUserId() == (Integer)session.getAttribute("userId")) {
				model.addAttribute("userLikes", true);
			} else {
				model.addAttribute("userLikes", false);
			}
		}
		
		
		
		
		return "index/layout.html";
	}
	
	// book 페이지에 리뷰 보내주기
	@GetMapping("/bookReviewList")
	public String BookReviewList(@RequestParam(name = "bookId", required = false) Long bookId,
			@RequestParam(name = "order", required = false) String order, Model model){
		if(reviewService.getBookReiews(1, bookId, order) != null) {
			model.addAttribute("reviewList", reviewService.getBookReiews(1, bookId, order));
		}
		return "redirect:/review";
	}
	
	@GetMapping("/likes")
	public String likesCount(@RequestParam(name = "id") Integer id,
			HttpSession session) {
		Review tempReview = reviewService.getReview(id);
		
		User tempUser = userService.getUser((String)session.getAttribute("userName"));
		
		// 조회시 likesService.getAll의 리스트가 비어있는 것을 잘못 가져왔을 경우 처리
		try {
			// likes의 중복 체크를 방지하기 위해 임시 객체를 만들어준다. 
			Likes tempLikes = new Likes();
			tempLikes.setUserId(tempUser.getId());
			tempLikes.setReviewId(id);
			tempLikes.setLikes(true);
			
			// likes가 하나도 없으면 조회가 불가능하기 때문에 모든 리뷰의 최초 1회는 그냥 생성한다.
			if(likesService.getAll(id).isEmpty()) {
				likesService.like(tempLikes);
			} else {
				// 만약 로그인 한 유저가 기존에 likes를 눌렀다면, 그 내용을 삭제한다. 
				for(int i=0; i<likesService.getAll(id).size(); i++) {
					if(likesService.getAll(id).get(i).getUserId() == tempLikes.getUserId()) {
						likesService.unLike(likesService.get(likesService.getAll(id).get(i).getId())); 
					} else {
						likesService.like(tempLikes);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/read?id="+id;
	}
	
	
	@GetMapping("/add")
	public String add(@RequestParam Map<String, String> data, HttpSession session) {
		Review tempReview = new Review();
		
		tempReview.setTitle(data.get("title"));
		tempReview.setBookName(data.get("book-name"));
		tempReview.setBookId(Long.parseLong(data.get("book-id")));
		tempReview.setGenre(data.get("genre"));
		tempReview.setUserName((String)session.getAttribute("userName"));
		
		User tempUser = userService.getUser(tempReview.getUserName());
		
		tempReview.setUserId(tempUser.getId());
		
		reviewService.writeReview(tempReview);
		
		return "redirect:/home";
	}
}
