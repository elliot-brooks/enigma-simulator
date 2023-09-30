package main.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Tools {
    /**
     * Swap the key and value pairs contained in the hashmap
     */
    public static <V, K> Map<V, K> invertHashMap(Map<K, V> map) {
        Map<V, K> inversedMap = map.entrySet().stream().collect(Collectors.toMap(Entry::getValue, Entry::getKey));
        return inversedMap;
    }

    /**
     * Creates a translation table for the given string such that the string[0] will
     * be mapped to 'A' and string[25] will be mapped to 'z'.
     * 
     * @param ordered_cyphertext_alphabet The cyphertext alphabet
     * @return Hashmap containing the cyphertext mappings
     */
    public static Map<String, String> createTranslationTable(String orderedCyphertextAlphabet) {
        if (orderedCyphertextAlphabet.length() != 26) {
            return null;
        }
        HashMap<String, String> translationTable = new HashMap<>();
        for (int i = 0; i < orderedCyphertextAlphabet.length(); i++) {
            translationTable.put(String.valueOf(Constants.ALPHABET.charAt(i)),
                    String.valueOf(orderedCyphertextAlphabet.charAt(i)));
        }
        return translationTable;
    }
}
