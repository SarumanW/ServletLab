package com.servlet.exceptions;

public class NotSufficientChangeException extends Exception {
    public NotSufficientChangeException() {
        super("Machine has no sufficient change to return. Choose another product or reset.");
    }
}
