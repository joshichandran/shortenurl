package com.payment.tiny.paymentlink.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.tiny.paymentlink.model.CreatePaymentRequest;
import com.payment.tiny.paymentlink.model.CreatePaymentResponse;
import com.payment.tiny.paymentlink.service.PaymentLinkCreationService;

@RestController
@RequestMapping("/api/PaymentURL")
public class PaymentLinkCreationController {
	
	
	@Autowired
	private PaymentLinkCreationService paymentLinkCreationService;
	
	@PostMapping("CreatePaymentURL")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	CreatePaymentResponse createPaymentResponse(@RequestBody CreatePaymentRequest request) throws Exception {
		
		CreatePaymentResponse response = paymentLinkCreationService.createPaymentResponse(request);
		return response;
	}
}
