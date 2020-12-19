package de.frauas.library.data;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.frauas.library.model.Book;
import de.frauas.library.repository.BookRepository;

@Repository
public class BookDAO implements DAO{
	
	@Autowired
	BookRepository bookRepository;

	@Override
	public Optional get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<Book> getAll() {
		List<Book> bookList = bookRepository.findAll();
		return bookList;
	}

	@Override
	public void save(Object t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Object t, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(Object t, String param) {
		// TODO Auto-generated method stub
		
	}

}
