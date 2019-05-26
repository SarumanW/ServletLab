package com.servlet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeViewModel {
    private String nominal;
    private String quantity;
}
