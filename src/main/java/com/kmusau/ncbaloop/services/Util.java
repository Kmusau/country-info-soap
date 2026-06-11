package com.kmusau.ncbaloop.services;

import com.countryinfo.xml.*;
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

    private static final String uri = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL";

    public FullCountryInfoAllCountriesResponse sendAllCountriesInfoRequest(FullCountryInfoAllCountries fullCountryInfoAllCountries, String soapAction) throws JsonProcessingException {
        log.info("Request to Country Info :: {}", new ObjectMapper().writeValueAsString(fullCountryInfoAllCountries));
        return  (FullCountryInfoAllCountriesResponse) webServiceTemplate.marshalSendAndReceive(
                uri,
                fullCountryInfoAllCountries,
                new SoapActionCallback(soapAction)
        );
    }
}
