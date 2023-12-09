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
	
	public List<Review> getReviewAll(String order) {
		return reviewDAO.getAll(order);
	}
	
	public List<Review> getGenre(int genreId, String order){
		return reviewDAO.getGenre(genreId, order);
	}
	
	//좋아요 순으로 가져오는 메서드.
	public List<Review> getLikes(){
		return reviewDAO.getLikes();
	}
	
	//특정 유저의 모든 리뷰를 가져오는 메서드.
	public List<Review> getUserReviews(User user, String order){
		return reviewDAO.getUserReview(user.getId(), order);
	}
	
	//책의 ISBN번호로 가저오는 메서드.
	public List<Review> getBookReiews(Long bookId, String order){
		return reviewDAO.getBookReview(bookId, order);
	}
	
	//특정 글을 수정.
	public void edit(Review review) {
		reviewDAO.edit(review);
	}
	
	//특정 글을 숨김.
	public void banR(Review review) {
		reviewDAO.edit(review);
	}
}
