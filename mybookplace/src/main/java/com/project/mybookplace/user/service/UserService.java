package com.project.mybookplace.user.service;

import java.security.MessageDigest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mybookplace.user.dao.UserDAO;
import com.project.mybookplace.user.domain.User;

@Service
public class UserService {
	@Autowired
	UserDAO userDAO;
	
	public void regist(User user) {
		user.setPassword(cryptoPassword(user.getPassword()));
		userDAO.add(user);
	}
	
	private String cryptoPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] sha256Hash = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : sha256Hash) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User login(User user) {
		User tempUser = new User();
		for(int i=0; i< userDAO.getAll().size(); i++) {
			if((user.getUserId().equals(userDAO.getAll().get(i).getUserId()))&&
					(cryptoPassword(user.getPassword()).equals(userDAO.getAll().get(i).getPassword()))) {
				tempUser= userDAO.getAll().get(i);
				return tempUser;
			}
		}
		return null;
	}
	
	public User searchUser(String userId) {
		return userDAO.get(userId);
	}
	
	public void banState(User user) {
		userDAO.ban(user);
	}
	
	public void adminState(User user) {
		userDAO.admin(user);
	}
	
	public List<User> seaechAll(){
		return userDAO.getAll();
	}
}
