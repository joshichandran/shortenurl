package com.payment.tiny.paymentlink.externalservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.payment.tiny.paymentlink.model.BitlyResponse;
import com.payment.tiny.paymentlink.util.HttpClient;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class BitlyClient {
	
	public String shortenPaymentUrl(String paymentUrl,String bearerAuth) throws Exception {
		
		ResponseEntity<BitlyResponse> response =null;
		String url = "https://api-ssl.bitly.com/v4/shorten";
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    
	   // headers.setContentType(MediaType.APPLICATION_JSON);
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

	
	public String getShortenPyamentUrl(String paymentUrl,String bearerAuth) throws IOException {
		String url = "https://api-ssl.bitly.com/v4/shorten";
		
		JSONObject jsonObject = new JSONObject();
	    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		try {
		    jsonObject.put("domain", "bit.ly");
		    jsonObject.put("long_url", paymentUrl);
		} catch (JSONException e) {
		    e.printStackTrace();
		}		
		RequestBody requestBody=new FormBody.Builder().
				add("domain", "bit.ly").
				add("long_url", paymentUrl).build();
		
		 RequestBody body = RequestBody.create(JSON, jsonObject.toString());

	   // Headers headers = new Headers();

		Request request= new Request.Builder().
				header("AUTHORIZATION", "Bearer "+bearerAuth).
				url(url).
				post(body).
				build();
		//sync call
		//Response response=HttpClient.getHttpClient().newCall(request).execute();
		
		//async call
		
		Call call=HttpClient.getHttpClient().newCall(request);
		call.enqueue(new Callback(){

			@Override
			public void onFailure(Call call, IOException e) {
		        e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if(!response.isSuccessful()) {
		            throw new IOException("Unexpected response " + response);

				}else {
					System.out.println("response is "+response.body().string());
				}
			}
				
		});
	//	System.out.println("response is "+response.body().string());
		return null;
	}
}
