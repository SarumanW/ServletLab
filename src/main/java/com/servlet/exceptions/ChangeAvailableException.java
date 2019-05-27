package com.servlet.exceptions;

import com.servlet.entity.ChangeViewModel;
import lombok.Data;

import java.util.List;

@Data
public class ChangeAvailableException extends Exception {
    private List<ChangeViewModel> change;

    public ChangeAvailableException(List<ChangeViewModel> change) {
        super("Change is available");
        this.change = change;
    }
}
