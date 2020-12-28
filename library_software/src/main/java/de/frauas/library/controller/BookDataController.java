package de.frauas.library.controller;

import java.sql.Date;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.util.UriComponentsBuilder;

import de.frauas.library.data.BookDAO;
import de.frauas.library.data.BookDataDAO;
import de.frauas.library.form.BookForm;
import de.frauas.library.model.Book;
import de.frauas.library.model.BookData;

@Controller
public class BookDataController {
	
	@Autowired
	BookDataDAO bookDataDAO;
	
	@Autowired
	BookDAO bookDAO;
	
	
	@GetMapping(value = "/bookData/{isbn13}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Optional<BookData>> getBookData(@PathVariable("isbn13") long isbn13) {

		if (bookDataDAO.get(isbn13).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Optional<BookData>>(bookDataDAO.get(isbn13), HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/addBook")
	public String addBookData(Model model, @ModelAttribute("bookForm") BookForm bookForm) {

		String title = bookForm.getTitle();
		String authors = bookForm.getAuthors();
		String publisher = bookForm.getPublisher();
		Date publicationDate = bookForm.getPublicationDate();
		long isbn13 = bookForm.getIsbn13();
		
		if (title != null
				&& title.length() > 0
				&& authors != null
				&& authors.length() > 0
				&& publisher != null
				&& publisher.length() > 0
				&& publicationDate != null
				&& !String.valueOf(isbn13).isBlank()
				&& String.valueOf(isbn13).length() == 13) {
		
		BookData bookData = new BookData(title, authors, publisher, publicationDate, isbn13);
		
		bookDataDAO.save(bookData);
		
		Book book = new Book(bookDataDAO.get(bookForm.getIsbn13()).get());
		bookDAO.save(book);
		
		model.addAttribute("successMessage", "Adding book was successful.");
		return "addBook";
		}
		
		model.addAttribute("errorMessage", "Submission is not valid.");
		return "addBook";
	}
	
	@DeleteMapping(value = "/search")
	public String deleteBookData(@Param("isbn13") long isbn13, Model model) {
	
		if(bookDAO.findByIsbn13(isbn13).get(0).isLent()) {
			model.addAttribute("errorMessage", "You cannot delete book with ISBN13 '" + isbn13 + "', because it is lent.");
			return "fragments";
		}
		
		bookDAO.delete(bookDAO.findByIsbn13(isbn13).get(0));
		bookDataDAO.delete(bookDataDAO.get(isbn13).get());
		model.addAttribute("searchResult", bookDAO.getAll());
		model.addAttribute("successMessage", "Book with ISBN13 '" + isbn13 +"' deleted.");
		
		return "searchResult";
	}

}
