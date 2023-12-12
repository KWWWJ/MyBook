package com.project.mybookplace.likes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Likes {
	private int id;
	private int userId;
	private int reviewId;
	private boolean isLikes;
}
