package com.nk.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nk.service.CustomerService;

import lombok.SneakyThrows;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	
	@Autowired
	private CustomerService customerService;
	
	@Bean
	public BCryptPasswordEncoder pwdEncode()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider()
	{
		DaoAuthenticationProvider authProvider = new 
				DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(pwdEncode());
		authProvider.setUserDetailsService(customerService);
		
		return authProvider;
	}
	
	@Bean
	@SneakyThrows
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
		
	}
	@Bean
	public SecurityFilterChain security(HttpSecurity http) throws Exception
	{
		//logic
		http.authorizeHttpRequests((req) ->
		req.requestMatchers("/register","/login")
		.permitAll()
		.anyRequest()
		.authenticated());
		return http.csrf().disable().build();
	}

}
