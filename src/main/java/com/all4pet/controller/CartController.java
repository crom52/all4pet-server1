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
		
//		else if (cartEntity != null) {// Chua dang nhap, ton tai cookie cart
//			cartService.updateCartItem(cartEntity, productId);
//			System.out.println("Chua dang nhap, co cookie");
//		} else {// Chua dang nhap, ko ton tai cookie cart
//			System.out.println("chua dang nhap, k co cookie");
//
//			// Random cart_code
//			cartCode = RandomCartCode();
//
//			// Tao cookie cho cart
//			Cookie cookie = new Cookie("cart_code", cartCode);
//			cookie.setMaxAge(60 * 60 * 24 * 365);
//			response.addCookie(cookie);
//			// Tao moi cart
//			cartService.saveCartWithCartCodeAndProductId(cartCode, productId);
//		}
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
//		else {
//			cart = cartService.getCartByCartCode(cartCode);
//		}
//		if (cart == null) {
//			return new CartEntity();
//		}
		return cart;
	}

//	@GetMapping(value = "cart/showCart")
//	@ResponseBody
//	public CartEntity showCart(HttpServletRequest request, HttpServletResponse response,
//			@CookieValue(value = "cart_ck", required = false) String cartCode) {
//		System.out.println("--------------cart/showcat ---------------------------------");
//		HttpSession session = request.getSession();
//		boolean checkLogin = SecurityController.isAuthenticanted();
//		String userName = SecurityController.getPrincipal().getName();
//		CartEntity sessionCart = (CartEntity) session.getAttribute("cart");
//		if (sessionCart == null) {// lan dau vao trang
//			System.out.println("show cart : lan dau vao trang");
//			sessionCart = new CartEntity();
//			session.setAttribute("cart", sessionCart);
//			return sessionCart;
//		} else {
//			System.out.println("sessionCart != null");
//			if (checkLogin == true) {
//				System.out.println("check login == true");
//				CartEntity userCart = cartMapper.getCartByUserName(userName);
//				UserEntity user = userMapper.getUserByUserName(userName);
//				List<CartItemEntity> userListItems = cartMapper.getListCartItem(userCart.getId());
//				List<CartItemEntity> sessionListItems = sessionCart.getListCartItem();
//				System.out.println("userListitem" + userListItems);
//				System.out.println("session item " + sessionListItems);
//				if (sessionListItems != null) {
//					for (CartItemEntity item : sessionListItems) {
//						item.setCartId(userCart.getId());
//						cartMapper.saveCartItemWithUserId(item);
//					}
//					userListItems.addAll(sessionListItems);
////						userListItems = removeExistsProduct(userListItems);
//				}
//				userCart.setListCartItem(userListItems);
//				userCart.setUserEntity(user);
//				return userCart;
//			} else {
//				System.out.println("check login == false");
//				sessionCart = (CartEntity) session.getAttribute("cart");
//				System.out.println(sessionCart.getListCartItem());
//				return sessionCart;
//			}
//		}
//	}

// @GetMapping(value = "cart/add")
// @ResponseBody public CartEntity addProductToCart(HttpServletRequest request, HttpServletResponse response, 
//    		@RequestParam("product") long productId, @CookieValue(value = "cart_ck", required = false) String cartCookie) {	
//	 System.out.println("--------------cart/add cart -----------------------------");
//	 System.out.println("cart cookie init " + cartCookie);
//	 
//	 ProductEntity product = productMapper.getProductById(productId);		 
//	 if (product == null) {
//		 return null;			
//	 } else {
//		 HttpSession session = request.getSession();
////		 Cookie cookie = new Cookie("cart_ck", cartCookie);
//	     CartEntity sessionCart = (CartEntity) session.getAttribute("cart");
//	     System.out.println("session " + session.getId()); 
//	     boolean checkLogin = SecurityController.isAuthenticanted();
//	     UserEntity user = new UserEntity();	  
//	     CartEntity userCart = new CartEntity();
//	     System.out.println("session cart = " +sessionCart);
//	     
////	     if (sessionCart == null) {
////	    	sessionCart =  new CartEntity();    
////	    	return sessionCart;
////	     }
//	     
////		 if (checkLogin == true) {
////			 System.out.println("-----add cart checklogin == true-----");
////			 String userName = SecurityController.getPrincipal().getName();
////			 user = userMapper.getUserByUserName(userName);
////    		 userCart = cartMapper.getCartByUserName(userName);	
////			 List<CartItemEntity> sessionListItems = sessionCart.getListCartItem();
////			 if (sessionListItems == null) {
////				 //sessionlistItem == null
////				 System.out.println("------chua tung them san pham truoc khi dang nhap-----");
////				 List<CartItemEntity> userListItems = cartMapper.getListCartItem(userCart.getId());				 
////				 System.out.println("user list item" + userListItems); 
////				 boolean existsItem =false;
////				 for (CartItemEntity item : userListItems) {
////					 if (item.getProductEntity().getId() == product.getId()) {								 
////						 System.out.println("da co san pham nay");
////						 item.setQuantity(item.getQuantity() + 1);	
////						 item.setProductEntity(product);
////						 existsItem = true;
////						 cartMapper.updateCartItem(item);						 
//////						 userListItems = removeExistsProduct(userListItems);
////						 userCart.setListCartItem(userListItems);
////					 }
////				 }
////				 if (existsItem == false) {
////						System.out.println("chua co san pham nay");
////						CartItemEntity newItem = new CartItemEntity();
////			 			newItem.setQuantity(1);
////			 			newItem.setProductEntity(product);
////			 			newItem.setCartId(userCart.getId());
////			 			userListItems.add(newItem);  
////			 			cartMapper.saveCartItemWithUserId(newItem);
////						System.out.println("listItems " + userListItems);	
////						userCart.setListCartItem(userListItems);
////				 }					
////				 userCart.setUserEntity(user);	
////				 session.setAttribute("cart", userCart);
////				 
////				 response.addCookie(cookie);
////				 
////			 } else { 
////				 // truong hop them san pham truoc khi dang nhap
////				 // sessionlistitem != null
////				 System.out.println("-------co san pham truoc khi dang nhap------------");
////				 boolean existsItem =false;
////				 for (CartItemEntity item : sessionListItems) {
////					 if (item.getProductEntity().getId() == product.getId()) {								 
////						 System.out.println("da co san pham nay");
////						 item.setQuantity(item.getQuantity() + 1);	
////						 item.setProductEntity(product);
////						 existsItem = true;
////						 cartMapper.updateCartItem(item);
////					 }
////				 }
////				 if (existsItem == false) {
////						System.out.println("chua co san pham nay");
////						CartItemEntity newItem = new CartItemEntity();
////			 			newItem.setQuantity(1);
////			 			newItem.setProductEntity(product);
////			 			sessionListItems.add(newItem);  
////			 			sessionCart.setListCartItem(sessionListItems);
////						System.out.println("listItems " + sessionListItems);	
////				 }	
//////				 sessionListItems = removeExistsProduct(sessionListItems);
////			 }
////			 
////			 userCart.setListCartItem(sessionListItems);
////			 userCart.setUserEntity(user);	
////			 session.setAttribute("cart", sessionCart);
////			 cookie = new Cookie("cart_ck", cartCookie);
////			 response.addCookie(cookie);
////		 } 
//		  if (checkLogin == false){
//			 System.out.println("-------check login == false---------");
////			 List<CartItemEntity> sessionListItems = sessionCart.getListCartItem();
//			 boolean existsItem = false;
////			 if (sessionCart.getListCartItem() != null) {
////				 System.out.println("sessionCart.getListCartItem() != null");
////				 
////				 //check exist or not
////				 for (CartItemEntity item : sessionListItems) {
////					 if (item.getProductEntity().getId() == product.getId()) {								 
////						 System.out.println("da co san pham nay");
////						 item.setQuantity(item.getQuantity() +1);	
////						 item.setProductEntity(product);
////						 existsItem = true;
////						 session.setAttribute("cart", sessionCart);
//////						 cookie = new Cookie("cart_ck", cartCookie);
//////						 response.addCookie(cookie);
////					 }
////				 }
////				 if (existsItem == false) {
////						System.out.println("chua co san pham nay");
////						CartItemEntity newItem = new CartItemEntity();
////			 			newItem.setQuantity(1);
////			 			newItem.setProductEntity(product);
////			 			sessionListItems.add(newItem);  
////			 			sessionCart.setListCartItem(sessionListItems);
////			 			session.setAttribute("cart", sessionCart);
//////			 			cookie = new Cookie("cart_ck", cartCookie);
//////						response.addCookie(cookie);
//////						System.out.println(cookie);
////						System.out.println("listItems " + sessionListItems);	
////						
////				 }					 						 
////			 } 
//			 if (sessionCart == null) {
//				 sessionCart = new CartEntity();
//				 System.out.println("sessionCart.getListCartItem() == null");
//				 CartItemEntity item = new CartItemEntity();
//				 List<CartItemEntity> newListItems = new ArrayList<CartItemEntity>();
//				 item.setQuantity(1);
//				 item.setProductEntity(product);
//				 newListItems.add(item);
//				 
//				 sessionCart.setListCartItem(newListItems);
//				 session.setAttribute("cart", sessionCart);
//				 return sessionCart;
////				 cookie = new Cookie("cart_ck", cartCookie);
////					response.addCookie(cookie);
////					System.out.println(cookie);
//			 }
//		 }
//	     return sessionCart;
//	 }
////	return "da them thanh cong" ;
//}

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

	public List<CartItemEntity> removeExistsProduct(List<CartItemEntity> listItems) {
		List<CartItemEntity> tmpList = new ArrayList<CartItemEntity>(listItems);
		System.out.println("temp list" + tmpList);
		for (int i = 0; i < listItems.size() - 1; i++) {
			for (int j = i + 1; j < tmpList.size() - 1; j++) {
				if (tmpList.get(i).getProductEntity().getId() == tmpList.get(j).getProductEntity().getId()) {
					tmpList.remove(i);
					System.out.println("da remove " + tmpList.get(i).getProductEntity().getId());
				}
			}
			System.out.println("list item" + listItems);
			System.out.println("list temp " + tmpList);
		}
		return tmpList;
	}

	public static String RandomCartCode() {
		return UUID.randomUUID().toString();
	}

}