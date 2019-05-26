package com.servlet.entity;

import java.math.BigDecimal;

public enum Coin {
    DOLLAR(1), NICKEL(0.5), DIME(0.1), QUARTER(0.25);
    private BigDecimal denomination;

    Coin(double denomination) {
        this.denomination = new BigDecimal(denomination);
    }

    public BigDecimal getDenomination() {
        return denomination;
    }
}


