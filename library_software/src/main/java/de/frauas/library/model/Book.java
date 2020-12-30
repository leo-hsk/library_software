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

package de.frauas.library.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Contains book data to map book information with user.
 * Book represents the physical book which can be lent.
 * POJO defined as an Entity so that data can be persisted to the database.
 * @author Leonard
 *
 */
@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "isbn13")
	private BookData bookData;

	@Column(columnDefinition = "boolean default false")
	private boolean lent = false;
	
	@ManyToOne
	@JoinColumn(name = "lent_by_user")
	private User user;

	@Column(columnDefinition = "date default null")
	private Date lendingDate;
	
	public Book() {
	}

	public Book(BookData bookData) {
		super();
		this.bookData = bookData;
	}

	public Boolean isLent() {
		return lent;
	}

	public void setLent(Boolean lent) {
		this.lent = lent;
	}

	public long getId() {
		return id;
	}
	
	public BookData getBookData() {
		return this.bookData;
	}

	public void setBookData(BookData bookdata) {
		this.bookData = bookdata;
	}


	public Date getLendingDate() {
		return lendingDate;
	}

	public void setLendingDate(Date lendingDate) {
		this.lendingDate = lendingDate;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
