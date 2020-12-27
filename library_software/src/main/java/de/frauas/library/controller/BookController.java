package de.frauas.library.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import de.frauas.library.data.BookDAO;
import de.frauas.library.data.UserDAO;
import de.frauas.library.model.Book;

@Controller
public class BookController {
	
	@Autowired
	BookDAO bookDAO;
	
	@Autowired
	UserDAO userDAO;
	
	
	@GetMapping(value = {"/", "/index"})
	public String welcome() {
		return "index";
	}
	
	
	@GetMapping(value = "/books",
				produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Book>> getBooks() {
		if (bookDAO.getAll().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>(bookDAO.getAll(), HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Optional<Book>> getBook(@PathVariable("id") long id) {

		if (bookDAO.get(id).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Optional<Book>>(bookDAO.get(id), HttpStatus.OK);
		}
	}
	
	
	@DeleteMapping(value = "/books/{id}")
	@ResponseBody
	public ResponseEntity<Object> deleteBook(@PathVariable("id") long id, UriComponentsBuilder builder) {	
		if(bookDAO.get(id).isEmpty()) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		else {
			bookDAO.delete(bookDAO.get(id).get());
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping(value = "/search")
	public String search(@Param("keyword") String keyword, Model model) {
		
		if(keyword.isBlank()) {
			model.addAttribute("searchResult", bookDAO.getAll());
			model.addAttribute("keyword", "All books");
			model.addAttribute("pageTitle", "All books");
		}else {
			model.addAttribute("searchResult", bookDAO.search(keyword));
			model.addAttribute("keyword", keyword);
			model.addAttribute("pageTitle", "Search results of \'" + keyword + "\'");
		}	
		return "searchResult";
	}
	
	
	@PatchMapping(value = "/search")
	public String lendBook(@Param("isbn13") long isbn13, Model model) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		
		Book book = bookDAO.findByIsbn13(isbn13).get(0);
			
		String[] param = new String[3];
		Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		
		if(!book.isLent()) {
			param [0] = "true";
			param[1] = username;
			param[2] = sqlDate.toString();		
		} else {
			param = null;
		}	
		bookDAO.edit(book, param);
//		Add model.addAttribute when you know the right page to redirect
		return "searchResult";
	}
	
	
	@GetMapping(value = "books")
	public String listBooks(Model model) {
		model.addAttribute("books", bookDAO.getAll());
		return "books";
	}
	
	@GetMapping(value = "/myBooks")
	public String listBooksFromUser(Model model) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		
		model.addAttribute("books", bookDAO.findByUser(userDAO.get(username).get().getId()));
		return "myBooks";
	}
	
	
	
}
