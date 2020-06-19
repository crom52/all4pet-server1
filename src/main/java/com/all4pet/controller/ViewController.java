package com.all4pet.controller;




import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import com.all4pet.mapper.UserMapper;
import com.all4pet.entity.UserEntity;



@Controller
public class ViewController {
	@Autowired UserMapper personMapper;

//    @RequestMapping(value = "/login" )
//	    public String welcomeView(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws LoginException {
//    		boolean auth = SecurityController.isAuthenticanted();
//    		if (auth == true) {
//    			
//    		    return "redirect:home";
//            }
//    		else {
//    		UserEntity userEntity = new UserEntity();
//        	model.addAttribute("loginUser", userEntity);
//    	    return "login";
//    		}
//    		
//    	} 
    	
//    @RequestMapping(value = "/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//            return "redirect:/home";
//        } else return "redirect:/login"; 
//    }
    @RequestMapping(value = "/")
    public String index() {
        return "shop-details";
    }

	@RequestMapping(value =  "admin/userList" )
	    public String personListView() {
	        return "userList";
		}
	
	@RequestMapping(value =  "shop-details" )
    public String shopDetails() {
        return "shop-details";
	}
	@RequestMapping(value =  "/home" )
	    public String homePage() {
	        return "home";
	    }
	@RequestMapping(value = "/admin") 
	    public String adminPage() {
	        return "admin";
	    }

	@RequestMapping(value = { "/register" })
			public String registerView() {
	       return "registrationForm";
	   }


}