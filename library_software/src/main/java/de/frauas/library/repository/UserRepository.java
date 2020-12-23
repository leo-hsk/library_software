package de.frauas.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.frauas.library.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u from User u WHERE u.username = :username")
	public User getUserByUsername(@Param("username") String username);
	
}
