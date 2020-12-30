package de.frauas.library.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Class containing stateless methods.
 * @author Leonard
 *
 */
public class UserManagerUtils {
	
	public static String getCurrentUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		return username;
	}

}
