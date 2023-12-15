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
					rs.getString("book_cid"),
					rs.getString("book_name"),
					rs.getString("genre"),
					rs.getString("title"),
					rs.getString("content"),
					rs.getInt("review_likes"),
					rs.getInt("is_ban") == 1,
					rs.getTimestamp("created_at"),
					rs.getString("user_name"),
					rs.getString("user_acount_id")
					);
		}
		
	};
	
	public void add(Review review) { 
		jdbcTemplate.update("insert into reviews ( user_id, book_id, book_cid, book_name, genre, title, content ) values ( ?, ?, ?, ?, ?, ?, ? )",
				review.getUserId(),
				review.getBookId(),
				review.getBookCid(),
				review.getBookName(),
				review.getGenre(),
				review.getTitle(),
				review.getContent()
				);
	}
	
	public void edit(Review review) { 
		jdbcTemplate.update("update reviews set book_id=?, book_cid=?, book_name=?, genre=?, title=?, content=? where id=?",
				review.getBookId(),
				review.getBookCid(),
				review.getBookName(),
				review.getGenre(),
				review.getTitle(),
				review.getContent(),
				review.getId()
				);
	}
	
	
	public Review get(int id) {
		return jdbcTemplate.queryForObject(
				"select a.*, b.name as user_name, b.user_id as user_acount_id from reviews a join users b on a.user_id=b.id where a.id=?", 
				mapper, id
			);
	}
	
	// 페이지당 10개의 리뷰 출력, 시작지점 설정, 정렬 기준(최근순, 좋아요 순)
	public List<Review> getAll(int start, String order){
		return jdbcTemplate.query("select a.*, b.name as user_name, b.user_id as user_acount_id from reviews a join users b on a.user_id=b.id where a.is_ban=0 order by a."+order+" desc limit ?, 10",
				mapper,
				start
				);
		
	}
	
	// 페이지당 10개의 리뷰 출력, 시작지점 설정, 정렬 기준(최근순, 좋아요 순)
		public List<Review> getBanAll(int userId){
			return jdbcTemplate.query("select a.*, b.name as user_name, b.user_id as user_acount_id from reviews a join users b on a.user_id=b.id where a.user_id=? and a.is_ban=1 order by a.id desc",
					mapper,
					userId
					);
			
		}

	public List<Review> getBookReviewId(int start, Long bookId, String order){
		return jdbcTemplate.query("select a.*, b.name as user_name, b.user_id as user_acount_id from reviews a join users b on a.user_id=b.id where a.book_id=? and a.is_ban=0 order by a."+order+" desc limit ?, 10",
				mapper,
				bookId,
				start
				);
	}
	
	public List<Review> getBookReviewCid(int start, String bookCid, String order){
		return jdbcTemplate.query("select a.*, b.name as user_name, b.user_id as user_acount_id from reviews a join users b on a.user_id=b.id where a.book_cid=? and a.is_ban=0 order by a."+order+" desc limit ?, 10",
				mapper,
				bookCid,
				start
				);
	}
	
	public List<Review> getGenre(int start, String genre, String order){
		return jdbcTemplate.query("select a.*, b.name as user_name, b.user_id as user_acount_id from reviews a join users b on a.user_id=b.id where a.genre=? and is_ban=0 order by a."+order+" desc limit ?, 10",
				mapper,
				genre,
				start
				);
	}
	
	public List<Review> getUserReview(int userId, String order){
		return jdbcTemplate.query("select a.*, b.name as user_name, b.user_id as user_acount_id from reviews a join users b on a.user_id=b.id where a.user_id=? and is_ban=0 order by a."+order+" desc",
				mapper,
				userId
				);
	}
	
	public void ban(Review review) { 
		jdbcTemplate.update("update reviews set is_ban=? where id=?",
				review.isBan() ? 1:0,
				review.getId()
						);
	}
	
	public void likesCount(Review review) { 
		jdbcTemplate.update("update reviews set review_likes=? where id=?",
				review.getLikes(),
				review.getId()
						);
	}
	
	public int getCount() {
		return jdbcTemplate.queryForObject("select count(*) from reviews where is_ban=0", Integer.class);
	}
	
}
