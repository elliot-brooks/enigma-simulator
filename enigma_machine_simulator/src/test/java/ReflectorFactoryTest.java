import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.enigma_machine.enigma.ReflectorFactory;
import com.enigma_machine.enigma.exceptions.InvalidReflectorEncodingException;

public class ReflectorFactoryTest {
    @Test
    public void validateEncodingTest() {
        // Encoding too long
        try {
            ReflectorFactory.buildCustomReflector("Test", "EJMZALYXVBWFCRQUONTSPIKHGD_______");
            fail("Encodings cannot be longer than 26");
        } catch (InvalidReflectorEncodingException e) {
            // Passed
        }

        // Encodings must be one-to-one
        try {
            ReflectorFactory.buildCustomReflector("Test", "EEMZALYXVBWFCRQUONTSPIKHGD");
            fail("Encodings must be one-to-one");
        } catch (InvalidReflectorEncodingException e) {
            // Passed
        }

        // Test null encoding
        try {
            ReflectorFactory.buildCustomReflector("Test", null);
            fail("Encoding cannot be null");
        } catch (InvalidReflectorEncodingException e) {
            // Passed
        }
        
        // Encodings cannot allow A -> A etc...
        try {
            ReflectorFactory.buildCustomReflector("Test", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            fail("Encodings cannot have letters mapped to themselves");
        } catch (InvalidReflectorEncodingException e) {
            // Passed
        }

        // Test valid encoding
        try {
            ReflectorFactory.buildCustomReflector("Test", "ZYXWVUTSRQPONMLKJIHGFEDCBA");
        } catch (InvalidReflectorEncodingException e) {
            fail();
        }
    }

    @Test
    public void prebuiltTest() {
        assertEquals("EJMZALYXVBWFCRQUONTSPIKHGD", ReflectorFactory.buildPresetReflector("UKW-A").getEncoding());
        assertEquals("YRUHQSLDPXNGOKMIEBFZCWVJAT", ReflectorFactory.buildPresetReflector("UKW-B").getEncoding());
        assertEquals("FVPJIAOYEDRZXWGCTKUQSBNMHL", ReflectorFactory.buildPresetReflector("UKW-C").getEncoding());
    }
}
