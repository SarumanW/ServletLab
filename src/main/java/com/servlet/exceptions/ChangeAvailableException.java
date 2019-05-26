package com.servlet.exceptions;

import com.servlet.entity.Coin;
import lombok.Data;

import java.util.List;

@Data
public class ChangeAvailableException extends Exception {
    private List<Coin> change;

    public ChangeAvailableException(List<Coin> change) {
        super("Change is available");
        this.change = change;
    }
}
