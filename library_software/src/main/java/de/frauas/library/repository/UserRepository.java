package de.frauas.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.frauas.library.model.User;

/**
 * UserRepository defines methods to retrieve user related data from the database.
 * @author Leonard
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u from User u WHERE u.username = :username")
	public Optional<User> findByUsername(@Param("username") String username);
	
	@Query("SELECT u from User u WHERE u.email = :email")
	public Optional<User> findbyEmail(@Param("email") String email);
	
}
