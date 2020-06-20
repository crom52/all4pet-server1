package com.all4pet.controller;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.all4pet.config.JWTAuthenticationFilter;
import com.all4pet.config.JWTTokenProvider;
import com.all4pet.entity.*;
import com.all4pet.mapper.BillMapper;
//import com.jwatgroupb.repository.ProductRepository;
//import com.jwatgroupb.repository.RoleUserRepository;
//import com.jwatgroupb.repository.UserRepository;
//import com.all4pet.service.AdminService;
//PHUC
import com.all4pet.mapper.ProductMapper;
import com.all4pet.mapper.UserMapper;
import com.fasterxml.jackson.databind.JsonNode;
//import com.all4pet.mapper.ProductMapper;


@RestController
public class WebController {
	@Autowired ProductMapper productMapper;
	@Autowired UserMapper userMapper;
	@Autowired SecurityController securityController;
	@Autowired AuthenticationManager authenticationManager;
	@Autowired JWTAuthenticationFilter jwtAuthenticationFilter;
	@Autowired BillMapper billMapper;



      @Autowired
      private JWTTokenProvider tokenProvider;
      @Autowired
      private UserDetailsServiceImp userDetailsServiceImp;

      @PostMapping("/login")
      public List<String> authenticateUser(@Valid @RequestBody JsonNode json) {
    	  List<String> jwt = new ArrayList<String>();
    	  jwt.add(null);
    	  jwt.add("Wrong user name or password");
    	  
    	  try {
    		  String userName = json.get("userName").asText();
    		  String password = json.get("password").asText();
    	      UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(userName);
    	      
    	      String role = userDetails.getAuthorities().toString();
    	      role = role.substring(6,role.toString().length() - 1 );
    	      if (userDetails != null && password.contentEquals(userDetails.getPassword()))  {
    	    	  jwt.clear();
    	    	  jwt.add(role);
    	    	  jwt.add(tokenProvider.generateToken(userName));
    	    	  return jwt;
    		}	
		} catch (Exception e) {
			return jwt;
		}
    	  return jwt;
      }
      
	@GetMapping( "/getCategoryList" )
    public @ResponseBody List<CategoryEntity> getCategoryList() {
		List<CategoryEntity> categoryList = productMapper.getAllCategoryName();
		return categoryList;
	}
	
    
	@GetMapping( "/search" )
    public @ResponseBody List<ProductEntity> searchProduct(@RequestParam("key") String key) {
		key = key.replaceAll("-", " ");
		key = "'%"+key+"%'";
		System.out.println(key);
		List<ProductEntity> listProduct = productMapper.getProductBySearchKey(key);
		return listProduct;
	}
	
	
	@GetMapping( "/product" )
    public @ResponseBody List<ProductEntity> getProductByCategory(@RequestParam("category") String categoryName) {
		List<ProductEntity> listProduct = productMapper.getProductByCategoryName(categoryName);
		return listProduct;
	}
	
	@GetMapping("/topProduct") 
	 public @ResponseBody List<ProductEntity> getTopProduct() {
		List<ProductEntity> listProduct = productMapper.getTopProduct();
		return listProduct;
	}
	

	@GetMapping( "/product/{category}" )
    public @ResponseBody List<ProductEntity> getProductList(@PathVariable ("category") String category,
    						@RequestParam ("type") String type, @RequestParam(value = "page" , defaultValue = "1") int pageNumber) { 
		int offset = 0;
		if (pageNumber >= 1) {
			 offset = pageNumber *10 - 10;
		} 
		List<ProductEntity> productList = productMapper.get10ProductByCategoryAndType(offset,category,type);
		return productList;
	}
	@GetMapping("/" )
	 public @ResponseBody List<ProductEntity> getProductList() { 		
			List<ProductEntity> productList = productMapper.getAllProduct();
			return productList;
		}	
	
	@GetMapping("/productDetail")
	 public @ResponseBody ProductEntity productDetail(@RequestParam("id") long productId) {
		ProductEntity product = productMapper.getProductById(productId);		
		return product; 		
		}
	
	@PostMapping("/showListOrder")
	 public @ResponseBody List<BillEntity> showOrder () {
		List<BillEntity> bill = new ArrayList<BillEntity>();
		boolean checkLogin = SecurityController.isAuthenticanted();
		if (checkLogin == true) {// Da dang nhap
			String userName = SecurityController.getPrincipal().getName();
			bill = billMapper.getListBillByUserName(userName);
		} 
		
		return  bill; 		
		//	TODO code billmapper.xml
		}
}