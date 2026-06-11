package com.kmusau.ncbaloop.services;

import com.countryinfo.xml.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmusau.ncbaloop.configurations.RedisUtils;
import com.kmusau.ncbaloop.dto.CountryDetails;
import com.kmusau.ncbaloop.dto.ResponseDto;
import com.kmusau.ncbaloop.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ApiService {
    private final Util util;
    private final RedisUtils redisUtils;
    private final ObjectMapper objectMapper;

    private static final String ALL_COUNTRIES_FULL_INFO_KEY = "allCountriesFullInfo";

    public FullCountryInfoAllCountriesResponse getAllCountriesInfo() throws JsonProcessingException {
        String soapAction = "FullCountryInfoAllCountries";
        FullCountryInfoAllCountries fullCountryInfoAllCountries = new FullCountryInfoAllCountries();
        return util.sendAllCountriesInfoRequest(fullCountryInfoAllCountries, soapAction);
    }


    public ResponseDto getAllCountriesFullInfo() throws CustomException {
        ResponseDto responseDto = new ResponseDto();
        List<CountryDetails> countryEntries = new ArrayList<>();
        try {
            String redisValue = redisUtils.fetchFromRedis(ALL_COUNTRIES_FULL_INFO_KEY);

            if (redisValue == null) {
                var countriesResponseList = this.getAllCountriesInfo().getFullCountryInfoAllCountriesResult().getTCountryInfo();

                for (var countryInfo : countriesResponseList) {
                    String isoCode = countryInfo.getSISOCode();
                    String name = countryInfo.getSName();
                    String capital = countryInfo.getSCapitalCity();
                    String phone = countryInfo.getSPhoneCode();
                    String contCode = countryInfo.getSContinentCode();
                    String currency = countryInfo.getSCurrencyISOCode();

                    List<CountryDetails.Languages> languages = new ArrayList<>();

                    var langList = countryInfo.getLanguages().getTLanguage();
                    if (langList != null) {
                        for (var lang : langList) {
                            languages.add(CountryDetails.Languages.builder()
                                    .languageCode(lang.getSISOCode())
                                    .language(lang.getSName())
                                    .build());
                        }
                    }

                    CountryDetails countryDetails = CountryDetails.builder()
                            .countryIsoCode(isoCode)
                            .countryName(name)
                            .capitalCity(capital)
                            .phoneCode(phone)
                            .continentCode(contCode)
                            .currencyCode(currency)
                            .languages(languages)
                            .build();

                    countryEntries.add(countryDetails);
                }
                redisUtils.writeToRedis(ALL_COUNTRIES_FULL_INFO_KEY, objectMapper.writeValueAsString(countryEntries), Duration.ofHours(24));
                responseDto.setMessage("Success");
                responseDto.setData(countryEntries);
                return responseDto;
            } else {
                countryEntries = objectMapper.readValue(redisValue, new TypeReference<ArrayList<CountryDetails>>() {});
                responseDto.setMessage("Success");
                responseDto.setData(countryEntries);
                return responseDto;
            }
        } catch (Exception e) {
            throw new CustomException("Exception occurred, "+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
