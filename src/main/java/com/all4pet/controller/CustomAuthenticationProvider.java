package com.all4pet.controller;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;



import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  UserDetailsService userDetailService;

//  @Autowired
//  private PasswordService passwordService;
  
 
  public void setUserDetailService(UserDetailsService userDetailService) {
      this.userDetailService = userDetailService;
  }

  
  @Override
  public Authentication authenticate(Authentication a) throws AuthenticationException {
	  	String loginUserName = a.getName();
	    String loginPassword = a.getCredentials().toString();
      try {
          UserDetails user = userDetailService.loadUserByUsername(loginUserName);
          UsernamePasswordAuthenticationToken result = null;
          String userName = user.getUsername();
          String password = user.getPassword();
          Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
          if(authorities.toString().equals("[admin]")){
              BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();    
              boolean checkEncodePassword =bCryptPasswordEncoder.matches(loginPassword, password);
        	 if( checkEncodePassword == true && userName.equals(loginUserName))
        	  result = new UsernamePasswordAuthenticationToken(userName,password, authorities);
          }
          else if (password.equals(loginPassword) && userName.equals(loginUserName)) {
              result = new UsernamePasswordAuthenticationToken(userName, password, authorities);          }
          return result;
      } catch (UsernameNotFoundException e) {
          throw e;
      }
  }

  @Override
  public boolean supports(Class<?> auth) {
    return auth.equals(UsernamePasswordAuthenticationToken.class);
  }
}