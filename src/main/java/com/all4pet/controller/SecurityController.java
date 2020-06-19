package com.all4pet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.all4pet.mapper.CartMapper;
import com.all4pet.mapper.UserMapper;
@Service
public class SecurityController   {
	@Autowired UserMapper userMapper;
	@Autowired CartMapper cartMapper;
	
     AuthenticationManager authenticationManager;

     UserDetailsService userDetailsService;

	public static Authentication getPrincipal() {
		Authentication  myUser = SecurityContextHolder.getContext().getAuthentication();
		return myUser;
	}


	public static String passwordEncoder(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}
	
	public static String replaceNull(String string){
	    if (string == null) {
			return "";
		}
	    return string;
	}

	
	 public static boolean isAuthenticanted() {
			if (SecurityContextHolder.getContext().getAuthentication() != null
					&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
					!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
				return true;
			}
			return false;
		}
	
}
