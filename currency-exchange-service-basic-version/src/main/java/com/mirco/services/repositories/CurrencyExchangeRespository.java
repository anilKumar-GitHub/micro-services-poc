package com.mirco.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mirco.services.beans.ExchangeValue;

@Repository
public interface CurrencyExchangeRespository	extends		JpaRepository<ExchangeValue, Long>	{

	ExchangeValue findByFromAndTo(String from, String to);
}