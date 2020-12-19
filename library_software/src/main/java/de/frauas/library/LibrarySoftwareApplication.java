package de.frauas.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // Deactivating Security for development
public class LibrarySoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarySoftwareApplication.class, args);
	}

}
