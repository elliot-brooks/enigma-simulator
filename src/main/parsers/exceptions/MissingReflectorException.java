package main.parsers.exceptions;

public class MissingReflectorException extends Exception {
    private static final String ERROR_MESSAGE = '"' + "%s" + '"' + " does not exist, please ensure it is defined in reflector_bank.xml";
    public MissingReflectorException(String rotorName) {
        super(String.format(ERROR_MESSAGE, rotorName));
    }
}
