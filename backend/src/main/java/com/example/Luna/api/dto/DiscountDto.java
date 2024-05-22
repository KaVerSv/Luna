package com.example.Luna.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {
    private Integer id;
    private Integer percentage;
    private LocalDate startDate;
    private LocalDate endDate;
}
