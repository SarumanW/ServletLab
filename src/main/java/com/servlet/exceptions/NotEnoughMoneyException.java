package com.servlet.exceptions;

public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException(String moneyLeft) {
        super("It's not enough to buy this product. Please insert " + moneyLeft + " or reset.");
    }
}
