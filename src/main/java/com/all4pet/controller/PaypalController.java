//package com.all4pet.controller;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.all4pet.paypal.PaypalPaymentIntent;
//import com.all4pet.paypal.PaypalPaymentMethod;
//import com.all4pet.paypal.Utils;
//import com.all4pet.service.PaypalService;
//import com.paypal.api.payments.Links;
//import com.paypal.api.payments.Payment;
//import com.paypal.base.rest.PayPalRESTException;
//
//
//@Controller
//public class PaypalController {
//	
//	public static final String URL_PAYPAL_SUCCESS = "pay/success";
//	public static final String URL_PAYPAL_CANCEL = "pay/cancel";
//	
//	private Logger log = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	private PaypalService paypalService;
//	
//	@Autowired CheckoutController checkoutController;
//
//	
//	@PostMapping("/payWithPaypal")
//	public String payWithPaypal(HttpServletRequest request,@RequestParam("price") double price ){
//		String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
//		String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
//		try {
//			Payment payment = paypalService.createPayment(
//					price, 
//					"USD", 
//					PaypalPaymentMethod.paypal, 
//					PaypalPaymentIntent.sale,
//					"payment description",  
//					cancelUrl, 
//					successUrl);
//			for(Links links : payment.getLinks()){
//				if(links.getRel().equals("approval_url")){
//					return "redirect:" + links.getHref();
//				}
//			}
//		} catch (PayPalRESTException e) {
//			log.error(e.getMessage());
//		}
//		return "redirect:/home";
//	}
//
//	@GetMapping(URL_PAYPAL_CANCEL)
//	public String cancelPay(){
//		return "home";
//	}
//
//	@GetMapping(URL_PAYPAL_SUCCESS)
//	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
//		try {
//			Payment payment = paypalService.executePayment(paymentId, payerId);
//			System.out.println(payment.getPayer().getPayerInfo().getFirstName());
//			if(payment.getState().equals("approved")){
//				checkoutController.removeCartAfterCheckoutSuccess();
//				return "paymentSuccess";
//			}
//		} catch (PayPalRESTException e) {
//			log.error(e.getMessage());
//		}
//		return "redirect:/paymentSuccess";
//	}
//	
//}