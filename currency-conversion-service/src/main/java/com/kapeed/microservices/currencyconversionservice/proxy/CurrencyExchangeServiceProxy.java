package com.kapeed.microservices.currencyconversionservice.proxy;


import com.kapeed.microservices.currencyconversionservice.beans.CurrencyConversionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// @FeignClient(name ="currency-exchange-service" , url = "localhost:8000")
// @FeignClient(name ="currency-exchange-service")

@FeignClient(name = "netflix-zuul-api-gateway-server")

/*
Now the request is going through the Zuul API Gateway instead of directly calling "currency-exchange-service"
 */

@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {


    // @RequestMapping(method = RequestMethod.GET, path = "/currency-exchange/from/{from}/to/{to}")

    @RequestMapping(method = RequestMethod.GET,
            path = "/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from,
                                                        @PathVariable("to") String to);

}
