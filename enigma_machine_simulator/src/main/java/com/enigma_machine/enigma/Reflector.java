package com.enigma_machine.enigma;

import com.enigma_machine.tools.Constants;

public class Reflector {
    private String name;
    private String encoding;
    private int[] wiring;

    Reflector(String name, String encoding) {
        this.name = name;
        this.encoding = encoding;
        this.wiring = configureWiring(encoding);
    }

    private int[] configureWiring(String encoding) {
        char[] charArray = encoding.toCharArray();
        wiring = new int[charArray.length];
        for (int i = 0; i < wiring.length; i++) {
            wiring[i] = charArray[i] - Constants.JAVA_A_VALUE;
        }
        return wiring;
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

    public int[] getWiring() {
        return wiring;
    }
}
