package com.payment.tiny.paymentlink.externalservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.payment.tiny.paymentlink.model.BitlyResponse;

@Component
public class BitlyClient {
	
	public String shortenPaymentUrl(String paymentUrl,String bearerAuth) throws Exception {
		
		ResponseEntity<BitlyResponse> response =null;
		String url = "https://api-ssl.bitly.com/v4/shorten";
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setBearerAuth(bearerAuth);

	    Map<String, Object> map = new HashMap<>();
	    map.put("domain", "bit.ly");
	    map.put("long_url",paymentUrl);
	    try {
	    	HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
		    response = restTemplate.postForEntity(url, entity, BitlyResponse.class);
		    
	    }catch(Exception e) {
	    	throw new Exception("BitlyClient rest template call failed");
	    }
	   
	    if(response!=null && response.getStatusCode() != HttpStatus.OK) {
	    	throw new Exception("BitlyClient failed to call. Status is "+response.getStatusCode().value());
	    }
		return response.getBody().getLink();
	}

}
