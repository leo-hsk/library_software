package de.frauas.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.frauas.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	
	@Query(value = "SELECT * FROM books b LEFT JOIN book_data bd "
			+ "ON b.isbn13 = bd.isbn13 "
			+ "WHERE b.isbn13 = ?1 "
			+ "OR MATCH(bd.title, bd.authors, bd.publisher) AGAINST(?1)", nativeQuery = true)
	public List<Book> search(String keyword);
	
	@Query(value = "SELECT * from books WHERE isbn13 = ?1", nativeQuery = true)
	public List<Book> findByIsbn13(@Param("isbn13") long isbn13);

}
