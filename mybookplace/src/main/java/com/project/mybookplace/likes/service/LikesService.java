package com.project.mybookplace.likes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mybookplace.likes.dao.LikesDAO;
import com.project.mybookplace.likes.domain.Likes;

@Service
public class LikesService {
	@Autowired
	LikesDAO likesDAO;
	
	public void like(Likes likes) {
		likesDAO.add(likes);
	}
	
	public Likes get(int id) {
		return likesDAO.get(id);
	}
	
	public List<Likes> getAll(int reviewId){
		return likesDAO.getAll(reviewId);
	}
	
	public int likesCount(int reviewId) {
		return likesDAO.likesCount(reviewId);
	}
	
	public void unLike(Likes likes) {
		likesDAO.delete(likes);
	}
}
