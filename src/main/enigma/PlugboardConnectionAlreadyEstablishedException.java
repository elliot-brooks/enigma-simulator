package main.enigma;

public class PlugboardConnectionAlreadyEstablishedException extends Exception {
    private static final String ERROR_MESSAGE = "The specified connection already exists on the plugboard";

    public PlugboardConnectionAlreadyEstablishedException() {
        super(ERROR_MESSAGE);
    }
}
