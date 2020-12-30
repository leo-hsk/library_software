/*
 * Copyright (c) 2020 Leonard Hußke. All rights reserved.
 * 
 * University:		Frankfurt University of Applied Sciences
 * Study program:	Engineering Business Information Systems
 * Semester:		Web-basierte Anwendungenssysteme 20/21
 * Professor:		Prof. Dr. Armin Lehmann
 * Date:			30.12.2020
 * 
 * Author:			Leonard Hußke
 * Email:			leonard.husske@stud.fra-uas.de
 */

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
