package com.kmusau.ncbaloop.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kmusau.ncbaloop.dto.ResponseDto;
import com.kmusau.ncbaloop.exceptions.CustomException;
import com.kmusau.ncbaloop.services.AggregationService;
import com.kmusau.ncbaloop.services.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rdas/v1")
public class Controller {

    private final AggregationService aggregationService;

    //Search by country with options - minimum data, just a list of country name
    //They will all support pagination & sorting
    //Covers all these scenarios
//        • Search by country name
//        • Filter by continent
//        • Filter by currency
//        • Filter by language
//        • View countries sharing the same currency
    @GetMapping("/fetch-countries")
    public ResponseEntity<ResponseDto> fetchCountries(
            @RequestParam(name = "countryName", required = false) String countryName,
            @RequestParam(name = "continent", required = false) String continent,
            @RequestParam(name = "language", required = false) String language,
            @RequestParam(name = "currency", required = false) String currency,
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "4") int pageSize) throws CustomException, JsonProcessingException {
        var responseData = aggregationService.fetchAllCountriesFullInfoPaginated(countryName, continent, language, currency, pageNo, pageSize);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
