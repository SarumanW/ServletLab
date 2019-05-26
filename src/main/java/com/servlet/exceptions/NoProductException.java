package com.servlet.exceptions;

public class NoProductException extends Exception {
    public NoProductException() {
        super("No such product left. Try to input another number.");
    }
}
