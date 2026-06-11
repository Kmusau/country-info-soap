package com.kmusau.ncbaloop.controllers;

import com.kmusau.ncbaloop.dto.ResponseDto;
import com.kmusau.ncbaloop.services.AggregationService;
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
            @RequestParam(name = "", required = false) String countryName,
            @RequestParam(name = "", required = false) String continent,
            @RequestParam(name = "", required = false) String language,
            @RequestParam(name = "", required = false) String currency ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Retrieve country details
    @GetMapping("/country-details/{countryName}")
    public ResponseEntity<ResponseDto> fetchCountries(@PathVariable String countryName) {
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
