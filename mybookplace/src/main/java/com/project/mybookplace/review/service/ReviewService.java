package com.project.mybookplace.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mybookplace.review.dao.ReviewDAO;
import com.project.mybookplace.review.domain.Review;
import com.project.mybookplace.user.domain.User;

@Service
public class ReviewService {
	@Autowired
	ReviewDAO reviewDAO;
	
	public void writeReview(Review review) {
		reviewDAO.add(review);
	}
	
	public Review getReview(int id) {
		return reviewDAO.get(id);
	}
	
	// 모든 리뷰 가져오는 메서드
	public List<Review> getReviewAll(int page, String order) {
		return reviewDAO.getAll(page, order);
	}
	
	// 장르별로 가져오는 메서드
	public List<Review> getGenre(int start, String genre, String order){
		return reviewDAO.getGenre(start, genre, order);
	}
	
	//특정 유저의 모든 리뷰를 가져오는 메서드.
	public List<Review> getUserReviews(User user, String order){
		return reviewDAO.getUserReview(user.getId(), order);
	}
	
	//책의 ISBN번호로 가저오는 메서드.
	public List<Review> getBookReiews(int start, Long bookId, String order){
		return reviewDAO.getBookReview(start, bookId, order);
	}
	
	//특정 글을 수정.
	public void likes(Review review, int likes) {
		review.setLikes(review.getLikes()+likes);
		reviewDAO.likesCount(review);
	}
	
	//특정 글을 수정.
	public void edit(Review review) {
		reviewDAO.edit(review);
	}
	
	//특정 글을 숨김.
	public void banR(Review review) {
		reviewDAO.ban(review);
	}
	
	public int page(int showReviewCount) {
		int page = (reviewDAO.getCount()/showReviewCount);
		if(!(reviewDAO.getCount()%showReviewCount == 0)) {
			page = page+ 1;
		}
		
		return page;
	}
	
	public int showPageNumber(int nowPage, int showCount) {
		int pageGroup = (nowPage/5) + 1;
		
		int showPage = (pageGroup-1) * showCount + 1;
				
		return showPage;
	}
	
	
}
