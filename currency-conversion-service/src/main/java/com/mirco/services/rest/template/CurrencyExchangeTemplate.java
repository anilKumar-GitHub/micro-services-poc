package com.mirco.services.rest.template;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mirco.services.beans.CurrencyConversion;

@Component
public class CurrencyExchangeTemplate {

	@Autowired
	private RestTemplate template;
	
	public CurrencyConversion getCurrencyExchangeDetails(String from, String to) {
		
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConversion> conversion = template.getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversion.class, 
				uriVariables);

		return conversion.getBody();
	}
}