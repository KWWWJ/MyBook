package com.project.mybookplace.book.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.mybookplace.book.domain.Book;

@Repository
public class BookDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Book> mapper = new RowMapper<Book>() {
		@Override
		public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			return new Book(
					rs.getLong("book_id"),
					rs.getString("title"),
					rs.getString("description"),
					rs.getString("writer"),
					rs.getString("publication_date"),
					rs.getInt("category"),
					rs.getString("img")
					);
		}
	};
	
	public void add(Book book) {
		jdbcTemplate.update(
				"insert into book ( book_id, title, description, writer, publication_date, category, img ) values ( ?, ?, ?, ?, ?, ?, ? )",
				book.getBookId(),
				book.getTitle(),
				book.getDescription(),
				book.getWriter(),
				book.getPublicationDate(),
				book.getCategory(),
				book.getImg()
				);
	}
	
	public Book get(long bookId) {
		return jdbcTemplate.queryForObject(
				"select * from book where book_id=?", 
				mapper, bookId
			); 
	}
	
	public List<Book> getAll(){
		return jdbcTemplate.query("select * from book order by book_id", mapper);
		
	}
	
	public void edit(Book book) {
		jdbcTemplate.update("update book set image=? where book_id=?",
				book.getImg(),
				book.getBookId());
	}
}
