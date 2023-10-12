package main.enigma;

import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.List;

import main.tools.Constants;

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

    public void addCable(int firstCharacter, int secondCharacter) {
        if (isCharacterWired(firstCharacter) || isCharacterWired(secondCharacter)) {
            // TODO : Throw error for adding cables which are already taken
            return;
        }
        wiring[firstCharacter] = secondCharacter;
        wiring[secondCharacter] = firstCharacter;
    }

    public void removeCable(int firstCharacter, int secondCharacter) {
        if (isCharacterPairWired(firstCharacter, secondCharacter)) {
            // Remove wiring only if the two letters are connected
            wiring[firstCharacter] = firstCharacter;
            wiring[secondCharacter] = secondCharacter;
            return;
        }
        // TODO : Throw error for removing non-existant cable
        return;
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
            pairedList.add(formatCharacterPair(i));
        }
        return pairedList;
    }

    public int getCablesUsed() {
        return cablesUsed;
    }

}
