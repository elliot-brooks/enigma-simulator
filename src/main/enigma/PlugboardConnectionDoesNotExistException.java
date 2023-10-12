package main.enigma;

public class PlugboardConnectionDoesNotExistException extends Exception {
    private static final String ERROR_MESSAGE = "The specified connection does not exist on the plugboard";

    public PlugboardConnectionDoesNotExistException() {
        super(ERROR_MESSAGE);
    }
}
