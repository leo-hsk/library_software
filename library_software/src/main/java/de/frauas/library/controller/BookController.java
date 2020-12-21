package de.frauas.library.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
	
	@GetMapping(value = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Optional<Book>> getBook(@PathVariable("id") int id) {

		if (bookDAO.get(id).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Optional<Book>>(bookDAO.get(id), HttpStatus.OK);
		}
	}
	
	
	@DeleteMapping(value = "/books/{id}")
	@ResponseBody
	public ResponseEntity<Object> deleteBook(@PathVariable("id") int id, UriComponentsBuilder builder) {	
		if(bookDAO.get(id).isEmpty()) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		else {
			bookDAO.delete(bookDAO.get(id).get());
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping(value = "/books", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> addBook(@RequestBody Book book, UriComponentsBuilder builder) { // RequestBody needs to be in a JSON format. See Postman "Add Book".
		bookDAO.save(book);
		UriComponents path = builder.path("books/").path(String.valueOf(book.getId())).build();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", path.toUriString());
		return new ResponseEntity<Object>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/books/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> updateStudent(@PathVariable("id") int id,
			@RequestBody Book book, UriComponentsBuilder builder) {

		String[] params = new String[5];
		params[0] = book.getTitle();
		params[1] = book.getAuthors();
		params[2] = book.getIsbn13();
		params[3] = book.getPublicationDate().toString();
		params[4] = book.getPublisher();

		if (bookDAO.get(id).isEmpty()) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		} else {
			bookDAO.update(bookDAO.get(id).get(), params);
			UriComponents path = builder.path("books/").path(String.valueOf(id)).build();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Location", path.toUriString());
			return new ResponseEntity<Object>(headers, HttpStatus.OK);
		}
	}

	
	@PatchMapping(value = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> editStudent(@PathVariable("id") int id,
												@RequestBody Book book, UriComponentsBuilder builder) {
		if(bookDAO.get(id).isEmpty()) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		else {
			
			String[] param = new String[3];
			Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			
			param[0] = String.valueOf(book.getLent());
			param[1] = String.valueOf(book.getUser().getId());
			param[2] = sqlDate.toString();
			
			bookDAO.edit(bookDAO.get(id).get(), param);
			UriComponents path = builder.path("books/").path(String.valueOf(id)).build();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Location", path.toUriString());
			return new ResponseEntity<Object>(headers, HttpStatus.OK);
		}
	}
	
	
	
}
