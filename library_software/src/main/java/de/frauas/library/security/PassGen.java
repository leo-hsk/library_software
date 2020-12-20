package de.frauas.library.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassGen {
	public static void main(String[] args ) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
	}
}

// $2a$10$hrNcOqMuGPphUyQedgdut.kzkQZSkvay1tEnhuXuO.kJKP30hnZ1u