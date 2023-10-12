package main.enigma;

import main.tools.Constants;

public class Reflector {
    private String name;
    private String encoding;
    private int[] wiring;

    Reflector(String name, String encoding) throws InvalidReflectorEncodingException {
        this.name = name;
        this.encoding = encoding;
        this.wiring = configureWiring(encoding);
    }

    private int[] configureWiring(String encoding) throws InvalidReflectorEncodingException {
        if (!validateEncoding(encoding)) {
            throw new InvalidReflectorEncodingException();
        }
        char[] charArray = encoding.toCharArray();
        wiring = new int[charArray.length];
        for (int i = 0; i < wiring.length; i++) {
            wiring[i] = charArray[i] - Constants.JAVA_A_VALUE;
        }
        return wiring;
    }

    /**
     * Enigma Reflectors had a flaw that meant any letter x could not map to x. This
     * ensures that this criteria is met
     * 
     * @return
     */
    private boolean validateEncoding(String encoding) {
        char[] charArray = encoding.toCharArray();
        if (charArray.length != Constants.ALPHABET_LENGTH) {
            return false;
        }

        // Check for the case where A -> A (enigma machines could not do this)
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] - Constants.JAVA_A_VALUE == i) {
                return false;
            }
        }
        return true;
    }

    public int encrypt(int characterIndex) {
        return wiring[characterIndex];
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
