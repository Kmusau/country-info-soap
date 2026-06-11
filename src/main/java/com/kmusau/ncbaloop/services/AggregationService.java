package com.kmusau.ncbaloop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AggregationService {
    private final ApiService apiService;

    //The logic to combine different SOAP contracts to desired REST API response will go here

    //The SOAP APIs have already been consumed in  apiService
}
