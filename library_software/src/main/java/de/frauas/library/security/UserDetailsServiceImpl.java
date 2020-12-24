package de.frauas.library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.frauas.library.model.User;
import de.frauas.library.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository UserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = UserRepository.findByUsername(username).get();
		if(user == null) {
			throw new UsernameNotFoundException("Could not find User '" + username + "'.");
		}
		
		return user;
	}

}
