package com.project.mybookplace.likes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.mybookplace.likes.domain.Likes;

@Repository
public class LikesDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Likes> mapper = new RowMapper<Likes>() {

		@Override
		public Likes mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			return new Likes(
					rs.getInt("id"),
					rs.getInt("user_id"),
					rs.getInt("review_id"),
					rs.getInt("is_likes") == 1
					);
		}
		
	};
	
	public void add(Likes likes) {
		jdbcTemplate.update("insert into likes ( user_id, review_id, is_likes ) values ( ?, ?, ?)",
				likes.getUserId(),
				likes.getReviewId(),
				likes.isLikes() ? 1:0
				);
		
	}
	
	public Likes get(int id) {
		return jdbcTemplate.queryForObject("select * from likes where id=?",
				mapper,
				id);
	}
	
	public Likes getUserId(int userId) {
		return jdbcTemplate.queryForObject("select * from likes where user_id=?",
				mapper,
				userId);
	}
	
	public List<Likes> getAll(int reviewId){
		return jdbcTemplate.query("select * from likes where review_id=?",
				mapper,
				reviewId
				);
	}
	
	public void delete(Likes likes) { 
		jdbcTemplate.update("delete from likes where id=?",
				likes.getId()
				);
	}
	
	public int likesCount(int reviewId) {
		return jdbcTemplate.queryForObject("select count(*) from likes where review_id=?",
				Integer.class,
				reviewId);
	}
}
