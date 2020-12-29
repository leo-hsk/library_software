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
		
		http
		.authorizeRequests()
		.antMatchers("/", "/index", "resources/**", "/register").permitAll()
		.and()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/search/**").hasAnyAuthority("user", "admin")
		.antMatchers(HttpMethod.PATCH, "/search/**").hasAnyAuthority("user", "admin")
		.antMatchers(HttpMethod.DELETE, "/search/**").hasAnyAuthority("admin")
		.antMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("admin")
		.antMatchers(HttpMethod.DELETE, "/users/**").hasAnyAuthority("admin")
		.antMatchers(HttpMethod.GET, "/addBook").hasAnyAuthority("admin")
		.antMatchers(HttpMethod.POST, "/addBook").hasAnyAuthority("admin")
		.antMatchers(HttpMethod.GET, "/myBooks").hasAnyAuthority("user", "admin")
		.antMatchers(HttpMethod.PUT, "/account").hasAnyAuthority("user", "admin")
		.antMatchers(HttpMethod.GET, "/account").hasAnyAuthority("user", "admin")
		.antMatchers(HttpMethod.GET, "/login").permitAll()
		.and()
		.httpBasic()
		.and()
		.formLogin()
		.loginPage("/login").permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/403");
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
	}

}
