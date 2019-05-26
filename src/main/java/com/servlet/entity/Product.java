package com.servlet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Product {
    private int productNumber;
    private BigDecimal price;
    private int quantity;
    private String name;

}
