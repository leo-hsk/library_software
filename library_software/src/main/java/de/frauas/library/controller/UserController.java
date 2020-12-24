package de.frauas.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@GetMapping(value = {"/account"})
	public String showAccountPage(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		User user = userDAO.get(username).get();
		
		UserForm userForm = new UserForm(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole().getName().toUpperCase());
		model.addAttribute("userForm", userForm);
		return "account";
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

	@DeleteMapping(value = {"account"})
	public String deleteUser(Model model, @ModelAttribute("userForm") UserForm userForm) {
//			Überlegen, an welcher Stelle user gelöscht werden soll.
//			userDAO.delete(userDAO.get(userForm.getUsername()).get());
			return "/";
	}

	@PostMapping(value = "/register")
	public String addUser(Model model, @ModelAttribute("userForm") UserForm userForm) {
		
		String username = userForm.getUsername();
		String password = userForm.getPassword();
		String firstName = userForm.getFirstName();
		String lastName = userForm.getLastName();
		String email = userForm.getEmail();
		
		if(userDAO.findByEmail(email).get() != null) {
			model.addAttribute("errorMessage", "Username already exists.");
			return "register";
		}
		
		if(userDAO.findByEmail(email).get() != null) {
			model.addAttribute("errorMessage", "There is already an account with the given email");
			return "register";
		}
		
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

	@PutMapping(value = "/account")
	public String updateUser(Model model, @ModelAttribute("userForm") UserForm userForm) {
		
		String[] params = new String[4];
		params[0] = userForm.getUsername();
		params[1] = userForm.getFirstName();
		params[2] = userForm.getLastName();
		params[3] = userForm.getEmail();
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();

//		Keep role after reloading page.
		User user = userDAO.get(username).get();
		
		userForm.setRole(user.getRole().getName().toUpperCase());
		
		if(userDAO.get(userForm.getUsername()).isPresent()) {
			if(userDAO.get(userForm.getUsername()).get().getUsername() != user.getUsername()) {
				model.addAttribute("errorMessage", "Username already exists.");
				return "account";
			}
		}
		
		if(userDAO.findByEmail(userForm.getEmail()).isPresent()) {
			if(userDAO.findByEmail(userForm.getEmail()).get().getEmail() != user.getEmail()) {
				model.addAttribute("errorMessage", "There is already an account with the given email.");
				return "account";
			}
		}
		
		if (encoder.matches(userForm.getPassword(), userDAO.get(username).get().getPassword())) {
			userDAO.update(userDAO.get(username).get(), params);
			model.addAttribute("successMessage", "Updating account was successful.");
			return "account";
		} else {
			model.addAttribute("errorMessage", "Please enter your old password to verify changes.");
			return "account";
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
