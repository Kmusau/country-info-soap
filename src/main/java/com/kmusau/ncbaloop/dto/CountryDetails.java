package com.kmusau.ncbaloop.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDetails {
    private String countryIsoCode;
    private String countryName;
    private String capitalCity;
    private String phoneCode;
    private String continentCode;
    private String continentName;
    private String currencyCode;
    private List<Languages> languages;



    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Languages {
        private String languageCode;
        private String language;
    }
}
