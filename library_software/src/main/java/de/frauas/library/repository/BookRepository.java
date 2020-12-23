package de.frauas.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.frauas.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
