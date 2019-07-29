package com.kapeed.microservices.currencyconversionservice.controller;

import com.kapeed.microservices.currencyconversionservice.beans.CurrencyConversionBean;
import com.kapeed.microservices.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CurrencyExchangeServiceProxy  currencyExchangeServiceProxy;

    @RequestMapping(method = RequestMethod.GET, path = "/currency-converter/from/{from}/to/{to}/amount/{amount}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal amount){

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class,
                uriVariables);

        CurrencyConversionBean response = responseEntity.getBody();


        return new CurrencyConversionBean(response.getId(),
                from,
                to,
                response.getConversionMultiple(),
                amount,
                amount.multiply(response.getConversionMultiple()),
                response.getPort());
    }

    // Using Feign Proxy: Feing is a REST Client for invoking other microservice

    @RequestMapping(method = RequestMethod.GET, path = "/currency-converter-feign/from/{from}/to/{to}/amount/{amount}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal amount){

        CurrencyConversionBean response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);

        logger.info("CCS -> {}", response);
        return new CurrencyConversionBean(response.getId(),
                from,
                to,
                response.getConversionMultiple(),
                amount,
                amount.multiply(response.getConversionMultiple()),
                response.getPort());
    }
}
