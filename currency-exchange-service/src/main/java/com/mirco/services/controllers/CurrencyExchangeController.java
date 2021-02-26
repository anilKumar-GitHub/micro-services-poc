package com.mirco.services.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mirco.services.beans.ExchangeValue;
import com.mirco.services.repositories.CurrencyExchangeRespository;

@RestController
public class CurrencyExchangeController {
	
	private Logger LOGGER = LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	private CurrencyExchangeRespository exchangeRepo;
	
	@Autowired
	Environment env;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue
				(@PathVariable String from, @PathVariable String to)	throws Exception	{

		LOGGER.info("currency-exchange called.............");
		
		ExchangeValue exchangeValue = exchangeRepo.findByFromAndTo(from, to);
		exchangeValue.setPort(Integer.parseInt(env.getProperty("server.port")));
		return exchangeValue;
	}
	
	@GetMapping("/greeting")
	public String greeting() {
		return "Currency-Exchange :: Good Morning ! Have Nice Day...!";
	}
}
