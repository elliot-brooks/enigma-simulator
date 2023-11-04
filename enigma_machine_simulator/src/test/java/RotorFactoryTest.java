import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.enigma_machine.enigma.RotorFactory;
import com.enigma_machine.enigma.exceptions.InvalidRotorEncodingException;

public class RotorFactoryTest {
    @Test
    public void encodingValidationTest() {
        // Encoding too long
        try {
            RotorFactory.buildCustomRotor("Test", "EKMFLGDQVZNTOWYHXUSPAIBRCJ_1111", 0, 0, 0);
            fail("Encodings cannot be longer than 26");
        }
        catch (InvalidRotorEncodingException e) {
            // Passed
        }

        // Encoding is not one to one
        try {
            RotorFactory.buildCustomRotor("Test", "EEMFLGDQVZNTOWYHXUSPAIBRCJ", 0, 0, 0);
            fail("Encodings must be one-to-one");
        } catch (InvalidRotorEncodingException e) {
            // Passed
        }

        try {
            RotorFactory.buildCustomRotor("Test", null, 0, 0, 0);
            fail("Encodings cannot be null");
        } catch (InvalidRotorEncodingException e) {
            // Passed
        }

        // Test valid encoding
        try {
            RotorFactory.buildCustomRotor("Test", "ZYXWVUTSRQPONMLKJIHGFEDCBA", 0, 0, 0);
        } catch (InvalidRotorEncodingException e) {
            fail();
        }
    }

    @Test
    public void prebuiltTest() {
        assertEquals("EKMFLGDQVZNTOWYHXUSPAIBRCJ", RotorFactory.buildPresetRotor("I", 0, 0).getEncoding());
        assertEquals("AJDKSIRUXBLHWTMCQGZNPYFVOE", RotorFactory.buildPresetRotor("II", 0, 0).getEncoding());
        assertEquals("BDFHJLCPRTXVZNYEIWGAKMUSQO", RotorFactory.buildPresetRotor("III", 0, 0).getEncoding());
        assertEquals("ESOVPZJAYQUIRHXLNFTGKDCMWB", RotorFactory.buildPresetRotor("IV", 0, 0).getEncoding());
        assertEquals("VZBRGITYUPSDNHLXAWMJQOFECK", RotorFactory.buildPresetRotor("V", 0, 0).getEncoding());
    }
}
