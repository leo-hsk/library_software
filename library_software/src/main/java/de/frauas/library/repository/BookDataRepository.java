package de.frauas.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.frauas.library.model.BookData;

public interface BookDataRepository extends JpaRepository<BookData, Long>{

}
