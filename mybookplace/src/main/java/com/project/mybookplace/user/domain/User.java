package com.project.mybookplace.user.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
	private int id;
	private String name;
	private String userId;
	private String password;
	private String email;
	private Timestamp cratedAt;
	private boolean ban;
	private boolean admin;
}
