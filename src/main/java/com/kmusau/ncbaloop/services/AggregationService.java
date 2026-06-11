package com.kmusau.ncbaloop.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kmusau.ncbaloop.dto.ResponseDto;
import com.kmusau.ncbaloop.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AggregationService {
    private final ApiService apiService;


    public ResponseDto fetchAllCountriesFullInfo() throws JsonProcessingException, CustomException {
        return apiService.getAllCountriesFullInfo();
    }



    //The logic to combine different SOAP contracts to desired REST API response will go here

    //The SOAP APIs have already been consumed in  apiService
}
