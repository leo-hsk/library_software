package de.frauas.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import de.frauas.library.data.UserDAO;
import de.frauas.library.form.UserForm;
import de.frauas.library.model.User;
import de.frauas.library.repository.RoleRepository;

@Controller
public class UserController {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping(value = {"/register"})
	public String showRegistrationPage(Model model) {
		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);
		return "register";
	}

	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<User>> getUsers() {
		if (userDAO.getAll().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(userDAO.getAll(), HttpStatus.OK);
		}
	}

	@GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Optional<User>> getUser(@PathVariable("id") long id) {

		if (userDAO.get(id).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Optional<User>>(userDAO.get(id), HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "/users/{id}")
	@ResponseBody
	public ResponseEntity<Object> deleteUser(@PathVariable("id") long id, UriComponentsBuilder builder) {
		if (userDAO.get(id).isEmpty()) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		} else {
			userDAO.delete(userDAO.get(id).get());
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping(value = "/register")
	public String addUser(Model model, @ModelAttribute("userForm") UserForm userForm) {
		
		String username = userForm.getUsername();
		String password = userForm.getPassword();
		String firstName = userForm.getFirstName();
		String lastName = userForm.getLastName();
		String email = userForm.getEmail();
		
		if(username.length() > 0
				&& username != null
				&& password.length() > 0
				&& password != null
				&& firstName.length() >0
				&& firstName != null
				&& lastName.length() >0
				&& lastName != null
				&& email.length() >0
				&& email != null) {
			
			User user = new User(username, password, firstName, lastName, email, roleRepository.getOne((long)2));
			userDAO.save(user);
			model.addAttribute("successMessage", "Registration was successful.");
			return "register";
		}
		
		model.addAttribute("errorMessage", "Registration was not successful.");
		return "register";
	}

//Not needed
	@PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> updateUser(@PathVariable("id") long id, @RequestBody User user,
			UriComponentsBuilder builder) {

		String[] params = new String[6];
		params[0] = user.getUsername();
		params[1] = user.getPassword();
		params[2] = String.valueOf(user.getRole().getId());
		params[3] = user.getFirstName();
		params[4] = user.getLastName();
		params[5] = user.getEmail();

		if (userDAO.get(id).isEmpty()) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		} else {
			userDAO.update(userDAO.get(id).get(), params);
			UriComponents path = builder.path("users/").path(String.valueOf(id)).build();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Location", path.toUriString());
			return new ResponseEntity<Object>(headers, HttpStatus.OK);
		}
	}

//  Not needed right now
	@PatchMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> editBook(@PathVariable("id") long id, @RequestBody User user,
			UriComponentsBuilder builder) {
		
//			userDAO.edit(userDAO.get(id).get(), param);
			UriComponents path = builder.path("users/").path(String.valueOf(id)).build();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Location", path.toUriString());
			return new ResponseEntity<Object>(headers, HttpStatus.OK);
	}

}
