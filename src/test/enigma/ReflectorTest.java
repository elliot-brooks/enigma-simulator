package test.enigma;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import main.enigma.Reflector;
import main.enigma.ReflectorFactory;
import main.enigma.exceptions.InvalidReflectorEncodingException;
import main.tools.Tools;

public class ReflectorTest {

    private static Reflector reflectorUnderTest;

    @Before
    public void setup() throws InvalidReflectorEncodingException {
        reflectorUnderTest = ReflectorFactory.buildPresetReflector(ReflectorFactory.B_REFLECTOR);
    }

    @Test
    public void encryptionTest() {
        String encodingString = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            char character = Tools.convertIndexToCharacter(reflectorUnderTest.encrypt(i));
            sb.append(character);
        }
        assertEquals(encodingString, sb.toString());
    }

    @Test
    public void testGetters() {
        assertEquals("YRUHQSLDPXNGOKMIEBFZCWVJAT", reflectorUnderTest.getEncoding());
        assertEquals("UKW-B", reflectorUnderTest.getName());
    }

    @Test
    public void testInvalidEncoding() {
        String INVALID_ENCODING_1 = "ABC";
        String INVALID_ENCODING_2 = "AAAAAAAAAAAAAAAAAAAAAAAAAA";

        try {
            ReflectorFactory.buildCustomReflector("Test", INVALID_ENCODING_1);
            fail("Reflectors must have encodings of length 26");
        } catch (InvalidReflectorEncodingException e) {
            // Do nothing
        }

        try {
            ReflectorFactory.buildCustomReflector("Test", INVALID_ENCODING_2);
            fail("An encoding cannot map to its self in a reflector");
        } catch (InvalidReflectorEncodingException e) {
            // Do nothing
        }

    }
}
