package main.struct;

import main.tools.Constants;

public class MissingEncodingException extends Exception {
    public MissingEncodingException() {
        super(Constants.NULL_TRANSLATION_TABLE);
    }
}
