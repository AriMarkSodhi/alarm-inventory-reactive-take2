package com.ari.msexp1;


import com.ari.msexp1.mongoDAL.model.AlarmDefinition;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class AlarmInventoryIT {
    private static final Logger LOG = Logger.getLogger(AlarmInventoryIT.class);
    private TestRestTemplate restTemplate;

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

    private String getTargetURL()
    {
         return "http://localhost:80/alarminventory/alarmdefinition";
    }

    @Before
    public void beforeTest() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        builder.configure(new RestTemplate(getClientHttpRequestFactory()));
        restTemplate = new TestRestTemplate(builder);
    }

    @Test
    public void getAllowedOptions() {
        Set<HttpMethod> optionsForAllow = restTemplate.optionsForAllow(getTargetURL());
        HttpMethod[] supportedMethods
                = {HttpMethod.GET};
        LOG.debug("Received alarm definition "+ ((optionsForAllow != null)? optionsForAllow.toString():"null"));
        assertTrue(optionsForAllow.containsAll(Arrays.asList(supportedMethods)));
    }

    @Test
    public void getAlarmDefinition() {
        ResponseEntity<AlarmDefinition> response
                = restTemplate.getForEntity(getTargetURL() + "?name=Alarm_0", AlarmDefinition.class);
        LOG.debug("Received alarm definition "+ ((response != null)? response.toString():"null"));
        assertTrue(HttpStatus.OK.equals(response.getStatusCode()));

    }
}