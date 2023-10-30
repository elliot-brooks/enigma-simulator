package com.enigma_machine.enigma.exceptions;

public class InvalidReflectorEncodingException extends Exception {
    private static final String ERROR_MESSAGE = "Specified encoding is not permitted. Please ensure that no character maps to itself and all characters are mapped";

    public InvalidReflectorEncodingException() {
        super(ERROR_MESSAGE);
    }
}
