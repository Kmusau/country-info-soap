package com.kmusau.ncbaloop.services;

import com.countryinfo.xml.CapitalCity;
import com.countryinfo.xml.CapitalCityResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class Util {

    private final WebServiceTemplate webServiceTemplate;

    public CapitalCityResponse sendCapitalCityRequest(CapitalCity capitalCity, String soapAction, String uri) throws JsonProcessingException {
        log.info("Request to Country Info :: {}", new ObjectMapper().writeValueAsString(capitalCity));
        return  (CapitalCityResponse) webServiceTemplate.marshalSendAndReceive(
                uri,
                capitalCity,
                new SoapActionCallback(soapAction)
        );
    }
}
