package com.payment.tiny.paymentlink.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.tiny.paymentlink.externalservice.BitlyClient;
import com.payment.tiny.paymentlink.model.CreatePaymentRequest;
import com.payment.tiny.paymentlink.model.CreatePaymentResponse;

@Service("paymentLinkcreationservice")
public class PaymentLinkCreationService {

	@Autowired
	private BitlyClient bitlyClient;

	public CreatePaymentResponse createPaymentResponse(CreatePaymentRequest request) throws Exception {

		String shortUrl = null;
		try {
			shortUrl = bitlyClient.getShortenPyamentUrl(
					"http://127.0.0.1:8030/EIPS/ProcessingEipsRequest?args=AAEF3E1E29CF35EE78C94742D29DF3391BBDA907600F759C32857A6F3F929646CD186B91C5A0D252",
					"d54204456c059c1436a674c1792a957e4320e0de");

		} catch (Exception e) {
			throw e;
		}
		// Change the hardcoded value
		CreatePaymentResponse response = new CreatePaymentResponse();
		response.setPaymentLinkId(1234L);
		response.setPaymentUrl(shortUrl);
		response.setResponseCode(200);

		return response;
	}

}
