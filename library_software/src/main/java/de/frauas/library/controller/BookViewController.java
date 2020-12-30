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

package de.frauas.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.frauas.library.form.BookForm;
import de.frauas.library.repository.BookRepository;

/**
 * This controller is used to render web content related to books.
 * @author Leonard
 *
 */
@Controller
public class BookViewController {
	
	@Autowired
	BookRepository bookRepository;
	
	@GetMapping(value = {"/addBook"})
	public String showAddBookPage(Model model) {
		BookForm bookForm = new BookForm();
		model.addAttribute("bookForm", bookForm);
		return "addBook";
	}
	
//	Not used method
	@GetMapping(value = {"/fragments"})
	public String showSearchPage(Model model) {
		return "fragments";
	}


}
