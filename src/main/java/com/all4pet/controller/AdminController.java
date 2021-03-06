
package com.all4pet.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.all4pet.config.VNCharacterUtils;
import com.all4pet.entity.*;

//PHUC
import com.fasterxml.jackson.databind.JsonNode;
import com.all4pet.mapper.BillMapper;
import com.all4pet.mapper.CartMapper;
import com.all4pet.mapper.ProductMapper;
import com.all4pet.mapper.UserMapper;

@RestController
public class AdminController {
	@Autowired
	UserMapper userMapper;
	@Autowired
	ProductMapper productMapper;
	@Autowired
	CartMapper cartMapper;
	@Autowired
	BillMapper billMapper;
	@Autowired
	UserDetailsServiceImp userDetailsServiceImp;

//-------User-------		
	@GetMapping("admin/getUserList")
	public @ResponseBody List<UserEntity> getUserList(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber) {
		int offset = 0;
		if (pageNumber <= 0) {
			pageNumber = 1;
		}
		if (pageNumber >= 1) {
			offset = pageNumber * 10 - 10;
		}
		List<UserEntity> userList = userMapper.paging10User(offset);
		return userList;
	}

	@Transactional
	@PostMapping("admin/saveEditUser")
	public @ResponseBody String editUser(@RequestBody JsonNode json) {
		try {
			String userName = json.get("userName").asText();
			String password = json.get("password").asText();
			String email = json.get("email").asText();
			int active = json.get("active").asInt();
			String roleName = json.get("roleName").asText().toLowerCase();
			RoleUserEntity role = new RoleUserEntity();
			if (roleName.equals("admin")) {
				role = userMapper.getRole(1);
				password = SecurityController.passwordEncoder(password);
			} else {
				role = userMapper.getRole(2);
			}
			UserEntity user = new UserEntity(userName, password, email, active, role);
			userMapper.updateUserByUserName(user);
			return "success";
		} catch (Exception e) {
			return "failed";
		}
		
	}

	@Transactional
	@PostMapping("admin/deleteUser")
	public @ResponseBody String deleteUser(@RequestParam("id") long id) {
		try {
			userMapper.deleteUserCart(id);
			userMapper.deleteUserProfile(id);
			userMapper.deleteUser(id);
			return "success";
		} catch (Exception e) {
			return "failed";
		}
		
	}

	@Transactional
	@PostMapping("admin/addUserByAdmin")
	public @ResponseBody String addUser(@RequestBody JsonNode json) {
		String userName = json.get("userName").asText();
		String password = json.get("password").asText();
		String email = json.get("email").asText();
		int active = json.get("active").asInt();
		String roleName = json.get("roleName").asText().toLowerCase();
		RoleUserEntity role = new RoleUserEntity();
		if (roleName.equals("admin")) {
			role = userMapper.getRole(1);
			password = SecurityController.passwordEncoder(password);
		} else {
			role = userMapper.getRole(2);
		}
		// roleid = 2 is customer
		UserEntity user = new UserEntity(userName, password, email, active, role);
		if (userMapper.getUserByUserNameOrEmail(userName, email) != null) {
			return "username or email is existed";
		} else {
			userMapper.insertUser(user);
			Date createdDate = Calendar.getInstance().getTime();
			UserProfileEntity userProfile = new UserProfileEntity(createdDate, user);
			userMapper.insertUserProfile(userProfile);
			cartMapper.saveCart(userName);
			return "success";
		}
	}

	@Transactional
	@PostMapping("/register")
	public @ResponseBody String register(@RequestBody JsonNode json) {
		try {
			String userName = json.get("userName").asText();
			String password = json.get("password").asText();
			String email = json.get("email").asText();
			int active = 1;
			RoleUserEntity role = userMapper.getRole(2); // roleid = 2 is customer
			UserEntity user = new UserEntity(userName, password, email, active, role);
			if (userMapper.getUserByUserNameOrEmail(userName, email) != null) {
				return "User name is existed";
			} else {
				userMapper.insertUser(user);
				Date createdDate = Calendar.getInstance().getTime();
				UserProfileEntity userProfile = new UserProfileEntity(createdDate, user);
				userMapper.insertUserProfile(userProfile);
				cartMapper.saveCart(userName);
				return "success";
			}
			
		} catch (Exception e) {
			return "failed";
		}
		
	}

//-------ProductEntity--------

//	@Transactional
//	@GetMapping("/admin/getProductList")
//	public @ResponseBody List<ProductEntity> getProductList(
//			@RequestParam(value = "page", defaultValue = "1") int pageNumber) {
//		int offset = 0;
//		if (pageNumber <= 0) {
//			pageNumber = 1;
//		}
//		if (pageNumber >= 1) {
//			offset = pageNumber * 10 - 10;
//		}
//		List<ProductEntity> productList = productMapper.paging10Products(offset);
//		return productList;
//	}

	@Transactional
	@GetMapping("admin/deleteProduct/{id}")
	public @ResponseBody void deleteProduct(@PathVariable("id") long id) {
		productMapper.deleteProductById(id);
	}

	@Transactional
	@PostMapping("admin/updateProduct/{id}")
	public @ResponseBody void editProduct(@PathVariable("id") long id, @RequestBody JsonNode json) {
		String name = json.get(1).asText();
		int amount = json.get(2).asInt();
//		String url = json.get().asText();
		float price = json.get(3).asLong();
		int promotion = json.get(4).asInt();
		String type = json.get(5).asText();
//		String url2 = json.get("url2").asText();
//		String url3 = json.get("url3").asText();
		String brand = json.get(6).asText();
		int numOfPurchase = json.get(7).asInt();
		String url = json.get(9).asText();
		if (!url.equals("")){
			ProductEntity product = new ProductEntity(amount, price, promotion, type,  brand,
					 numOfPurchase, url);
			productMapper.updateProductById(id, product);
		} else {
			ProductEntity product = new ProductEntity( amount, price, promotion, type,  brand,
					 numOfPurchase,"");
			productMapper.updateProductById(id, product);
			
		}
		
		
	}

//	@Transactional
//	@PostMapping("admin/addProductByAdmin")
//	public @ResponseBody String addProduct(@RequestBody JsonNode json) {
//		String name = json.get("name").asText();
//		int amount = json.get("amount").asInt();
//		String url = json.get("url").asText();
//		float price = json.get("price").asLong();
//		int promotion = json.get("promotion").asInt();
//		String type = json.get("type").asText();
//		String url2 = json.get("url2").asText();
//		String url3 = json.get("url3").asText();
//		String brand = json.get("brand").asText();
//		String description = json.get("description").asText();
//		String categoryName = json.get("categoryName").asText();
//		String color = json.get("color").asText();
//		String size = json.get("size").asText();
//		CategoryEntity category = productMapper.getCategoryByName(categoryName);
//		ProductEntity product = new ProductEntity(name, amount, url, price, promotion, type, url2, url3, brand,
//				description, 0, category, size, color);
//		name = VNCharacterUtils.removeAccent(name);
//		name = "%"+name+"%";
//		if (productMapper.getProductByName(name) != null) {
//			return "san pham nay da ton tai";
//		} else {
//			productMapper.insertProduct(product);
//			return "add product thanh cong";
//		}
//	}
	
	//----ORDER
	@Transactional
	@PostMapping("/admin/updateOrder/{id}")
	@ResponseBody
	public void updateOrder(@PathVariable("id") long id, @RequestBody JsonNode json) {
		String billCode = json.get(1).asText();
		String receiver = json.get(2).asText();
		String address = json.get(3).asText();
		String phonenumber = json.get(4).asText();
		String productName = json.get(5).asText();
		int status = json.get(6).asInt();
		String paymentMethod = json.get(7).asText();
		float totalMoney = json.get(8).asLong();
		BillEntity bill = billMapper.getBillById(id);
		if (bill == null) {
			// chua co bill nay. dung cho btnAdd Order
			bill = new BillEntity();
			bill.setBillCode(billCode);
			bill.setReceiver(receiver);
			bill.setAddress(address);
			bill.setPaymentMethod(paymentMethod);
			bill.setStatus(status);
			bill.setPhonenumber(phonenumber);
			bill.setTotalMoney(totalMoney);
			
			productName = VNCharacterUtils.removeAccent(productName);
			productName = "%"+productName+"%";
			ProductEntity product = productMapper.getProductByName(productName);
			if (product != null) {
				billMapper.saveBill(bill);
				billMapper.saveBillItems(billCode, product.getId());
			}
		} else {
			// da co bill nay. dung cho btnUpdate Order
			billMapper.updateOrder(id, receiver, address, phonenumber, status, paymentMethod, totalMoney);
		}

	}
	
	@GetMapping("admin/deleteOrder/{id}")
	public void deleteOrderById(@PathVariable(value = "id") long id) {
		billMapper.deleteOrderById(id);
	}


}
