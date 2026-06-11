package com.kmusau.ncbaloop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PagedResponse<T> {
    private List<T> content;
    private int number;
    private long totalElements;
    private int totalPages;
}

