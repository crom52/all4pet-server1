package com.all4pet.controller;




import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.all4pet.mapper.BillMapper;
import com.all4pet.mapper.ProductMapper;
import com.all4pet.mapper.UserMapper;
import com.all4pet.entity.BillEntity;
import com.all4pet.entity.UserEntity;



@Controller
public class ViewController {
	@Autowired ProductMapper productMapper;
	@Autowired BillMapper billMapper;
	
	@RequestMapping("admin/orderList")
	public String getOrderList(Model model) {
		List<BillEntity> orderList = billMapper.getOrderList();
		model.addAttribute("orderList", orderList);
		return "listOrder";
	}
	
	@RequestMapping("admin/deleteOrder/{id}")
	public String deleteOrderById(@PathVariable(value = "id") long id) {
		billMapper.deleteOrderById(id);
		return "redirect:/admin/orderList";
	}


//
//	@RequestMapping(value =  "admin/userList" )
//	    public String personListView() {
//	        return "userList";
//		}
//	
//	@RequestMapping(value =  "shop-details" )
//    public String shopDetails() {
//        return "shop-details";
//	}
//	@RequestMapping(value =  "/home" )
//	    public String homePage() {
//	        return "home";
//	    }
//	@RequestMapping(value = "/admin") 
//	    public String adminPage() {
//	        return "admin";
//	    }
//
//	@RequestMapping(value = { "/register" })
//			public String registerView() {
//	       return "registrationForm";
//	   }


}