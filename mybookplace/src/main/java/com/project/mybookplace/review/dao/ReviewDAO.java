package com.project.mybookplace.review.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.mybookplace.review.domain.Review;

@Repository
public class ReviewDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Review> mapper = new RowMapper<Review>() {

		@Override
		public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			return new Review(
					rs.getInt("id"),
					rs.getInt("user_id"),
					rs.getLong("book_id"),
					rs.getInt("genre_id"),
					rs.getString("title"),
					rs.getString("content"),
					rs.getInt("likes"),
					rs.getInt("is_ban") == 1,
					rs.getTimestamp("created_at"),
					rs.getString("user_name")
					);
		}
		
	};
	
	public void add(Review review) { 
		jdbcTemplate.update("insert into reviews ( user_id, book_id, genre_id, title, content ) values ( ?, ?, ?, ?, ?)",
				review.getUserId(),
				review.getBookId(),
				review.getGenreId(),
				review.getTitle(),
				review.getContent());
	}
	
	public void edit(Review review) { 
		jdbcTemplate.update("update reviews book_id=?, genre_id=?, title=?, content=? set where id=?",
				review.getBookId(),
				review.getGenreId(),
				review.getTitle(),
				review.getContent());
	}
	
	
	public Review get(int id) {
		return jdbcTemplate.queryForObject(
				"select * from reviews where id=?", 
				mapper, id
			);
	}
	
	public List<Review> getAll(String order){
		return jdbcTemplate.query("select a.*, b.name as user_name from reviews a join users b on a.user_id=b.id order by a."+order,
				mapper
				);
		
	}

	public List<Review> getBookReview(Long bookId, String order){
		return jdbcTemplate.query("select a.*, b.name as user_name from reviews a join users b on a.user_id=b.id where a.book_id=? order by a."+order,
				mapper,
				bookId
				);
	}
	
	public List<Review> getGenre(int genre, String order){
		return jdbcTemplate.query("select a.*, b.name as user_name from reviews a join users b on a.user_id=b.id where a.genre_id=? order by a."+order,
				mapper,
				genre
				);
	}
	
	public List<Review> getLikes(){
		return jdbcTemplate.query("select a.*, b.name as user_name from reviews a join users b on a.user_id=b.id order by a.likes",
				mapper
				);
	}
	
	public List<Review> getUserReview(int userId, String order){
		return jdbcTemplate.query("select a.*, b.name as user_name from reviews a join users b on a.user_id=b.id where a.user_id=? order by a."+order,
				mapper,
				userId
				);
	}
	
	public void ban(Review review) { 
		jdbcTemplate.update("update reviews is_ban=? set where id=?",
				review.isBan() ? 1:0
						);
	}
	
}