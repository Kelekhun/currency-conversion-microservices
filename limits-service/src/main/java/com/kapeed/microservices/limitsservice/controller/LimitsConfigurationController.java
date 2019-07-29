package com.kapeed.microservices.limitsservice.controller;

import com.kapeed.microservices.limitsservice.beans.LimitsConfiguration;
import com.kapeed.microservices.limitsservice.configuration.Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    @Autowired
    private Configuration configuration;

    @RequestMapping(method = RequestMethod.GET, path = "/limits")
    public LimitsConfiguration retrieveLimitsFromConfigurations(){
        LimitsConfiguration limitsConfiguration = new LimitsConfiguration(configuration.getMaximum(), configuration.getMinimum());
        return limitsConfiguration;
    }

    @GetMapping("/fault-tolerance")
    @HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration")
    public LimitsConfiguration retrieveConfiguration() {
        throw new RuntimeException("Not available");
    }

    public LimitsConfiguration fallbackRetrieveConfiguration() {
        return new LimitsConfiguration(999, 9);
    }
}
