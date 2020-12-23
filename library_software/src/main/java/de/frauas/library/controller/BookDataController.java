package de.frauas.library.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import de.frauas.library.data.BookDataDAO;
import de.frauas.library.model.BookData;

@RestController
public class BookDataController {
	
	@Autowired
	BookDataDAO bookDataDAO;
	
	@GetMapping(value = "/bookData/{isbn13}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Optional<BookData>> getBookData(@PathVariable("isbn13") long isbn13) {

		if (bookDataDAO.get(isbn13).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Optional<BookData>>(bookDataDAO.get(isbn13), HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/bookData", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> addBookData(@RequestBody BookData bookData, UriComponentsBuilder builder) {
		bookDataDAO.save(bookData);
		UriComponents path = builder.path("/bookData").path(String.valueOf(bookData.getIsbn13())).build();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", path.toUriString());
		return new ResponseEntity<Object>(headers, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/bookData/{isbn13}")
	@ResponseBody
	public ResponseEntity<Object> deleteBookData(@PathVariable("isbn13") long isbn13, UriComponentsBuilder builder) {	
		if(bookDataDAO.get(isbn13).isEmpty()) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		else {
			bookDataDAO.delete(bookDataDAO.get(isbn13).get());
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}

}
