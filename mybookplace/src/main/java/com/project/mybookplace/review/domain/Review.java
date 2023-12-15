package com.project.mybookplace.review.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Review {
	private int id;
	private int userId;
	private Long bookId;
	private String bookCid;
	private String bookName;
	private String genre;
	private String title;
	private String content;
	private int likes;
	private boolean isBan = false;
	private Timestamp createdAt;
	private String userName;
	private String userAcountId;
}
