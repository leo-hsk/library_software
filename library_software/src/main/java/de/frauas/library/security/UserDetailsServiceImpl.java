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

package de.frauas.library.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import de.frauas.library.model.User;
import de.frauas.library.repository.UserRepository;

/**
 * UserDetailsService implementation to retrieve user-related data.
 * It is used by the DaoAuthenticationProvider to load details about the user during authentication.
 * @author Leonard
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository UserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> optUser = UserRepository.findByUsername(username);
		if(optUser.isPresent()) {
			User user = optUser.get();
			return user;
		}
//		Handling if there is no user with the given username.
		User noUser = new User();
		noUser.setEnabled(false);
		return noUser;
	}

}
