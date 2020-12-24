package de.frauas.library.data;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import de.frauas.library.model.User;
import de.frauas.library.repository.RoleRepository;
import de.frauas.library.repository.UserRepository;

@Repository
public class UserDAO implements DAO<User>{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Optional<User> get(long id) {
		try {
			Optional<User> user = userRepository.findById(id);
			return user;
		} catch (Exception e) {
			System.err.println("No User with id " + id + " found!");
			return Optional.empty();
		}	
	}
	
	public Optional<User> get(String username) {
		try {
			Optional<User> user = userRepository.findByUsername(username);
			return user;
		} catch (Exception e) {
			System.err.println("No User with username " + username + " found!");
			return Optional.empty();
		}	
	}
	
	public Optional<User> findByEmail(String email) {
		try {
			Optional<User> user = userRepository.findbyEmail(email);
			return user;
		} catch (Exception e) {
			System.err.println("No User with email " + email + " found!");
			return Optional.empty();
		}	
	}
	
	

	@Override
	public List<User> getAll() {
		List<User> userList = userRepository.findAll();
		return userList;
	}

	@Override
	public void save(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		
		userRepository.save(user);
	}

	@Override
	public void update(User user, String[] params) {
		
		user.setUsername(Objects.requireNonNull(params[0], "Username cannot be null!"));
		user.setFirstName(Objects.requireNonNull(params[1], "First name cannot be null!"));
		user.setLastName(Objects.requireNonNull(params[2], "Last name cannot be null!"));
		user.setEmail(Objects.requireNonNull(params[3], "Email cannot be null!"));
		
		userRepository.save(user);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
		
	}

//	Not used at the moment
	@Override
	public void edit(User user, String[] params) {
		// TODO Auto-generated method stub
		
	}

}
