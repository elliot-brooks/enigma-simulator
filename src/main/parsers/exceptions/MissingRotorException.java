package main.parsers.exceptions;

public class MissingRotorException extends Exception {
    private static final String ERROR_MESSAGE = '"' + "%s" + '"' + " does not exist, please ensure it is defined in rotor_bank.xml";
    public MissingRotorException(String rotorName) {
        super(String.format(ERROR_MESSAGE, rotorName));
    }
}
