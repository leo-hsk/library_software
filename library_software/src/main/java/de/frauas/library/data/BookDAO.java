package de.frauas.library.data;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.frauas.library.model.Book;
import de.frauas.library.repository.BookDataRepository;
import de.frauas.library.repository.BookRepository;
import de.frauas.library.repository.UserRepository;

@Repository
public class BookDAO implements DAO<Book>{
	
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookDataRepository bookDataRepository;
	
	@Override
	public List<Book> getAll() {
		List<Book> bookList = bookRepository.findAll();
		return bookList;
	}


	@Override
	public Optional<Book> get(long id) {
		try {
			Optional<Book> book = bookRepository.findById(id);
			return book;
		} catch (Exception e) {
			System.err.println("No Book with id " + id + " found!");
			return Optional.empty();
		}	
	}


	@Override
	public void save(Book book) {
		bookRepository.save(book);
	}


	@Override
	public void update(Book book, String[] params) {
		
		book.setTitle(Objects.requireNonNull(params[0], "Title cannot be null!"));
		book.setAuthors(Objects.requireNonNull(params[1], "Authors cannot be null!"));
		book.setBookData(bookDataRepository.findById(Long.parseLong(Objects.requireNonNull(params[2], "ISBN cannot be null!"))).get());
		book.setPublicationDate(Date.valueOf(Objects.requireNonNull(params[3].toString(), "Publication date cannot be null!")));
		book.setPublisher(Objects.requireNonNull(params[4], "Publisher cannot be null!"));
		
		bookRepository.save(book);
	}


	@Override
	public void delete(Book book) {
		bookRepository.delete(book);
	}


	@Override
	public void edit(Book book, String[] param) {
		
		if(param == null) {
			book.setLent(false);
			book.setLendingDate(null);
			book.setUser(null);
		}
		else {
		book.setLent(Boolean.valueOf(param[0]));
		book.setUser(userRepository.getOne(Long.valueOf(param[1])));
		book.setLendingDate(Date.valueOf(param[2]));
		}
		
		bookRepository.save(book);
	}


}
