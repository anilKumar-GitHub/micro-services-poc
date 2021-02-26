package com.mirco.services.controllers;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mirco.services.beans.CurrencyConversion;
import com.mirco.services.feign.CurrencyExchangeProxy;
import com.mirco.services.rest.template.CurrencyExchangeTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class CurrencyConversionController {

	private Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionController.class);
	
	@Autowired
	private CurrencyExchangeTemplate template;
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	private CurrencyConversion currencyConversion(
					@PathVariable String from, 
					@PathVariable String to, 
					@PathVariable BigDecimal quantity)		{

		LOGGER.info("METHOD:: CurrencyConversion currencyConversion(String from@" 
					+ from+ ", String to@" + to +", BigDecimal quantity@" + quantity +")");

		CurrencyConversion bean = template.getCurrencyExchangeDetails(from, to);
		bean.setQuantity(quantity);
		bean.setTotalCalculatedAmount(bean.getConversionMultiple().multiply(quantity));
		
		return bean;
	}

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	private CurrencyConversion currencyConversionUsingFeign(
					@PathVariable String from, 
					@PathVariable String to, 
					@PathVariable BigDecimal quantity)		throws Exception	{

		LOGGER.info("METHOD:: CurrencyConversion currencyConversionUsingFeign("
				+ "String from@" + from+ ", String to@" + to +", BigDecimal quantity@" + quantity +")");

		CurrencyConversion bean = proxy.retrieveExchangeValue(from,  to);
		bean.setQuantity(quantity);
		bean.setTotalCalculatedAmount(bean.getConversionMultiple().multiply(quantity));
		return bean;
	}
	
	@GetMapping
	public String greetings() {
		return proxy.greeting();
	}
	
	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod = "fallBackFaultToleranceMethod")
	public String faultToleranceExample()	{
		LOGGER.info("METHOD:: String faultToleranceExample(  )");
		throw new RuntimeException("Hystrix example for fault tolerance");
	}
	
	public String fallBackFaultToleranceMethod()	{
		LOGGER.info("METHOD:: String fallBackFaultToleranceMethod(  )");
		return "Hystrix example for fault tolerance: default implementation";
	}	
}