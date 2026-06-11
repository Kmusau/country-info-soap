package com.kmusau.ncbaloop.services;

import com.countryinfo.xml.CapitalCity;
import com.countryinfo.xml.CapitalCityResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kmusau.ncbaloop.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ApiService {
    private final Util util;

    public ResponseDto fetchCapitalCities(String soapAction, String countryIsoCode, String uri) throws JsonProcessingException {
        Map<String, String> responseData = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();
        CapitalCityResponse capitalCityResponse = this.getCapitalCityResponse(soapAction, countryIsoCode, uri);
        responseData.put("countryIsoCode", countryIsoCode);
        responseData.put("capitalCity", capitalCityResponse.getCapitalCityResult());

        responseDto.setData(responseData);
        responseDto.setMessage("Success");
        return responseDto;
    }

    public CapitalCityResponse getCapitalCityResponse(String soapAction, String countryIsoCode, String uri) throws JsonProcessingException {
        CapitalCity capitalCity = new CapitalCity();
        capitalCity.setSCountryISOCode(countryIsoCode);
        return util.sendCapitalCityRequest(capitalCity, soapAction, uri);
    }
}
