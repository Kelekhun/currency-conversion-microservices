package com.kapeed.microservices.currencyexchangeservice.controller;

import com.kapeed.microservices.currencyexchangeservice.beans.ExchangeValue;
import com.kapeed.microservices.currencyexchangeservice.repository.ExchangeValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private ExchangeValueRepository exchangeValueRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, path = "/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to){

        ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to);

        exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

        logger.info("CES: -> {}", exchangeValue);

        return exchangeValue;
    }
}
