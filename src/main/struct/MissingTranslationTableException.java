package main.struct;

import main.tools.Constants;

public class MissingTranslationTableException extends Exception {
    public MissingTranslationTableException() {
        super(Constants.NULL_TRANSLATION_TABLE);
    }
}
