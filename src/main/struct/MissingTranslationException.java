package main.struct;

import main.tools.Constants;

public class MissingTranslationException extends Exception {
    public MissingTranslationException(String character) {
        super(String.format(Constants.MISSING_CHARACTER_TRANSLATION, character));
    }
}
