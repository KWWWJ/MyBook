package com.project.mybookplace.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.mybookplace.user.domain.User;

@Repository
public class UserDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<User> mapper = new RowMapper<User>() {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			return new User(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("user_id"),
					rs.getString("password"),
					rs.getString("email"),
					rs.getTimestamp("created_at"),
					rs.getInt("ban") == 1,
					rs.getInt("admin") == 1
					);
		}
	};
	
	public void add(User user) {
		jdbcTemplate.update(
				"insert into users ( name, user_id, password, email ) values ( ?, ?, ?, ? )",
				user.getName(),
				user.getUserId(),
				user.getPassword(),
				user.getEmail()
				);
	}
	
	public User get(int id) {
		return jdbcTemplate.queryForObject(
				"select * from users where id=?", 
				mapper, id
			);
	}
	
	public User get(String userId) {
		try {
			return jdbcTemplate.queryForObject(
					"select * from users where user_id=?", 
					mapper, userId
				);
		}catch(Exception e) {
			
		}
		return null;
	}
	
	
	public List<User> getAll(){
		return jdbcTemplate.query("select * from users order by id", mapper);
		
	}
	
	public List<User> getNameAll(String name){
		return jdbcTemplate.query("select * from users where name=? order by id", 
				mapper,
				name);
		
	}
	
	
	public void ban(User user) {
		jdbcTemplate.update("update users set ban = ? where id=?",
				user.isBan() ? 1:0,
				user.getId()
				);
	}
	
	public void admin(User user) {
		jdbcTemplate.update("update users set admin = ? where id=?",
				user.isAdmin() ? 1:0,
				user.getId()
				);
	}
	
	public void delete(int id) {
		jdbcTemplate.update("delete from users where id=?",
				id);
	}
	
	public int getCount() {
		return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
	}
}
