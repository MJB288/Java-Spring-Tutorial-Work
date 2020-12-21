package com.example.securingweb;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//Ensures that only authorized users may see the greeting message

@Configuration
@EnableWebSecurity //Needed to enable the web security with Spring MVC's support
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//This method defines which URL paths get secured
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests().antMatchers("/", "/home").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()//Link of the login page
			.loginPage("/login")
			.permitAll()
			.and()
		.logout()
			.permitAll();
	}
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user = 
				User.withDefaultPasswordEncoder()
					.username("user")
					.password("password")
					.roles("USER")
					.build();
		
		return new InMemoryUserDetailsManager(user);
	}
}
