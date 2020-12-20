package de.frauas.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/").hasAnyAuthority("user", "admin")
		.antMatchers(HttpMethod.GET, "/books").hasAnyAuthority("user", "admin")
		.antMatchers(HttpMethod.GET, "/books/**").hasAnyAuthority("user", "admin")
		.antMatchers(HttpMethod.DELETE, "/books/**").hasAnyAuthority("admin")
		.antMatchers(HttpMethod.POST, "/books/**").hasAnyAuthority("admin")
		.antMatchers(HttpMethod.PUT, "/books/**").hasAnyAuthority("admin")
		.antMatchers(HttpMethod.PATCH, "/books/**").hasAnyAuthority("admin")
		.anyRequest().authenticated()
		.and()
		.httpBasic()
		.and()
		.formLogin()
		// .loginPage("/login").permitAll() for custom login page
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/403");
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
	}

}
