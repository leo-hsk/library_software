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

import de.frauas.library.common.BookDataAttribute;
import de.frauas.library.model.BookData;
import de.frauas.library.repository.BookDataRepository;

/**
 * Implementation of DAO Interface.
 * Defines CRUD methods to retrieve book information.
 * @author Leonard
 *
 */
@Repository
public class BookDataDAO implements DAO<BookData>{
	
	@Autowired
	BookDataRepository bookDataRepository;

	@Override
	public Optional<BookData> get(long isbn13) {
		try {
			Optional<BookData> bookData = bookDataRepository.findById(isbn13);
			return bookData;
		} catch (Exception e) {
			System.err.println("No BookData with isbn " + isbn13 + " found!");
			return Optional.empty();
		}	
	}

//	Not needed
	@Override
	public List<BookData> getAll() {
		return null;
	}

	@Override
	public void save(BookData bookData) {
		bookDataRepository.save(bookData);
	}

	@Override
	public void update(BookData bookData, String[] params) {
		bookData.setTitle(Objects.requireNonNull(params[BookDataAttribute.TITLE], "Title cannot be null!"));
		bookData.setAuthors(Objects.requireNonNull(params[BookDataAttribute.AUTHORS], "Authors cannot be null!"));
		bookData.setIsbn13(Long.parseLong(Objects.requireNonNull(params[BookDataAttribute.ISBN13], "ISBN cannot be null!")));
		bookData.setPublicationDate(Date.valueOf(Objects.requireNonNull(params[BookDataAttribute.PUBLICATION_DATE].toString(), "Publication date cannot be null!")));
		bookData.setPublisher(Objects.requireNonNull(params[BookDataAttribute.PUBLISHER], "Publisher cannot be null!"));
		
		bookDataRepository.save(bookData);
	}

	@Override
	public void delete(BookData bookData) {
		bookDataRepository.delete(bookData);
	}

//	Not needed
	@Override
	public void edit(BookData t, String[] params) {
		// TODO Auto-generated method stub
	}

}
