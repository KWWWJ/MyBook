package com.project.mybookplace.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mybookplace.book.dao.BookDAO;
import com.project.mybookplace.book.domain.Book;

@Service
public class BookService {
	@Autowired
	BookDAO bookDAO;
	
	public void add(Book book) {
		bookDAO.add(book);
	}
	
	public Book get(long bookId) {
		return bookDAO.get(bookId);
	}
	
	public List<Book> getAll(){
		return bookDAO.getAll();
	}
	
	public void setImg(Book book) {
		bookDAO.edit(book);
	}
}
