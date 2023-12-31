package com.enigma_machine.enigma.exceptions;

public class PlugboardConnectionAlreadyEstablishedException extends Exception {
    private static final String ERROR_MESSAGE = "The specified connection already exists on the plugboard";

    public PlugboardConnectionAlreadyEstablishedException() {
        super(ERROR_MESSAGE);
    }
}
