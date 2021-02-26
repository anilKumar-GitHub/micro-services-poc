package com.mirco.services.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mirco.services.beans.CurrencyConversion;
import com.mirco.services.feign.CurrencyExchageProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchageProxy proxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	private CurrencyConversion currencyConversion(
					@PathVariable String from, 
					@PathVariable String to, 
					@PathVariable BigDecimal quantity)		{
		
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversion> conversion = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversion.class, 
				uriVariables);

		CurrencyConversion bean = conversion.getBody();
		bean.setQuantity(quantity);
		bean.setTotalCalculatedAmount(bean.getConversionMultiple().multiply(quantity));
  		
		return bean;
	}

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	private CurrencyConversion currencyConversionUsingFeign(
					@PathVariable String from, 
					@PathVariable String to, 
					@PathVariable BigDecimal quantity)		throws Exception	{

		System.out.println("-------------------------------------currencyConversionUsingFeign-------------------------------------");
		CurrencyConversion bean = proxy.retrieveExchangeValue(from, to);
		bean.setQuantity(quantity);
		bean.setTotalCalculatedAmount(bean.getConversionMultiple().multiply(quantity));
		return bean;
	}
	
	@GetMapping
	public String greetings() {
		return proxy.greeting();
	}
}