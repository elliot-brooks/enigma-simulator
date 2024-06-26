package com.enigma_machine.enigma;

import java.util.ArrayList;
import java.util.List;

import com.enigma_machine.enigma.exceptions.PlugboardConnectionAlreadyEstablishedException;
import com.enigma_machine.enigma.exceptions.PlugboardConnectionDoesNotExistException;
import com.enigma_machine.enigma.tools.Constants;

public class Plugboard {
    private int cablesUsed;
    private int[] wiring;

    public Plugboard() {
        initialiseWiring();
    }

    public int encrypt(int characterIndex) {
        return wiring[characterIndex];
    }

    // Initially, map each character to its self
    private void initialiseWiring() {
        wiring = new int[Constants.ALPHABET_LENGTH];
        for (int i = 0; i < wiring.length; i++) {
            wiring[i] = i;
        }
    }

    public void addCable(int firstCharacter, int secondCharacter)
            throws PlugboardConnectionAlreadyEstablishedException {
        if (isCharacterWired(firstCharacter) || isCharacterWired(secondCharacter)) {
            throw new PlugboardConnectionAlreadyEstablishedException();
        }
        wiring[firstCharacter] = secondCharacter;
        wiring[secondCharacter] = firstCharacter;
        cablesUsed = cablesUsed + 1;

    }

    public void removeCable(int firstCharacter, int secondCharacter) throws PlugboardConnectionDoesNotExistException {
        if (isCharacterPairWired(firstCharacter, secondCharacter)) {
            // Remove wiring only if the two letters are connected
            wiring[firstCharacter] = firstCharacter;
            wiring[secondCharacter] = secondCharacter;
            cablesUsed = cablesUsed - 1;
            return;
        }
        throw new PlugboardConnectionDoesNotExistException();
    }

    private boolean isCharacterWired(int characterIndex) {
        return wiring[characterIndex] == characterIndex ? false : true;
    }

    private boolean isCharacterPairWired(int firstCharacter, int secondCharacter) {
        if (wiring[firstCharacter] == secondCharacter) {
            if (wiring[secondCharacter] == firstCharacter) {
                return true;
            }
        }
        return false;
    }

    private String formatCharacterPair(int characterIndex) {
        StringBuilder sb = new StringBuilder();
        char firstCharacter = (char) (characterIndex + Constants.JAVA_A_VALUE);
        char secondCharacter = (char) (wiring[characterIndex] + Constants.JAVA_A_VALUE);
        sb.append(firstCharacter);
        sb.append(secondCharacter);
        return sb.toString();
    }

    public List<String> getPairedCharacters() {
        List<String> pairedList = new ArrayList<>();
        for (int i = 0; i < wiring.length; i++) {
            if (isCharacterWired(i)) {
                pairedList.add(formatCharacterPair(i));
            }
        }
        return pairedList;
    }

    public String getEncoding() {
        List<String> pairs = getPairedCharacters();
        if (pairs.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pairs.size() - 1; i++ ) {
            sb.append(pairs.get(i) + " ");
        }
        sb.append(pairs.get(pairs.size() - 1));

        return sb.toString();
    }

    public int getCablesUsed() {
        return cablesUsed;
    }

}
