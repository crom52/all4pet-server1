package com.all4pet.controller;

import java.util.ArrayList;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.all4pet.entity.UserEntity;
import com.all4pet.mapper.UserMapper;

import io.jsonwebtoken.Claims;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImp implements UserDetailsService {
	 User user;
	@Autowired
	private UserMapper userMapper;
	
	
       
   
	@Override
	public UserDetails loadUserByUsername(String loginUserName) {
        UserEntity user = userMapper.getUserByUserName(loginUserName);
        if (user == null) throw new UsernameNotFoundException(loginUserName);
        List<GrantedAuthority> authorities = new ArrayList<>();
        String userName = user.getUserName();
        String password = user.getPassword();
        String roleName =  "ROLE_"+user.getRole().getRoleName().toUpperCase();
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(roleName);
        authorities.add(role);        
		return new User (userName, password,
        		true, true, true, true, authorities);
    }




	
	
	 
	}