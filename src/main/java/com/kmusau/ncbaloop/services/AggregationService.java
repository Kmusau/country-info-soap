package com.kmusau.ncbaloop.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kmusau.ncbaloop.dto.CountryDetails;
import com.kmusau.ncbaloop.dto.PagedResponse;
import com.kmusau.ncbaloop.dto.ResponseDto;
import com.kmusau.ncbaloop.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AggregationService {
    private final ApiService apiService;


    public ResponseDto fetchAllCountriesFullInfoPaginated(
            String countryName, String continent, String language, String currency, int pageNo, int pageSize
    ) throws CustomException {
        ResponseDto responseDto = new ResponseDto();
        List<CountryDetails> countryEntries;
        countryEntries = (List<CountryDetails>) apiService.getAllCountriesFullInfo().getData();

        List<CountryDetails> filteredCountries = countryEntries.stream()
                .filter(country -> isBlank(countryName) ||
                        country.getCountryName().equalsIgnoreCase(countryName))
                .filter(country -> isBlank(continent) ||
                        country.getContinentCode().equalsIgnoreCase(continent))
                .filter(country -> isBlank(currency) ||
                        country.getCurrencyCode().equalsIgnoreCase(currency))
                .filter(country -> isBlank(language) ||
                        country.getLanguages().stream()
                                .anyMatch(lang ->
                                        lang.getLanguage().equalsIgnoreCase(language)))
                .sorted(CountryDetails::compareTo)
                .toList();

        responseDto.setData(paginate(filteredCountries, pageNo, pageSize));
        responseDto.setMessage("Success");
        return responseDto;
    }


    public static <T> PagedResponse<T> paginate(List<T> items, int page, int size) {

        int start = page * size;
        int end = Math.min(start + size, items.size());

        List<T> content =
                start >= items.size()
                        ? Collections.emptyList()
                        : items.subList(start, end);

        return new PagedResponse<>(
                content,
                page,
                items.size(),
                (int) Math.ceil((double) items.size() / size)
        );
    }


    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
