package de.frauas.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.frauas.library.form.BookForm;
import de.frauas.library.repository.BookRepository;

/**
 * This class is used to render web content related to books
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

}
