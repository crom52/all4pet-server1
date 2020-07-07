package com.all4pet.controller;

import com.all4pet.entity.CartEntity;
import com.all4pet.mapper.CartMapper;
import com.all4pet.mapper.ProductMapper;
//import com.all4pet.ultil.SecurityUtils;
import com.all4pet.mapper.UserMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.all4pet.entity.CartItemEntity;
import com.all4pet.entity.ProductEntity;
import com.all4pet.entity.UserEntity;
import com.all4pet.service.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController

public class CartController {
	@Autowired
	CartMapper cartMapper;
	@Autowired
	ProductMapper productMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	SecurityController securityController;
	@Autowired
	CartService cartService;

	@PostMapping(value = "cart/add")
	public String addProduct(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("product") long productId,
			@CookieValue(value = "cart_code", required = false) String cartCode) {
		boolean checkLogin = SecurityController.isAuthenticanted();
		CartEntity cartEntity = cartMapper.getCartByCartCode(cartCode);

		if (checkLogin == true) {// Da dang nhap
			String username = SecurityController.getPrincipal().getName();
			cartService.updateOrCreateCartWithUsername(username, productId);
			System.out.println("Da dang nhap");
		} 
		return "da them thanh cong" ;
		
	}

	@GetMapping(value = "cart/showCart")
	public CartEntity showCart(@RequestParam(value = "cart_code", required = false) String cartCode) {
		CartEntity cart = new CartEntity();
		boolean checkLogin = SecurityController.isAuthenticanted();
		if (checkLogin == true) {// Da dang nhap
			String userName = SecurityController.getPrincipal().getName();
			cart = cartService.getCartByUserName(userName);
		} 
		return cart;
	}


	@RequestMapping(value = "cart/remove")
	@ResponseBody
	public String removeProduct(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("product") long productId,
			@CookieValue(value = "cart_ck", required = false) String cartCode) {
		System.out.println("------------remove product-------------");
		HttpSession session = request.getSession();
		CartEntity sessionCart = (CartEntity) session.getAttribute("cart");
		List<CartItemEntity> sessionListItems = sessionCart.getListCartItem();
		List<CartItemEntity> listItems = new ArrayList<CartItemEntity>();
		List<CartItemEntity> deleteList = new ArrayList<CartItemEntity>();
		if (sessionListItems != null) {
			listItems = sessionListItems;
		}
		boolean checkLogin = SecurityController.isAuthenticanted();
		if (checkLogin == true) { // da dang nhap
			System.out.println("remove product : da dang nhap");
			String userName = SecurityController.getPrincipal().getName();
			List<CartItemEntity> userListItems = cartMapper
					.getListCartItem(cartMapper.getCartByUserName(userName).getId());
			listItems = userListItems;
			sessionCart.setListCartItem(listItems);
			System.out.println("list item " + userListItems);
			cartMapper.removeProduct(productId, cartMapper.getCartByUserName(userName).getId());
		}
		for (int i = 0; i < listItems.size(); i++) {
			if (listItems.get(i).getProductEntity().getId() == productId) {
				System.out.println("tim thay san pham can` xoa");
				deleteList.add(listItems.get(i));
			}
			listItems.removeAll(deleteList);
			sessionCart.setListCartItem(null);
		}
		return "done";

	}



	public static String RandomCartCode() {
		return UUID.randomUUID().toString();
	}

}