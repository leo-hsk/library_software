package de.frauas.library.data;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.frauas.library.model.BookData;
import de.frauas.library.repository.BookDataRepository;

/**
 * Implementation of DAO Interface.
 * Defines CRUD methods to retrieve book information.
 * @author Leonard
 *
 */
@Repository
public class BookDataDAO implements DAO<BookData>{
	
	@Autowired
	BookDataRepository bookDataRepository;

	@Override
	public Optional<BookData> get(long isbn13) {
		try {
			Optional<BookData> bookData = bookDataRepository.findById(isbn13);
			return bookData;
		} catch (Exception e) {
			System.err.println("No BookData with isbn " + isbn13 + " found!");
			return Optional.empty();
		}	
	}

//	Not needed
	@Override
	public List<BookData> getAll() {
		return null;
	}

	@Override
	public void save(BookData bookData) {
		bookDataRepository.save(bookData);
	}

	@Override
	public void update(BookData bookData, String[] params) {
		bookData.setTitle(Objects.requireNonNull(params[0], "Title cannot be null!"));
		bookData.setAuthors(Objects.requireNonNull(params[1], "Authors cannot be null!"));
		bookData.setIsbn13(Long.parseLong(Objects.requireNonNull(params[2], "ISBN cannot be null!")));
		bookData.setPublicationDate(Date.valueOf(Objects.requireNonNull(params[3].toString(), "Publication date cannot be null!")));
		bookData.setPublisher(Objects.requireNonNull(params[4], "Publisher cannot be null!"));
		
		bookDataRepository.save(bookData);
	}

	@Override
	public void delete(BookData bookData) {
		bookDataRepository.delete(bookData);
	}

//	Not needed
	@Override
	public void edit(BookData t, String[] params) {
		// TODO Auto-generated method stub
	}

}
