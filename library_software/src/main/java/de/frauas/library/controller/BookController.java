package de.frauas.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.frauas.library.data.BookDAO;
import de.frauas.library.model.Book;

@RestController
public class BookController {
	
	@Autowired
	BookDAO bookDAO;
	
	@RequestMapping("/")
	@ResponseBody
	public String welcome() {
		return "RESTful Application";
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

}
