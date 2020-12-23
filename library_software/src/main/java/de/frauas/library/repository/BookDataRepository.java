package de.frauas.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import de.frauas.library.model.BookData;

public interface BookDataRepository extends JpaRepository<BookData, Long>{

}
