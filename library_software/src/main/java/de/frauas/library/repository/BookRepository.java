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

package de.frauas.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.frauas.library.model.Book;

/**
 * BookRepository defines methods to retrieve book related data from the database.
 * @author Leonard
 *
 */
public interface BookRepository extends JpaRepository<Book, Long>{
	
//	Implementing fulltext search and search ISBN in one query, because fulltext search is not 
//	possible on numeric columns.
	@Query(value = "SELECT * FROM books b LEFT JOIN book_data bd "
			+ "ON b.isbn13 = bd.isbn13 "
			+ "WHERE b.isbn13 = ?1 "
			+ "OR MATCH(bd.title, bd.authors, bd.publisher) AGAINST(?1)", nativeQuery = true)
	public List<Book> search(String keyword);
	
	@Query(value = "SELECT * from books WHERE isbn13 = ?1", nativeQuery = true)
	public List<Book> findByIsbn13(@Param("isbn13") long isbn13);
	
	@Query(value = "SELECT * FROM books WHERE lent_by_user = ?1", nativeQuery = true)
	public List<Book> findByUser(@Param("id") long id);

}
