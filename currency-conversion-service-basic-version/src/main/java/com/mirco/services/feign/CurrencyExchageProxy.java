package com.mirco.services.feign;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mirco.services.beans.CurrencyConversion;

//@FeignClient(name = "currency-exchange-service", url = "localhost:8000")  // used without ribbon client
@FeignClient(name = "currency-exchange-service")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchageProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue
				(@PathVariable String from, @PathVariable String to)	throws Exception;
	
	@GetMapping("/greeting")
	public String greeting();
}
