package com.example.comeonBusan.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.comeonBusan.jwt.JWTFilter;
import com.example.comeonBusan.jwt.JWTUtil;
import com.example.comeonBusan.jwt.LoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticaitonConfiguration;
	
	private final JWTUtil jwtUtil;
	
	public SecurityConfig(AuthenticationConfiguration authenticaitonConfiguration, JWTUtil jwtUtil) {
		this.authenticaitonConfiguration = authenticaitonConfiguration;
		this.jwtUtil = jwtUtil;
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception	{
		
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		http.csrf((auth) -> auth.disable());
		http.formLogin((auth) -> auth.disable());
		http.httpBasic((auth) -> auth.disable());
		http.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/", "/join", "/login").permitAll()
				.requestMatchers("/admin").hasRole("ADMIN")
				.anyRequest().permitAll());
		
		http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
		http.addFilterAt(new LoginFilter(authenticationManager(authenticaitonConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
		
		
		
		http.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return http.build();
		
	}
	
}