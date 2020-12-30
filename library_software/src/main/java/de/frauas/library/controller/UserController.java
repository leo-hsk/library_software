package de.frauas.library.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.frauas.library.data.BookDAO;
import de.frauas.library.data.UserDAO;
import de.frauas.library.form.UserForm;
import de.frauas.library.model.User;
import de.frauas.library.repository.RoleRepository;
import de.frauas.library.utility.UserManagerUtils;

/**
 * Controller to handle requests related to users.
 * @author Leonard
 *
 */
@Controller
public class UserController {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	BookDAO bookDAO;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/login")
	public String showLoginPage(HttpServletRequest request, Model model, @ModelAttribute("del") String del) {
		
//		Check if user is not authenticated.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			HttpSession session = request.getSession(false);
			if(session != null) {
				AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
				if(ex != null) {
//					Print message why login was not successful. If there is a message.
					model.addAttribute("errorMessage", ex.getMessage());
				}
			}
			return "login";
		}
		
//		Check if user got deleted by checking ModelAttribute.
		if(!del.isBlank()) {		
//			Delete authentication and print message that account is deleted.
			SecurityContextHolder.getContext().setAuthentication(null);
			model.addAttribute("success", del);
			return "login";
			}	
		
//		Login successful.
		return "redirect:/";
	}
	
	@GetMapping(value = "/register")
	public String showRegistrationPage(Model model) {
		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);
		return "register";
	}
	
	@GetMapping(value = "/account")
	public String showAccountPage(Model model) {
		
		String username = UserManagerUtils.getCurrentUserName();
		User user = userDAO.get(username).get();
		
//		Load user information into thymeleaf.
		UserForm userForm = new UserForm(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole().getName().toUpperCase());
		model.addAttribute("userForm", userForm);
		return "account";
	}

	@GetMapping(value = "/users")
	public String ShowAllUsersPage(Model model) {
		model.addAttribute("users", userDAO.getAll());
		return "users";
	}

	@DeleteMapping(value = "/account")
	public String deleteUser(Model model, @ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult, RedirectAttributes redirectAttrs) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String username = UserManagerUtils.getCurrentUserName();

//		Safe user role for thymeleaf.
		User user = userDAO.get(username).get();
		userForm.setRole(user.getRole().getName().toUpperCase());
		
//		Check if entered password is correct.
		if (encoder.matches(userForm.getPassword(), userDAO.get(username).get().getPassword())) {
			if(bookDAO.findByUser(userDAO.get(username).get().getId()).isEmpty()) {
				userDAO.delete(user);
				redirectAttrs.addFlashAttribute("del", "Your account has been deleted.");
				return "redirect:/login?logout";
			}
			model.addAttribute("errorMessage", "You need to return books before you can delete your account.");
			return "account";
			
		} else {
			model.addAttribute("errorMessage", "Please enter your correct password to confirm deletion.");
			return "account";
		}
	}
	
	@DeleteMapping(value = "/users")
	public String deleteUser(@Param("username") String username, Model model) {
//		Check if user has no more lent books.
		if(bookDAO.findByUser(userDAO.get(username).get().getId()).isEmpty()) {
			userDAO.delete(userDAO.get(username).get());
			model.addAttribute("users", userDAO.getAll());
			model.addAttribute("successMessage", "Deleting user was successful.");
			return "users";
		}
		model.addAttribute("users", userDAO.getAll());
		model.addAttribute("errorMessage", "User needs to return books before you can delete the account.");
		return "users";	
	}

	@PostMapping(value = "/register")
	public String addUser(Model model, @ModelAttribute("userForm") UserForm userForm) {

		String username = userForm.getUsername();
		String password = userForm.getPassword();
		String firstName = userForm.getFirstName();
		String lastName = userForm.getLastName();
		String email = userForm.getEmail();
		
		if(userDAO.get(username).isPresent()) {
			model.addAttribute("errorMessage", "Username already exists.");
			return "register";
		}
		
		if(userDAO.findByEmail(email).isPresent()) {
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
		
		String username = UserManagerUtils.getCurrentUserName();

//		Safe user role for thymeleaf.
		User user = userDAO.get(username).get();
		userForm.setRole(user.getRole().getName().toUpperCase());
		
//		Check if changed username already exists.
		if(userDAO.get(userForm.getUsername()).isPresent()) {
			if(userDAO.get(userForm.getUsername()).get().getUsername() != user.getUsername()) {
				model.addAttribute("errorMessage", "Username already exists.");
				return "account";
			}
		}
		
//		Check if changed email already exists.
		if(userDAO.findByEmail(userForm.getEmail()).isPresent()) {
			if(userDAO.findByEmail(userForm.getEmail()).get().getEmail() != user.getEmail()) {
				model.addAttribute("errorMessage", "There is already an account with the given email.");
				return "account";
			}
		}
		
//		Check if entered password is correct.
		if (encoder.matches(userForm.getPassword(), userDAO.get(username).get().getPassword())) {
			userDAO.update(userDAO.get(username).get(), params);
			model.addAttribute("successMessage", "Updating account was successful.");
			return "account";
		} else {
			model.addAttribute("errorMessage", "Please enter your correct password to verify changes.");
			return "account";
		}
	}
	
	
//	For testing purposes
	@GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Optional<User>> getUser(@PathVariable("id") long id) {
		if (userDAO.get(id).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Optional<User>>(userDAO.get(id), HttpStatus.OK);
		}
	}
}
