package com.mirco.services.feign;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mirco.services.beans.CurrencyConversion;

//@FeignClient(name = "currency-exchange-service")
@FeignClient(name = "netflix-zuul-api-gateway-service/currency-exchange-service")	// to pass via API gateway
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue
				(@PathVariable String from, @PathVariable String to)	throws Exception;
	
	@GetMapping("/greeting")
	public String greeting();
}