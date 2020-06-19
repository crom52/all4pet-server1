package com.all4pet.controller;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.all4pet.entity.*;

//PHUC
import com.fasterxml.jackson.databind.JsonNode;
import com.all4pet.mapper.UserMapper;
//import com.all4pet.mapper.ProductMapper;


@RestController
public class UserProfileController {
	@Autowired UserMapper userMapper;
	@Autowired SecurityController securityController;
		
	@GetMapping( "/user/showProfile" )
    public @ResponseBody UserProfileEntity getUserProfile() {
		boolean checkLogin = SecurityController.isAuthenticanted();
		if (checkLogin == true) {
			String userName = SecurityController.getPrincipal().getName();
			UserEntity user = userMapper.getUserByUserName(userName);
			UserProfileEntity userProfile = userMapper.getUserProfile(user.getId());			
			userProfile.setUserEntity(user);
			return userProfile;
		}
		return new UserProfileEntity(null,null);
	}
	
	@Transactional
	@PostMapping ("/user/editProfile")
	public @ResponseBody String editUserProfile (@RequestBody JsonNode json) {
		boolean checkLogin = SecurityController.isAuthenticanted();	
		if (checkLogin == true ) {
			String userName = SecurityController.getPrincipal().getName();
			long userId = userMapper.getUserByUserName(userName).getId();
			String name = json.get("name").asText();
			String address = json.get("address").asText();
			String phoneNumber = json.get("phoneNumber").asText();	
			long birthdayTypeMiliSecond = json.get("birthday").asLong();
			Date birthdayTypeDate = new Date(birthdayTypeMiliSecond);
			birthdayTypeDate.toLocalDate();
			userMapper.updateUserProfileByUserId(userId, name, address, phoneNumber, birthdayTypeDate);
			return "edit successfully";
		}		
		return "not edit";	
	}
	
	@Transactional
	@PostMapping("/user/changePassword")
	public @ResponseBody String changeUserPassword (@RequestBody JsonNode json) {
		boolean checkLogin = SecurityController.isAuthenticanted();
		System.out.println("here0");
		if (checkLogin == true)
		{
			String userName =  SecurityController.getPrincipal().getName();
			UserEntity user = userMapper.getUserByUserName(userName);
			String currenPassword = json.get("currentPassword").asText();
			String newPassword = json.get("newPassword").asText();
			String confirmNewPassword = json.get("confirmNewPassword").asText();
			if (user.getPassword().equals(currenPassword) && newPassword.equals(confirmNewPassword)) {
				userMapper.changePasswordByUserId(user.getId(), newPassword);
				return "da doi password";				
			} else return "password cu ko chinh xac hoac password moi khong khop";
		}

		return "chua dang nhap";		
	}
	


}