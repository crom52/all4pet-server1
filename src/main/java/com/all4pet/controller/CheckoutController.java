package com.all4pet.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.all4pet.entity.BillEntity;
import com.all4pet.entity.CartEntity;
import com.all4pet.entity.CartItemEntity;
import com.all4pet.entity.UserEntity;
import com.all4pet.entity.UserProfileEntity;
import com.all4pet.mapper.BillMapper;
import com.all4pet.mapper.CartMapper;
import com.all4pet.mapper.ProductMapper;
import com.all4pet.mapper.UserMapper;
import com.all4pet.service.EmailService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

@RestController

public class CheckoutController {
	@Autowired
	CartMapper cartMapper;
	@Autowired
	ProductMapper productMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	BillMapper billMapper;
	@Autowired
	SecurityController securityController;
	@Autowired
	EmailService emailService;
//	@Autowired
//	PaypalController paypalController;

	@PostMapping("/checkout")
	@ResponseBody
	public BillEntity checkout(HttpServletRequest request, JsonNode json) {
//		HttpSession session = request.getSession();
		boolean checkLogin = SecurityController.isAuthenticanted();
		BillEntity bill = new BillEntity();
		if (checkLogin == true) {
			String userName = SecurityController.getPrincipal().getName();
			UserEntity user = userMapper.getUserByUserName(userName);
			CartEntity userCart = cartMapper.getCartByUserName(userName);
			List<CartItemEntity> listItems = cartMapper.getListCartItem(userCart.getId());
			UserProfileEntity profile = userMapper.getUserProfile(user.getId());
			System.out.println(listItems);
			bill.setUserEntity(user);
			float totalMoney = 0;
			for (CartItemEntity item : listItems) {
				float price = item.getProductEntity().getPrice();
				int promotion = item.getProductEntity().getPromotion();
				long quantity = item.getQuantity();
				totalMoney += quantity * price - (quantity * price * promotion / 100);
			}
			bill.setTotalMoney(totalMoney);
			bill.setListItems(listItems);
			bill.setAddress(profile.getAddress());
			bill.setPhonenumber(profile.getPhoneNumber());
			bill.setEmail(user.getEmail());
			bill.setBillDate((Date) Calendar.getInstance().getTime());
			bill.setReceiver(userName);
		}

//		else { 
//			//chua dang nhap
//			CartEntity sessionCart = (CartEntity) session.getAttribute("cart");
//			if (sessionCart == null) {
//				return new BillEntity();
//			} else {
//				List<CartItemEntity> listItems = sessionCart.getListCartItem(); 
//				float totalMoney = 0;
//				for (CartItemEntity item : listItems) {			
//					float price = item.getProductEntity().getPrice();
//					int promotion = item.getProductEntity().getPromotion();
//					long quantity = item.getQuantity();	
//					totalMoney +=  (quantity*price) - (quantity*price*promotion/100);
//				}			
//				bill.setTotalMoney(totalMoney);
//				bill.setListItems(listItems);
//				bill.setBillDate((Date)Calendar.getInstance().getTime());	
//			}
//			session.setAttribute("bill", bill);
//		}
		return bill;

	}

	@Transactional
	@PostMapping("/confirmCheckout")
	@ResponseBody
	public BillEntity confirmCheckout(HttpServletRequest request, HttpServletResponse response,
			@RequestBody JsonNode json) throws IOException {
		HttpSession session = request.getSession();
		String receiver = "";
		String email = "";
		String billCode = "";
		String paymentMethod = "";
		BillEntity bill = new BillEntity();
//		BillEntity bill = (BillEntity) session.getAttribute("bill");
		boolean checkLogin = SecurityController.isAuthenticanted();
//		if (bill == null) { 
//			return new BillEntity();
//		} 

		ObjectMapper mapper = new ObjectMapper();
		JsonNode list = json.get("listItems");
		List<CartItemEntity> listItems = new ArrayList<CartItemEntity>();
		for (JsonNode node : list) {
			CartItemEntity itemCart = mapper.convertValue(node, CartItemEntity.class);
			listItems.add(itemCart);
		}

		billCode = bill.toString().substring(bill.toString().indexOf("@") + 1);
		bill.setBillCode(billCode);
		bill.setListItems(listItems);
		bill.setReceiver(json.get("receiver").asText());
		bill.setTotalMoney(json.get("totalMoney").asLong());
		bill.setStatus(json.get("status").asInt());
		bill.setEmail(json.get("email").asText());
		bill.setAddress(json.get("address").asText());
		bill.setNote(json.get("note").asText());
		bill.setPhonenumber(json.get("phoneNumber").asText());
		bill.setPaymentMethod(json.get("paymentMethod").asText());

//		if (paymentMethod.equals("PAYPAL")) {
//			paypalController.payWithPaypal(request, bill.getTotalMoney());
//		}

		billMapper.saveBill(bill);
		saveBillItems(bill);
		emailService.sendMail(billCode, email, receiver);

		return bill;
	}

	public void removeCartAfterCheckoutSuccess() {
		boolean checkLogin = SecurityController.isAuthenticanted();
		if (checkLogin == true) {
			String userName = SecurityController.getPrincipal().getName();
			CartEntity userCart = cartMapper.getCartByUserName(userName);
			for (CartItemEntity item : cartMapper.getListCartItem(userCart.getId())) {
				cartMapper.removeProduct(item.getProductEntity().getId(), userCart.getId());
			}
		}
	}

	public void saveBillItems(BillEntity bill) {
		for (CartItemEntity item : bill.getListItems()) {
			int numOfPurchase = item.getQuantity();
			productMapper.updateNumOfPurchase(item.getProductEntity().getId(), numOfPurchase);
			String billCode = bill.toString().substring(bill.toString().indexOf("@") + 1);
			billMapper.saveBillItems(billCode, item.getProductEntity().getId());
		}
	}

}
