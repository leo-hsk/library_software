package de.frauas.library.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * This is a helper class to manually encode a password.
 * @author Leonard
 *
 */
public class PassGen {
	public static void main(String[] args ) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
	}
}

// $2a$10$hrNcOqMuGPphUyQedgdut.kzkQZSkvay1tEnhuXuO.kJKP30hnZ1u