package com.project.mybookplace.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Book {
	private Long bookId;
	private String title;
	private String description;
	private String writer;
	private String publicationDate;
	private int category;
	private String img;
}
