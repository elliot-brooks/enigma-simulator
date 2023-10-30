package com.enigma_machine.enigma.exceptions;

public class InvalidRotorEncodingException extends Exception {
    private static final String ERROR_MESSAGE = "Invalid encoding - Ensure each letter is mapped to another";

    public InvalidRotorEncodingException() {
        super(ERROR_MESSAGE);
    }
}
