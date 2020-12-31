/*
 * Copyright (c) 2020 Leonard Hußke. All rights reserved.
 * 
 * University:		Frankfurt University of Applied Sciences
 * Study program:	Engineering Business Information Systems
 * Semester:		Web-basierte Anwendungenssysteme 20/21
 * Professor:		Prof. Dr. Armin Lehmann
 * Date:			30.12.2020
 * 
 * Author:			Leonard Hußke
 * Email:			leonard.husske@stud.fra-uas.de
 */

package de.frauas.library.data;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.frauas.library.common.BookAttribute;
import de.frauas.library.common.BookDataAttribute;
import de.frauas.library.model.Book;
import de.frauas.library.repository.BookDataRepository;
import de.frauas.library.repository.BookRepository;
import de.frauas.library.repository.UserRepository;

/**
 * Implementation of DAO Interface.
 * Defines CRUD methods to retrieve book data.
 * @author Leonard
 *
 */
@Repository
public class BookDAO implements DAO<Book>{
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookDataRepository bookDataRepository;
	
	@Override
	public List<Book> getAll() {
		List<Book> bookList = bookRepository.findAll();
		return bookList;
	}

	@Override
	public Optional<Book> get(long id) {
		try {
			Optional<Book> book = bookRepository.findById(id);
			return book;
		} catch (Exception e) {
			System.err.println("No Book with id " + id + " found!");
			return Optional.empty();
		}	
	}

	@Override
	public void save(Book book) {
		bookRepository.save(book);
	}

	@Override
	public void update(Book book, String[] params) {
		book.setBookData(bookDataRepository.findById(Long.parseLong(Objects.requireNonNull(params[BookDataAttribute.ISBN13], "ISBN cannot be null!"))).get());
		
		bookRepository.save(book);
	}

	@Override
	public void delete(Book book) {
		bookRepository.delete(book);
	}

	@Override
	public void edit(Book book, String[] param) {
		
		if(param == null) {
			book.setLent(false);
			book.setLendingDate(null);
			book.setUser(null);
		}
		else {
			book.setLent(Boolean.valueOf(param[BookAttribute.LENT]));
			book.setUser(userRepository.findByUsername(param[BookAttribute.LENT_BY_USER]).get());
			book.setLendingDate(Date.valueOf(param[BookAttribute.LENDING_DATE]));
		}
		bookRepository.save(book);
	}
	
	public List<Book> search(String keyword) {
		return bookRepository.search(keyword);
	}
	
	public List<Book> findByIsbn13(long isbn13) {
		return bookRepository.findByIsbn13(isbn13);
	}
	
	public List<Book> findByUser(long id) {
		return bookRepository.findByUser(id);
	}
}
