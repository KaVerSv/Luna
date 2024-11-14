package com.example.Luna.api.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderRequestDto {

    private Long userId;
    private Date orderDate;
    private boolean paid;
    private List<Long> bookIds;
}