package de.frauas.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import de.frauas.library.model.BookData;

/**
 * BookDataRepository defines methods to retrieve user related data from the database.
 * @author Leonard
 *
 */
public interface BookDataRepository extends JpaRepository<BookData, Long>{

}
